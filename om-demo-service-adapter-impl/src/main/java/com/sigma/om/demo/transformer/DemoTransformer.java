package com.sigma.om.demo.transformer;

import com.sigma.om.demo.commons.CreateRequestUtil;
import com.sigma.om.sdk.soi.ContentType;
import com.sigma.om.sdk.soi.framework.SoiContext;
import com.sigma.om.sdk.soi.framework.SoiRequest;
import com.sigma.om.sdk.soi.interaction.ExternalInteractionBuilder;
import com.sigma.om.sdk.soi.interaction.IMExternalInteraction;
import com.sigma.om.soi.framework.SoiConfig;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

public class DemoTransformer implements Processor {

    private static final Logger LOGGER= LoggerFactory.getLogger(DemoTransformer.class);


    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.info(
                "============================ Demo Request Processor has started ================================");

        final SoiContext soiContext=SoiConfig.getSoIContextFromInBody(exchange);

        final SoiRequest soiRequest = soiContext.getSoiRequestList().get(0);

        final IMExternalInteraction soiExtInteraction = ExternalInteractionBuilder.make(soiRequest).build();

        final String externalRequest= CreateRequestUtil.generateRequest(soiRequest);

        LOGGER.info(externalRequest);

        soiExtInteraction.setRequest(externalRequest);

        soiExtInteraction.setContentType(String.valueOf(MediaType.APPLICATION_XML));

        final List<IMExternalInteraction> imExternalInteractions = new ArrayList<>();

        imExternalInteractions.add(soiExtInteraction);

        soiContext.setSoiExternalInteractions(imExternalInteractions);

        soiExtInteraction.setContentType(ContentType.XML.name());

        exchange.getIn().setBody(soiContext);


    }
}