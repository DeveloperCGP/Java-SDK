package com.mp.javaPaymentSDK.enums;

public enum Endpoints {

    H2H_ENDPOINT("https://checkout-stg.addonpayments.com/EPGCheckout/rest/online/pay", "https://checkout.addonpayments.com/EPGCheckout/rest/online/pay"),
    REFUND_ENDPOINT("https://checkout-stg.addonpayments.com/EPGCheckout/rest/online/rebate", "https://checkout.addonpayments.com/EPGCheckout/rest/online/rebate"),
    VOID_ENDPOINT("https://checkout-stg.addonpayments.com/EPGCheckout/rest/online/void", "https://checkout.addonpayments.com/EPGCheckout/rest/online/void"),
    CAPTURE_ENDPOINT("https://checkout-stg.addonpayments.com/EPGCheckout/rest/online/capture", "https://checkout.addonpayments.com/EPGCheckout/rest/online/capture"),
    CHARGE_ENDPOINT("https://epgjs-mep-stg.addonpayments.com/charge/v2", "https://epgjs-mep.addonpayments.com/charge/v2"),
    AUTH_ENDPOINT("https://epgjs-mep-stg.addonpayments.com/auth", "https://epgjs-mep.addonpayments.com/auth"),
    HOSTED_ENDPOINT("https://checkout-stg.addonpayments.com/EPGCheckout/rest/online/tokenize", "https://checkout.addonpayments.com/EPGCheckout/rest/online/tokenize");

    private final String stageEndpoint;
    private final String productionEndpoint;

    Endpoints(String stageEndpoint, String productionEndpoint) {
        this.stageEndpoint = stageEndpoint;
        this.productionEndpoint = productionEndpoint;
    }

    public String getEndpoint(Environment environment) {
        if (environment == Environment.PRODUCTION) {
            return productionEndpoint;
        }
        else {
            return stageEndpoint;
        }
    }
}
