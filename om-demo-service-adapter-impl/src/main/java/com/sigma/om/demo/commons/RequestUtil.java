package com.sigma.om.demo.commons;

import com.sigma.om.sdk.engine.interaction.Interaction;
import com.sigma.om.sdk.engine.interaction.InteractionState;
import com.sigma.om.sdk.order.OrderItem;
import com.sigma.om.sdk.soi.framework.SoiRequest;
import com.sigma.om.sdk.soi.framework.SoiRequestItem;
import com.sigma.om.sdk.soi.interaction.ExternalInteractionState;
import com.sigma.om.sdk.soi.interaction.IMExternalInteraction;
import com.sigma.om.soi.framework.SoiConstants;
import com.sigma.om.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil 
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);


    public static OrderItem getOrderItem(SoiRequest soiRequest,final String entityName,final String guid)
    {
        LOGGER.info(entityName);
        LOGGER.info(guid);
        final SoiRequestItem soiRequestItem=soiRequest.getRequestItems().stream().filter(soiRequestItem1 -> StringUtils.equalsIgnoreCase(
                soiRequestItem1.getItem().getCatalogEntity().getSpec().getName(), entityName
        ) || StringUtils.equalsIgnoreCase(soiRequestItem1.getItem().getCatalogEntity().getSpec().getElementGUID(), guid)).findFirst().orElse(null);
        LOGGER.info("soiRequestItem is ",soiRequestItem);
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("soiRequestItem {}" , JsonUtils.toPlainString(soiRequestItem));
        }
        return soiRequestItem != null ? soiRequestItem.getItem() : null;

    }

    public static String getCharactersticsValue(OrderItem orderItem,final String phoneNumber)
    {
        if(orderItem==null)
        {
            LOGGER.info("orderItem is null");
            return StringUtils.EMPTY;
        }else if (!orderItem.getCatalogEntity().isCharacteristicPopulated(phoneNumber))
        {
            LOGGER.info(phoneNumber + "entity not populated");
            return StringUtils.EMPTY;
        }else {
            return orderItem.getCatalogEntity().getEntity().getCharacteristic(phoneNumber).getFirstValue();
        }

    }

    public static void setErrorState(Interaction interaction, IMExternalInteraction soiExtInteraction, String errMsg) {

        LOGGER.info("Inside setErrorState Method ");

        interaction.setState(InteractionState.FAILED);

        interaction.setErrorCode(SoiConstants.ERROR_CODE);

        interaction.setErrorDescription(errMsg);

        soiExtInteraction.setState(ExternalInteractionState.FAILED);

        soiExtInteraction.setErrorCode(SoiConstants.ERROR_CODE);

        soiExtInteraction.setErrorDescription(errMsg);

        LOGGER.info("setErrorState Method has completed successfully");
    }
    public static void setErrorState(Interaction interaction, IMExternalInteraction soiExtInteraction, String errMsg,
                              String errorCode) {

        LOGGER.info("Inside setErrorState Method ");

        interaction.setState(InteractionState.FAILED);

        interaction.setErrorCode(errorCode);

        interaction.setErrorDescription(errMsg);

        soiExtInteraction.setState(ExternalInteractionState.FAILED);

        soiExtInteraction.setErrorCode(errorCode);

        soiExtInteraction.setErrorDescription(errMsg);

        LOGGER.info("setErrorState Method has completed successfully ");

    }


}
