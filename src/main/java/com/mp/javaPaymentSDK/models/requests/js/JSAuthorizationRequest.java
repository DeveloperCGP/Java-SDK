package com.mp.javaPaymentSDK.models.requests.js;

import com.mp.javaPaymentSDK.enums.CountryCode;
import com.mp.javaPaymentSDK.enums.Currency;
import com.mp.javaPaymentSDK.enums.OperationTypes;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.models.Credentials;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class JSAuthorizationRequest {

    private String merchantId = null;
    private String merchantKey = null;
    private String productId = null;
    private Currency currency = null;
    private CountryCode country = null;
    private String customerId = null;
    private OperationTypes operationType = null;
    private boolean anonymousCustomer = false;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getProductId() {
        return productId;
    }

    public CountryCode getCountry() {
        return country;
    }

    public void setCountry(CountryCode country) {
        this.country = country;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) throws InvalidFieldException {
        if (customerId == null || customerId.isEmpty() || customerId.length() > 80) {
            throw new InvalidFieldException("customerId: Invalid Size, size must be (0 < customerId <= 80)");
        }
        this.customerId = customerId;
    }

    public OperationTypes getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypes operationType) {
        this.operationType = operationType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public boolean isAnonymousCustomer() {
        return anonymousCustomer;
    }

    public void setAnonymousCustomer(boolean anonymousCustomer) {
        this.anonymousCustomer = anonymousCustomer;
    }

    public void setCredentials(Credentials credentials) {
        this.merchantId = credentials.getMerchantId();
        this.productId = credentials.getProductId();
        this.merchantKey = credentials.getMerchantKey();
    }

    public Pair<Boolean, String> isMissingField() {
        List<String> mandatoryFields = Arrays.asList(
                "merchantId", "productId", "merchantKey",
                "currency", "country", "customerId",
                "operationType"
        );

        return Utils.containsNull(
                JSAuthorizationRequest.class, this, mandatoryFields
        );
    }

    public Pair<Boolean, String> checkCredentials(Credentials credentials) {
        if (credentials.getApiVersion() < 0) {
            return new Pair<>(true, "apiVersion");
        }
        List<String> mandatoryFields = Arrays.asList(
                "merchantId", "productId", "merchantKey", "environment"
        );

        return Utils.containsNull(
                Credentials.class, credentials, mandatoryFields
        );
    }
}
