package com.comerciaglobalpayments.javaPaymentSDK.models.quix_models;

import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixHostedRequest {

    private String merchantId = null;
    private String productId = null;
    private final PaymentSolutions paymentSolution = PaymentSolutions.quix;
    private String merchantTransactionId = null;
    private String amount = null;
    private final Currency currency = Currency.EUR;
    private final CountryCode country = CountryCode.ES;
    private String customerId = null;
    private String statusURL = null;
    private String successURL = null;
    private String errorURL = null;
    private String cancelURL = null;
    private String awaitingURL = null;
    private String firstName = null;
    private String lastName = null;
    private String customerEmail = null;
    private final CountryCode customerCountry = CountryCode.ES;
    private String customerNationalId = null;
    private String dob = null;
    private String ipAddress = null;
    private OperationTypes operationType = OperationTypes.DEBIT;
    private String paymentMethod = null;
    private String telephone = null;
    private Language language = null;

    public QuixHostedRequest() {
        merchantTransactionId = Utils.generateRandomNumber();
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws InvalidFieldException {
        String parsedAmount = Utils.parseAmount(amount);
        if (parsedAmount == null) {
            throw new InvalidFieldException("amount: Should Follow Format #.#### And Be Between 0 And 1000000");
        }
        this.amount = parsedAmount;
    }

    public CountryCode getCountry() {
        return country;
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

    public String getMerchantId() {
        return merchantId;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId(String merchantTransactionId) throws InvalidFieldException {
        if (merchantTransactionId.isBlank() || merchantTransactionId.length() > 45) {
            throw new InvalidFieldException("merchantTransactionId: Invalid Size, size must be (0 < merchantTransactionId <= 45)");
        }
        this.merchantTransactionId = merchantTransactionId;
    }

    public PaymentSolutions getPaymentSolution() {
        return paymentSolution;
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

    public String getSuccessURL() {
        return successURL;
    }

    public void setSuccessURL(String successURL) throws InvalidFieldException {
        if (!Utils.isValidURL(successURL)) {
            throw new InvalidFieldException("successURL");
        }
        this.successURL = successURL;
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

    public String getErrorURL() {
        return errorURL;
    }

    public void setErrorURL(String errorURL) throws InvalidFieldException {
        if (!Utils.isValidURL(errorURL)) {
            throw new InvalidFieldException("errorURL");
        }
        this.errorURL = errorURL;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public CountryCode getCustomerCountry() {
        return customerCountry;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCustomerNationalId() {
        return customerNationalId;
    }

    public void setCustomerNationalId(String customerNationalId) throws InvalidFieldException {
        if (customerNationalId.length() > 100)
        {
            throw new InvalidFieldException("customerNationalId: Invalid Size, size must be (customerNationalId <= 100)");
        }
        this.customerNationalId = customerNationalId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) throws InvalidFieldException {
        if (ipAddress.length() > 45 || !Utils.isValidIP(ipAddress))
        {
            throw new InvalidFieldException("ipAddress: must follow format IPv4 or IPv6 and max size is 45");
        }
        this.ipAddress = ipAddress;
    }

    public OperationTypes getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypes operationType) {
        this.operationType = operationType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws InvalidFieldException {
        if (telephone.length() > 45)
        {
            throw new InvalidFieldException("telephone: Invalid Size, size must be (telephone <= 45)");
        }
        this.telephone = telephone;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setCredentials(Credentials credentials) {
        this.merchantId = credentials.getMerchantId();
        this.productId = credentials.getProductId();
    }

    public Pair<Boolean, String> isMissingFields() {

        List<String> mandatoryFields = Arrays.asList(
                "merchantId", "productId", "paymentSolution", "merchantTransactionId",
                "amount", "currency", "country", "customerId",
                "awaitingURL", "statusURL", "successURL",
                "errorURL", "cancelURL", "firstName", "lastName", "customerEmail",
                "customerCountry","customerNationalId", "dob", "ipAddress"
        );

        return Utils.containsNull(
                QuixHostedRequest.class, this, mandatoryFields
        );
    }

    public Pair<Boolean, String> checkCredentials(Credentials credentials) {
        if (credentials.getApiVersion() < 0)
        {
            return new Pair<>(true, "apiVersion");
        }
        List<String> mandatoryFields = Arrays.asList(
                "merchantId", "productId", "environment"
        );

        return Utils.containsNull(
                Credentials.class, credentials, mandatoryFields
        );
    }
}
