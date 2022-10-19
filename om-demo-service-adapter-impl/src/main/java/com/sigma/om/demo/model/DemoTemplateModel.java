package com.sigma.om.demo.model;

import com.sigma.om.sdk.order.Order;
import com.sigma.om.sdk.soi.framework.SoiRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DemoTemplateModel
{
    private Map<String, String> environmentConfiguration = new HashMap<>();

    private Map<String, String> orderHeader = new HashMap<>();

    private Map<String, Map<String, Set<String>>> orderItemCharacteristics = new HashMap<>();

    private String timestampInMilliSec;

    private String orderKey;

    private String customerId;

    private Order order;

    private SoiRequest soiRequest;

    public Order getOrder(){
        return order;
    }

    public void setOrder(Order order){
        this.order = order;
    }

    public SoiRequest getSoiRequest(){
        return soiRequest;
    }

    public void setSoiRequest(SoiRequest soiRequest){
        this.soiRequest = soiRequest;
    }

    public Map<String, String> getEnvironmentConfiguration() {
        return environmentConfiguration;
    }

    public void setEnvironmentConfiguration(Map<String, String> environmentConfiguration) {
        this.environmentConfiguration = environmentConfiguration;
    }

    public Map<String, String> getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(Map<String, String> orderHeader) {
        this.orderHeader = orderHeader;
    }

    public Map<String, Map<String, Set<String>>> getOrderItemCharacteristics() {
        return orderItemCharacteristics;
    }

    public void setOrderItemCharacteristics(Map<String, Map<String, Set<String>>> orderItemCharacteristics) {
        this.orderItemCharacteristics = orderItemCharacteristics;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "requestModel [environmentConfiguration=" + environmentConfiguration + ", orderItemCharacteristics="
                + orderItemCharacteristics + ", orderHeader=" + orderHeader + "]";
    }

    public String getTimestampInMillisec() {
        return timestampInMilliSec;
    }

    public void setTimestampInMilliSec(String timesstampInMilliSec) {
        this.timestampInMilliSec = timesstampInMilliSec;
    }

}
