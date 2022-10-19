package com.sigma.om.demo.response;

import com.sigma.om.sdk.engine.interaction.Interaction;
import com.sigma.om.sdk.engine.interaction.InteractionBuilder;
import com.sigma.om.sdk.engine.interaction.InteractionState;
import com.sigma.om.sdk.engine.interaction.locator.InteractionResponseSenderLocator;
import com.sigma.om.sdk.repo.locator.SoiRepositoryLocator;
import com.sigma.om.sdk.soi.interaction.ExternalInteractionState;
import com.sigma.om.soi.framework.SoiAyncExternalResponse;
import com.sigma.om.soi.interaction.ExternalInteractionImpl;
import com.sigma.om.soi.interaction.ExternalResponseImpl;
import com.sigma.om.utils.CommonUtils;
import com.sigma.om.utils.JsonUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncDemoOssjToSOIExtInteractionResponse implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncDemoOssjToSOIExtInteractionResponse.class.getName());

    @Override
    public void process(Exchange exchange) throws Exception {
        // Get the external response object from exchange
        final SoiAyncExternalResponse soiExternalResponse = exchange.getIn().getBody(SoiAyncExternalResponse.class);

        // Grab the altkey from the exchange body object, to identify the
        // external interaction for which the async response is received.
        final String altKey = exchange.getIn().getBody(SoiAyncExternalResponse.class).getExternalKey();
        LOGGER.info("altKey {} ", altKey);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getHeaders {} ",
                    JsonUtils.toString(exchange.getIn().getBody(SoiAyncExternalResponse.class).getHeaders()));
        }

        // Grab the async response from external response object
        final String responseString = soiExternalResponse.getMessage();

        if (!CommonUtils.isEmpty(altKey)) {

            // Query the external interaction using the altkey value
            final ExternalInteractionImpl externalInteraction = (ExternalInteractionImpl) SoiRepositoryLocator
                    .getInstance().getSoiExtInteractionAltKey(altKey);

            // set the async response to external interaction
            final ExternalResponseImpl soiResponse = new ExternalResponseImpl();
            soiResponse.setResponseString(responseString);
            soiResponse.setExtInteractionId(externalInteraction.getId());
            externalInteraction.addResponse(soiResponse);

            // Create the interaction object
            final Interaction interaction = InteractionBuilder.make(externalInteraction).build();

            // Parse the value of VerificationStatus field from the async
            // response
            final String verificationStatus = StringUtils.substringBetween(responseString, "<m:VerificationStatus>",
                    "</m:VerificationStatus>");
            LOGGER.info("verificationStatus {}", verificationStatus);

            // If VerificationStatus is passed set the interaction and external
            // interaction state to DONE, else set Failed state.
            if (StringUtils.equalsIgnoreCase(verificationStatus, "passed")) {
                externalInteraction.setState(ExternalInteractionState.DONE);
                interaction.setState(InteractionState.DONE);
            } else {
                externalInteraction.setState(ExternalInteractionState.FAILED);
                interaction.setState(InteractionState.FAILED);
            }

            // Log and notify the interaction to the OM engine.
            LOGGER.debug("Notify Interaction response for {} adapter : {} ", externalInteraction.getAdapterName(),
                    JsonUtils.toPlainFormattedString(interaction));
            InteractionResponseSenderLocator.getInstance().notify(interaction);

            // Set the updated external interaction object to exchange.
            exchange.getOut().setBody(externalInteraction);
        }
    }


}
