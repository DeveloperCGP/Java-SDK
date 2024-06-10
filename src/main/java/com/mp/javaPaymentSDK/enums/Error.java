package com.mp.javaPaymentSDK.enums;

public enum Error {

    NETWORK_ERROR("Network Error Occurred"),
    INVALID_RESPONSE_RECEIVED("Invalid Response Received"),
    INVALID_AMOUNT("Invalid Amount Received"),
    MISSING_PARAMETER("Missing Parameter"),
    CLIENT_ERROR("Client Error Occurred"),
    SERVER_ERROR("Server Error Occurred"),
    INVALID_URL("Invalid URL Provided");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
