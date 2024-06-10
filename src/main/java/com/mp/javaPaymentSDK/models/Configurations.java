package com.mp.javaPaymentSDK.models;

public class Configurations {

    private String baseUrl;

    public Configurations() {
    }

    public Configurations(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
