package com.sigma.om.demo.outbound;

import com.sigma.om.demo.commons.RequestUtil;
import com.sigma.om.demo.util.DemoHelper;
import com.sigma.om.sdk.engine.interaction.Interaction;
import com.sigma.om.sdk.engine.interaction.InteractionBuilder;
import com.sigma.om.sdk.engine.interaction.InteractionState;
import com.sigma.om.sdk.engine.interaction.locator.InteractionResponseSenderLocator;
import com.sigma.om.sdk.soi.framework.SoiContext;
import com.sigma.om.sdk.soi.framework.SoiRequest;
import com.sigma.om.sdk.soi.interaction.ExternalInteractionState;
import com.sigma.om.sdk.soi.interaction.IMExternalInteraction;
import com.sigma.om.soi.framework.SoiConfig;
import com.sigma.om.soi.interaction.ExternalInteractionImpl;
import com.sigma.om.soi.interaction.ExternalResponseImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class DemoOutbound implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoOutbound.class);
    @Override
    public void process(Exchange exchange) throws Exception
    {

        LOGGER.info("Demo Outbound Service Started");

        final SoiContext soiContext= SoiConfig.getSoIContextFromInBody(exchange);

        final SoiRequest soiRequest=soiContext.getSoiRequestList().get(0);

        // Get imExternalInteraction from soiContext
      //  final IMExternalInteraction imExternalInteraction = soiContext.getSoiExternalInteraction().get(0);

        final ExternalInteractionImpl externalInteraction = (ExternalInteractionImpl) soiContext.getSoiExternalInteraction().get(0);

       /* final String responseString = DemoHelper
                .getResponseString(imExternalInteraction.getResponses().get(0).getResponse());*/

        final String responseString="<m:HomeNetworkResponse>"+
                "<m:Network>"+"pune"+"</m:Network>"+
                        "</m:HomeNetworkResponse>";

        LOGGER.info(responseString);

        final ExternalResponseImpl externalResponseImpl = new ExternalResponseImpl();


        externalResponseImpl.setExtInteractionId(soiContext.getSoiExternalInteraction().get(0).getId());
        externalResponseImpl.setResponseString(responseString);
        soiContext.getSoiExternalInteraction().get(0).addResponse(externalResponseImpl);

        final Interaction interaction= InteractionBuilder.make(externalInteraction).build();

        if (StringUtils.containsIgnoreCase(responseString, "error") || StringUtils.containsIgnoreCase(responseString, "exception"))
        {
            RequestUtil.setErrorState(interaction,externalInteraction,"error occured");
        }else if(StringUtils.isEmpty(responseString))
        {
            RequestUtil.setErrorState(interaction,externalInteraction,"empty response recevied");
        }else
        {
            DemoHelper.doEnrichment(soiRequest,responseString,interaction);
            interaction.setState(InteractionState.DONE);
            externalInteraction.setState(ExternalInteractionState.DONE);
        }
        InteractionResponseSenderLocator.getInstance().notify(interaction);

        exchange.getIn().setBody(soiContext);
    }
}
