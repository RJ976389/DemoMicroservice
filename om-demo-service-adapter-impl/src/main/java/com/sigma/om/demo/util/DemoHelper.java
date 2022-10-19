package com.sigma.om.demo.util;

import com.sigma.om.demo.commons.RequestUtil;
import com.sigma.om.demo.transformer.DemoTransformer;
import com.sigma.om.demo.adapter.DemoConstants;
import com.sigma.om.sdk.engine.interaction.Interaction;
import com.sigma.om.sdk.order.OrderItem;
import com.sigma.om.sdk.order.enrichment.change.*;
import com.sigma.om.sdk.soi.framework.SoiRequest;
import com.sigma.om.sdk.soi.interaction.IMExternalInteraction;
import com.sigma.om.soi.interaction.ExternalResponseImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sigma.om.demo.adapter.DemoConstants.*;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;


public class DemoHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(DemoHelper.class);

    public static String getResponseString(Object response) throws IOException {
        if (response == null) {
            LOGGER.warn("Empty response received");
            return "";
        }

        if (response instanceof InputStream) {
            final StringWriter writer = new StringWriter();
            IOUtils.copy((InputStream) response, writer, StandardCharsets.UTF_8);
            return writer.toString();
        } else {
            return response.toString();
        }
    }
    public static void doEnrichment(SoiRequest soiRequest, String responseString, Interaction interaction)
    {
        final EnrichmentInfo enrichmentInfo = soiRequest.getEnrichmentInfo();

        final String demoValue= StringUtils.substringBetween(responseString,"<m:Network>","</m:Network>");

        LOGGER.info("demovalue is",demoValue);

        final OrderItem orderItem= RequestUtil.getOrderItem(soiRequest, Mobile_Voice_Identity_RFS, Mobile_Voice_Identity_RFS_GUID);

        if (orderItem == null) {
            LOGGER.info(Mobile_Voice_Identity_RFS + " entity not found");
            return;
        }

        LOGGER.info("orderItem is",orderItem);

        final OrderChange opOrderChange = new OrderChange();
        final OrderItemChange itemChange=new OrderItemChange();
        final CatalogEntityChange catalogEntityChange=new CatalogEntityChange();
        itemChange.setKey(orderItem.getKey());

        final CharacteristicChange characteristicChange=new CharacteristicChange();
        characteristicChange.setName(Home_Mobile_Network);
        characteristicChange.addValue(demoValue);

        catalogEntityChange.addCharacteristic(characteristicChange);

        itemChange.setCatalogEntity(catalogEntityChange);

        opOrderChange.addOrderItem(itemChange);

        enrichmentInfo.setOpOrderChange(opOrderChange);

        interaction.setEnrichmentInfo(enrichmentInfo);


    }

    public static ExternalResponseImpl setExternalResponse(IMExternalInteraction imExternalInteraction,
                                                           String responseString) {

        final ExternalResponseImpl soiExtResponse = new ExternalResponseImpl();

        soiExtResponse.setExtInteractionId(imExternalInteraction.getId());

        soiExtResponse.setResponseString(responseString);

        soiExtResponse.setResponse(responseString);

        return soiExtResponse;
    }
}
