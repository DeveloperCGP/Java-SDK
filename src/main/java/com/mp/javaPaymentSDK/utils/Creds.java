package com.mp.javaPaymentSDK.utils;

import com.mp.javaPaymentSDK.enums.Environment;

public class Creds {

    public static String merchantPass = System.getenv().getOrDefault("MERCHANT_PASS", null);
    public static String merchantKey = System.getenv().getOrDefault("MERCHANT_KEY", null);
    public static String merchantId = System.getenv().getOrDefault("MERCHANT_ID", null);
    public static Environment environment = Environment.getEnvironment(System.getenv().getOrDefault("ENVIRONMENT", null));
    public static String productId = System.getenv().getOrDefault("PRODUCT_ID", null);
    public static String productIdAccommodation = System.getenv().getOrDefault("PRODUCT_ID_ACCOMMODATION", null);
    public static String productIdItem = System.getenv().getOrDefault("PRODUCT_ID_ITEM", null);
    public static String productIdService = System.getenv().getOrDefault("PRODUCT_ID_SERVICE", null);
    public static String productIdFlight = System.getenv().getOrDefault("PRODUCT_ID_FLIGHT", null);
    public static String statusUrl = System.getenv().getOrDefault("STATUS_URL", null);
    public static String successUrl = System.getenv().getOrDefault("SUCCESS_URL", null);
    public static String cancelUrl = System.getenv().getOrDefault("CANCEL_URL", null);
    public static String awaitingUrl = System.getenv().getOrDefault("AWAITING_URL", null);
    public static String errorUrl = System.getenv().getOrDefault("ERROR_URL", null);
}
