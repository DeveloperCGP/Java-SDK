package com.mp.javaPaymentSDK.models.responses.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.enums.TransactionResult;
import com.mp.javaPaymentSDK.models.responses.notification.operation.Operation;

import java.util.List;

@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    @JacksonXmlProperty(localName = "message")
    @JsonProperty("message")
    private String message;

    @JacksonXmlProperty(localName = "status")
    @JsonProperty("status")
    private String status;

    @JacksonXmlElementWrapper(localName = "operations")
    @JacksonXmlProperty(localName = "operation")
    @JsonProperty("operationsArray")
    @SerializedName("operationsArray")
    private List<Operation> operations;

    @JacksonXmlProperty(localName = "workFlowResponse")
    @JsonProperty("workFlowResponse")
    private WorkFlowResponse workFlowResponse;

    public Notification() {
    }

    public Notification(String message, String status, List<Operation> operations, WorkFlowResponse workFlowResponse) {
        this.message = message;
        this.status = status;
        this.operations = operations;
        this.workFlowResponse = workFlowResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public WorkFlowResponse getWorkFlowResponse() {
        return workFlowResponse;
    }

    public void setWorkFlowResponse(WorkFlowResponse workFlowResponse) {
        this.workFlowResponse = workFlowResponse;
    }

    public boolean isLastNotification() {
        return operations.get(operations.size() - 1).getStatus().equalsIgnoreCase("SUCCESS") || operations.get(operations.size() - 1).getStatus().equalsIgnoreCase("ERROR");
    }

    public String getRedirectUrl() {
        String redirectionUrl = operations.get(operations.size() - 1).getRedirectionResponse();
        if (redirectionUrl != null) {
            return redirectionUrl.replace("redirect:", "");
        }
        return null;
    }

    public String getNemuruCartHash(){
       return operations.get(0).getPaymentDetails().getExtraDetails().getNemuruCartHash();
    }

    public String getNemuruAuthToken(){
        return operations.get(0).getPaymentDetails().getExtraDetails().getNemuruAuthToken();
    }

    public String getMerchantTransactionId() {
        Operation operation = operations.get(operations.size() - 1);
        if (operation != null) {
            return operation.getMerchantTransactionId();
        }

        return null;
    }

    public TransactionResult getTransactionResult(){
        Operation operation = operations.get(operations.size() - 1);
        if (operation != null) {
            return TransactionResult.getTransactionResult(operation.getStatus());
        }

        return TransactionResult.ERROR;
    }

    public String getDisableFormEdition() {
        Operation operation = operations.get(operations.size() - 1);
        if (operation != null) {
            return operation.getPaymentDetails().getExtraDetails().getDisableFormEdition();
        }

        return null;
    }
}
