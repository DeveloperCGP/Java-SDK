package com.mp.javaPaymentSDK.enums;

public enum Environment {
    STAGING,
    PRODUCTION;

    public static Environment getEnvironment(String stringValue) {
        for (Environment environment : Environment.values()) {
            if (environment.name().equalsIgnoreCase(stringValue)) {
                return environment;
            }
        }
        return null;
    }
}
