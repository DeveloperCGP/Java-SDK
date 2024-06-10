package com.mp.javaPaymentSDK.models.quix_models;

import kotlin.Pair;

public class QuixJSRequest extends QuixHostedRequest {

    private String prepayToken = null;

    public String getPrepayToken() {
        return prepayToken;
    }

    public void setPrepayToken(String prepayToken) {
        this.prepayToken = prepayToken;
    }

    public Pair<Boolean, String> isMissingFields() {
        if (prepayToken == null || prepayToken.isBlank()) {
            return new Pair<>(true, "prepayToken");
        }

        return super.isMissingFields();
    }
}
