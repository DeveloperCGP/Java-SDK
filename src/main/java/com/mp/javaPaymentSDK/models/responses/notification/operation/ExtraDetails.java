package com.mp.javaPaymentSDK.models.responses.notification.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtraDetails {

    @JacksonXmlProperty(localName = "nemuruTxnId")
    @JsonProperty("nemuruTxnId")
    private String nemuruTxnId;
    @JacksonXmlProperty(localName = "nemuruCartHash")
    @JsonProperty("nemuruCartHash")
    private String nemuruCartHash;
    @JacksonXmlProperty(localName = "nemuruAuthToken")
    @JsonProperty("nemuruAuthToken")
    private String nemuruAuthToken;
    @JacksonXmlProperty(localName = "nemuruDisableFormEdition")
    @JsonProperty("nemuruDisableFormEdition")
    private String nemuruDisableFormEdition;
    @JacksonXmlProperty(localName = "status")
    @JsonProperty("status")
    private String status;
    @JacksonXmlProperty(localName = "disableFormEdition")
    @JsonProperty("disableFormEdition")
    private String disableFormEdition;

    public ExtraDetails() {
    }

    public ExtraDetails(String nemuruTxnId, String nemuruCartHash, String nemuruAuthToken, String nemuruDisableFormEdition, String status, String disableFormEdition) {
        this.nemuruTxnId = nemuruTxnId;
        this.nemuruCartHash = nemuruCartHash;
        this.nemuruAuthToken = nemuruAuthToken;
        this.nemuruDisableFormEdition = nemuruDisableFormEdition;
        this.status = status;
        this.disableFormEdition = disableFormEdition;
    }

    public String getNemuruTxnId() {
        return nemuruTxnId;
    }

    public void setNemuruTxnId(String nemuruTxnId) {
        this.nemuruTxnId = nemuruTxnId;
    }

    public String getNemuruCartHash() {
        return nemuruCartHash;
    }

    public void setNemuruCartHash(String nemuruCartHash) {
        this.nemuruCartHash = nemuruCartHash;
    }

    public String getNemuruAuthToken() {
        return nemuruAuthToken;
    }

    public void setNemuruAuthToken(String nemuruAuthToken) {
        this.nemuruAuthToken = nemuruAuthToken;
    }

    public String getNemuruDisableFormEdition() {
        return nemuruDisableFormEdition;
    }

    public void setNemuruDisableFormEdition(String nemuruDisableFormEdition) {
        this.nemuruDisableFormEdition = nemuruDisableFormEdition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisableFormEdition() {
        return disableFormEdition;
    }

    public void setDisableFormEdition(String disableFormEdition) {
        this.disableFormEdition = disableFormEdition;
    }
}
