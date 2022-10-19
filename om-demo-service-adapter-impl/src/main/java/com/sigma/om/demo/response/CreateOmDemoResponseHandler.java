package com.sigma.om.demo.response;

import com.sigma.om.demo.util.DemoHelper;
import com.sigma.om.sdk.engine.interaction.Interaction;
import com.sigma.om.sdk.engine.interaction.InteractionBuilder;
import com.sigma.om.sdk.order.Order;
import com.sigma.om.sdk.order.enrichment.change.EnrichmentInfo;
import com.sigma.om.sdk.repo.locator.OrderRepositoryLocator;
import com.sigma.om.sdk.soi.framework.SoiContext;
import com.sigma.om.sdk.soi.framework.SoiRequest;
import com.sigma.om.sdk.soi.interaction.IMExternalInteraction;
import com.sigma.om.soi.framework.SoiConfig;
import com.sigma.om.soi.interaction.ExternalInteractionImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class CreateOmDemoResponseHandler implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOmDemoResponseHandler.class.getName());
    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.info(
                "============================ In OM Demo  ResponseHandler has started ================================");
        final String CreateOmDemoClassName = CreateOmDemoResponseHandler.class.getName();

        final SoiContext soiContext = SoiConfig.getSoIContextFromInBody(exchange);

        final SoiRequest soiRequest = soiContext.getSoiRequestList().get(0);

        final IMExternalInteraction imExternalInteraction = soiContext.getSoiExternalInteraction().get(0);

        final Object externalIntGeneric = soiContext.getSoiExternalInteraction().get(0);

        final ExternalInteractionImpl externalInteraction = (ExternalInteractionImpl) externalIntGeneric;

        final Response response = (Response) imExternalInteraction.getResponses().get(0).getResponse();

        final String responseString = DemoHelper.getResponseString(response);

        LOGGER.info(" Sync Response String : {}", responseString);

        final Interaction interaction = InteractionBuilder.make(externalInteraction).build();

        externalInteraction.addResponse(DemoHelper.setExternalResponse(imExternalInteraction, responseString));

        final Order order = OrderRepositoryLocator.getInstance().getOrder(soiRequest.getOrderRef().getKey());
        EnrichmentInfo enrichmentInfo = soiRequest.getEnrichmentInfo();

        LOGGER.info("Response in responseHandler--> {}", response.getStatus());



    }
}
