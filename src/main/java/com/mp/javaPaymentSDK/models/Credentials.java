package com.mp.javaPaymentSDK.models;

import com.mp.javaPaymentSDK.enums.Environment;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.utils.Utils;

import java.util.regex.Pattern;

public class Credentials {

    private String merchantId;
    private String merchantKey;
    private String merchantPass;
    private Environment environment;
    private String productId;
    private int apiVersion = -1;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) throws InvalidFieldException {
        if (merchantId == null || !Utils.isNumbersOnly(merchantId) || merchantId.length() < 4 || merchantId.length() > 7)
        {
            throw new InvalidFieldException("merchantId: Should be numbers in size 4 <= merchantId <= 7");
        }
        this.merchantId = merchantId;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) throws InvalidFieldException {
        if (!Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$").matcher(merchantKey).matches())
        {
            throw new InvalidFieldException("merchantKey: Must be in UUID format");
        }
        this.merchantKey = merchantKey;
    }

    public String getMerchantPass() {
        return merchantPass;
    }

    public void setMerchantPass(String merchantPass) {
        this.merchantPass = merchantPass;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) throws InvalidFieldException {
        if (!Utils.isNumbersOnly(productId) || productId.length() < 6 || productId.length() > 11)
        {
            throw new InvalidFieldException("productId: Should be numbers in size 6 <= productId <= 11");
        }
        this.productId = productId;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(int apiVersion) throws InvalidFieldException {
        if (apiVersion < 0)
        {
            throw new InvalidFieldException("apiVersion: Should be (apiVersion > 0)");
        }
        this.apiVersion = apiVersion;
    }
}
