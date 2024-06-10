package com.mp.javaPaymentSDK.models.quix_models;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConfirmationCartData {
    private String url = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url != null && !url.isBlank()) {
            this.url = URLEncoder.encode(url, StandardCharsets.UTF_8);
        } else {
            this.url = null;
        }
    }
}
