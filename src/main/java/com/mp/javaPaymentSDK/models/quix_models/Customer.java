package com.mp.javaPaymentSDK.models.quix_models;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.Locale;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;

public class Customer {
    private Locale locale = null;
    private String userAgent = null;
    private String title = null;
    @SerializedName("document_expiration_date")
    private String documentExpirationDate = null;
    @SerializedName("logged_in")
    private boolean loggedIn = false;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) throws InvalidFieldException {
        if (userAgent.length() > 256)
        {
            throw new InvalidFieldException("userAgent: Invalid Size, Size Must Be (userAgent <= 256)");
        }
        this.userAgent = userAgent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentExpirationDate() {
        return documentExpirationDate;
    }

    public void setDocumentExpirationDate(String documentExpirationDate) {
        this.documentExpirationDate = documentExpirationDate;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
