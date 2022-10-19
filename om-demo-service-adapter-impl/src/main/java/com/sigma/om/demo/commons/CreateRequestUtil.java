package com.sigma.om.demo.commons;

import com.sigma.om.sdk.order.OrderItem;
import com.sigma.om.sdk.soi.framework.SoiRequest;
import com.sigma.om.soi.framework.exception.SoiRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sigma.om.demo.adapter.DemoConstants.*;


public class CreateRequestUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(CreateRequestUtil.class);
    public static String generateRequest(SoiRequest soiRequest)
    {
        final OrderItem orderItem = RequestUtil.getOrderItem(soiRequest, Mobile_Voice_CFSS, Mobile_Voice_CFSS_GUID);
        LOGGER.info(String.valueOf(orderItem));
        if(orderItem== null)
        {
            throw new SoiRuntimeException("default",Mobile_Voice_CFSS +"entity");
        }

        final String phoneNumber=RequestUtil.getCharactersticsValue(orderItem,Phone_Number);
        LOGGER.info("phoneNumebr {}", phoneNumber);
        return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
                + "    <soap:Body xmlns:m=\"http://www.example.org/stock\">\r\n" + "    	<m:GetHomeNetwork>\r\n"
                + "      		<m:PhoneNumber>" + phoneNumber + "</m:PhoneNumber>\r\n"
                + "   	 </m:GetHomeNetwork>\r\n" + "</soap:Body>\r\n" + "</soap:Envelope>  \r\n" + "";
    }
}
