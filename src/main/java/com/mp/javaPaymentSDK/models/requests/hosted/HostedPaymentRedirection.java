package com.mp.javaPaymentSDK.models.requests.hosted;

import com.mp.javaPaymentSDK.enums.*;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.models.Credentials;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostedPaymentRedirection {

    private String merchantId = null;
    private String productId = null;
    private PaymentSolutions paymentSolution = null;
    private OperationTypes operationType = OperationTypes.DEBIT;
    private String merchantTransactionId = null;
    private String amount = null;
    private Currency currency = null;
    private CountryCode country = null;
    private String customerId = null;
    private String statusURL = null;
    private String successURL = null;
    private String errorURL = null;
    private String cancelURL = null;
    private String awaitingURL = null;
    private Language language = null;
    private String referenceId = null;
    private boolean printReceipt = false;
    private TransactionType type = TransactionType.ECOM;
    private boolean autoCapture = true;
    private String description = null;
    private boolean forceTokenRequest = false;
    private boolean showRememberMe = false;
    private List<Pair<String, String>> merchantParams = null;

    public HostedPaymentRedirection() {
        merchantTransactionId = Utils.generateRandomNumber();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws InvalidFieldException {
        String parsedAmount = Utils.parseAmount(amount);
        if (parsedAmount == null)
        {
            throw new InvalidFieldException("amount: Should Follow Format #.#### And Be Between 0 And 1000000");
        }
        this.amount = parsedAmount;
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
        if (customerId.length() > 80)
        {
            throw new InvalidFieldException("customerId: Invalid Size, size must be (0 < customerId <= 80)");
        }
        this.customerId = customerId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId(String merchantTransactionId) throws InvalidFieldException {
        if (merchantTransactionId.isBlank() || merchantTransactionId.length() > 45)
        {
            throw new InvalidFieldException("merchantTransactionId: Invalid Size, size must be (0 < merchantTransactionId <= 45)");
        }
        this.merchantTransactionId = merchantTransactionId;
    }

    public PaymentSolutions getPaymentSolution() {
        return paymentSolution;
    }

    public void setPaymentSolution(PaymentSolutions paymentSolution) {
        this.paymentSolution = paymentSolution;
    }

    public String getStatusURL() {
        return statusURL;
    }

    public void setStatusURL(String statusURL) throws InvalidFieldException {
        if (!Utils.isValidURL(statusURL)) {
            throw new InvalidFieldException("statusURL");
        }
        this.statusURL = statusURL;
    }

    public String getErrorURL() {
        return errorURL;
    }

    public void setErrorURL(String errorURL) throws InvalidFieldException {
        if (!Utils.isValidURL(errorURL)) {
            throw new InvalidFieldException("errorURL");
        }
        this.errorURL = errorURL;
    }

    public String getSuccessURL() {
        return successURL;
    }

    public void setSuccessURL(String successURL) throws InvalidFieldException {
        if (!Utils.isValidURL(successURL)) {
            throw new InvalidFieldException("successURL");
        }
        this.successURL = successURL;
    }

    public String getCancelURL() {
        return cancelURL;
    }

    public void setCancelURL(String cancelURL) throws InvalidFieldException {
        if (!Utils.isValidURL(cancelURL)) {
            throw new InvalidFieldException("cancelURL");
        }
        this.cancelURL = cancelURL;
    }

    public String getAwaitingURL() {
        return awaitingURL;
    }

    public void setAwaitingURL(String awaitingURL) throws InvalidFieldException {
        if (!Utils.isValidURL(awaitingURL)) {
            throw new InvalidFieldException("awaitingURL");
        }
        this.awaitingURL = awaitingURL;
    }

    public String getProductId() {
        return productId;
    }

    public OperationTypes getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypes operationType) {
        this.operationType = operationType;
    }

    public boolean isForceTokenRequest() {
        return forceTokenRequest;
    }

    public void setForceTokenRequest(boolean forceTokenRequest) {
        this.forceTokenRequest = forceTokenRequest;
    }

    public boolean isShowRememberMe() {
        return showRememberMe;
    }

    public void setShowRememberMe(boolean showRememberMe) {
        this.showRememberMe = showRememberMe;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) throws InvalidFieldException {
        if (referenceId.length() != 12) {
            throw new InvalidFieldException("referenceId: Invalid Size, size must be (referenceId = 12)");
        }
        this.referenceId = referenceId;
    }

    public boolean isPrintReceipt() {
        return printReceipt;
    }

    public void setPrintReceipt(boolean printReceipt) {
        this.printReceipt = printReceipt;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public boolean isAutoCapture() {
        return autoCapture;
    }

    public void setAutoCapture(boolean autoCapture) {
        this.autoCapture = autoCapture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidFieldException {
        if (description.length() > 1000) {
            throw new InvalidFieldException("description: Invalid Size, size must be (description <= 1000)");
        }
        this.description = description;
    }

    public void setMerchantParameters(List<Pair<String, String>> merchantParams) throws InvalidFieldException {
        if (this.merchantParams == null) {
            this.merchantParams = merchantParams;
        }
        else {
            this.merchantParams.addAll(merchantParams);
        }
        if (Utils.merchantParamsQuery(this.merchantParams).length() > 500) {
            throw new InvalidFieldException("merchantParams: Invalid Size, Size Must Be merchantParams <= 100");
        }
    }

    public List<Pair<String, String>> getMerchantParameters() {
        return merchantParams;
    }

    public void setMerchantParameter(String key, String value) throws InvalidFieldException {
        if (merchantParams == null) {
            this.merchantParams = new ArrayList<>();
        }
        this.merchantParams.add(new Pair<>(key, value));
        if (Utils.merchantParamsQuery(this.merchantParams).length() > 500) {
            throw new InvalidFieldException("merchantParams: Invalid Size, Size Must Be merchantParams <= 100");
        }
    }

    public void setCredentials(Credentials credentials) {
        this.merchantId = credentials.getMerchantId();
        this.productId = credentials.getProductId();
    }

    public Pair<Boolean, String> isMissingField() {
        List<String> mandatoryFields = Arrays.asList(
                "currency", "merchantId", "productId", "paymentSolution",
                "operationType", "merchantTransactionId", "amount",
                "country", "customerId", "statusURL", "successURL", "errorURL",
                "cancelURL", "awaitingURL"
        );

        return Utils.containsNull(
                HostedPaymentRedirection.class, this, mandatoryFields
        );
    }

    public Pair<Boolean, String> checkCredentials(Credentials credentials) {
        if (credentials.getApiVersion() < 0)
        {
            return new Pair<>(true, "apiVersion");
        }
        List<String> mandatoryFields = Arrays.asList(
                "merchantId", "productId", "merchantPass", "environment"
        );

        return Utils.containsNull(
                Credentials.class, credentials, mandatoryFields
        );
    }
}
