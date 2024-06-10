package com.mp.javaPaymentSDK.models.responses.notification.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mp.javaPaymentSDK.enums.Currency;
import com.mp.javaPaymentSDK.enums.OperationTypes;
import com.mp.javaPaymentSDK.enums.PaymentSolutions;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.utils.Utils;

@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation {

    @JacksonXmlProperty(localName = "amount")
    @JsonProperty("amount")
    private double amount;
    @JacksonXmlProperty(localName = "currency")
    @JsonProperty("currency")
    private Currency currency;
    @JacksonXmlProperty(localName = "details")
    @JsonProperty("details")
    private String details;
    @JacksonXmlProperty(localName = "merchantTransactionId")
    @JsonProperty("merchantTransactionId")
    private String merchantTransactionId;
    @JacksonXmlProperty(localName = "paySolTransactionId")
    @JsonProperty("paySolTransactionId")
    private String paySolTransactionId;
    @JacksonXmlProperty(localName = "service")
    @JsonProperty("service")
    private String service;
    @JacksonXmlProperty(localName = "status")
    @JsonProperty("status")
    private String status;
    @JacksonXmlProperty(localName = "transactionId")
    @JsonProperty("transactionId")
    private String transactionId;
    @JacksonXmlProperty(localName = "respCode")
    @JsonProperty("respCode")
    private ResponseCode respCode;
    @JacksonXmlProperty(localName = "operationType")
    @JsonProperty("operationType")
    private OperationTypes operationType;
    @JacksonXmlProperty(localName = "paymentDetails")
    @JsonProperty("paymentDetails")
    private PaymentDetails paymentDetails;
    @JacksonXmlProperty(localName = "mpi")
    @JsonProperty("mpi")
    private Mpi mpi;
    @JacksonXmlProperty(localName = "paymentCode")
    @JsonProperty("paymentCode")
    private String paymentCode;
    @JacksonXmlProperty(localName = "paymentMessage")
    @JsonProperty("paymentMessage")
    private String paymentMessage;
    @JacksonXmlProperty(localName = "message")
    @JsonProperty("message")
    private String message;
    @JacksonXmlProperty(localName = "paymentMethod")
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    @JacksonXmlProperty(localName = "paymentSolution")
    @JsonProperty("paymentSolution")
    private PaymentSolutions paymentSolution;
    @JacksonXmlProperty(localName = "authCode")
    @JsonProperty("authCode")
    private String authCode;
    @JacksonXmlProperty(localName = "rad")
    @JsonProperty("rad")
    private String rad;
    @JacksonXmlProperty(localName = "radMessage")
    @JsonProperty("radMessage")
    private String radMessage;
    @JacksonXmlProperty(localName = "redirectionResponse")
    @JsonProperty("redirectionResponse")
    private String redirectionResponse;
    @JacksonXmlProperty(localName = "subscriptionPlan")
    @JsonProperty("subscriptionPlan")
    private String subscriptionPlan;

    public Operation() {
    }

    public Operation(double amount, Currency currency, String details, String merchantTransactionId, String paySolTransactionId, String service, String status, String transactionId, ResponseCode respCode, OperationTypes operationType, PaymentDetails paymentDetails, Mpi mpi, String paymentCode, String paymentMessage, String message, String paymentMethod, PaymentSolutions paymentSolution, String authCode, String rad, String radMessage, String redirectionResponse, String subscriptionPlan) {
        this.amount = amount;
        this.currency = currency;
        this.details = details;
        this.merchantTransactionId = merchantTransactionId;
        this.paySolTransactionId = paySolTransactionId;
        this.service = service;
        this.status = status;
        this.transactionId = transactionId;
        this.respCode = respCode;
        this.operationType = operationType;
        this.paymentDetails = paymentDetails;
        this.mpi = mpi;
        this.paymentCode = paymentCode;
        this.paymentMessage = paymentMessage;
        this.message = message;
        this.paymentMethod = paymentMethod;
        this.paymentSolution = paymentSolution;
        this.authCode = authCode;
        this.rad = rad;
        this.radMessage = radMessage;
        this.redirectionResponse = redirectionResponse;
        this.subscriptionPlan = subscriptionPlan;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId(String merchantTransactionId) {
        this.merchantTransactionId = merchantTransactionId;
    }

    public String getPaySolTransactionId() {
        return paySolTransactionId;
    }

    public void setPaySolTransactionId(String paySolTransactionId) {
        this.paySolTransactionId = paySolTransactionId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) throws InvalidFieldException {
        if (!Utils.isNumbersOnly(transactionId) || transactionId.length() > 100)
        {
            throw new InvalidFieldException("transactionId: Must be numbers only with size (transactionId <= 100)");
        }
        this.transactionId = transactionId;
    }

    public ResponseCode getRespCode() {
        return respCode;
    }

    public void setRespCode(ResponseCode respCode) {
        this.respCode = respCode;
    }

    public OperationTypes getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypes operationType) {
        this.operationType = operationType;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Mpi getMpi() {
        return mpi;
    }

    public void setMpi(Mpi mpi) {
        this.mpi = mpi;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentMessage() {
        return paymentMessage;
    }

    public void setPaymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentSolutions getPaymentSolution() {
        return paymentSolution;
    }

    public void setPaymentSolution(PaymentSolutions paymentSolution) {
        this.paymentSolution = paymentSolution;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getRad() {
        return rad;
    }

    public void setRad(String rad) {
        this.rad = rad;
    }

    public String getRadMessage() {
        return radMessage;
    }

    public void setRadMessage(String radMessage) {
        this.radMessage = radMessage;
    }

    public String getRedirectionResponse() {
        return redirectionResponse;
    }

    public void setRedirectionResponse(String redirectionResponse) {
        this.redirectionResponse = redirectionResponse;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
}
