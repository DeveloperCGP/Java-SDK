package com.mp.javaPaymentSDK.models.responses.notification.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mpi {

    @JacksonXmlProperty(localName = "acsTransID")
    @JsonProperty("acsTransID")
    private String acsTransID;
    @JacksonXmlProperty(localName = "authMethod")
    @JsonProperty("authMethod")
    private String authMethod;
    @JacksonXmlProperty(localName = "authTimestamp")
    @JsonProperty("authTimestamp")
    private String authTimestamp;
    @JacksonXmlProperty(localName = "authenticationStatus")
    @JsonProperty("authenticationStatus")
    private String authenticationStatus;
    @JacksonXmlProperty(localName = "cavv")
    @JsonProperty("cavv")
    private String cavv;
    @JacksonXmlProperty(localName = "eci")
    @JsonProperty("eci")
    private String eci;
    @JacksonXmlProperty(localName = "messageVersion")
    @JsonProperty("messageVersion")
    private String messageVersion;
    @JacksonXmlProperty(localName = "threeDSSessionData")
    @JsonProperty("threeDSSessionData")
    private String threeDSSessionData;
    @JacksonXmlProperty(localName = "threeDSv2Token")
    @JsonProperty("threeDSv2Token")
    private String threeDSv2Token;

    public Mpi() {
    }

    public Mpi(String acsTransID, String authMethod, String authTimestamp, String authenticationStatus, String cavv, String eci, String messageVersion, String threeDSSessionData, String threeDSv2Token) {
        this.acsTransID = acsTransID;
        this.authMethod = authMethod;
        this.authTimestamp = authTimestamp;
        this.authenticationStatus = authenticationStatus;
        this.cavv = cavv;
        this.eci = eci;
        this.messageVersion = messageVersion;
        this.threeDSSessionData = threeDSSessionData;
        this.threeDSv2Token = threeDSv2Token;
    }

    public String getAcsTransID() {
        return acsTransID;
    }

    public void setAcsTransID(String acsTransID) {
        this.acsTransID = acsTransID;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getAuthTimestamp() {
        return authTimestamp;
    }

    public void setAuthTimestamp(String authTimestamp) {
        this.authTimestamp = authTimestamp;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getCavv() {
        return cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public String getThreeDSSessionData() {
        return threeDSSessionData;
    }

    public void setThreeDSSessionData(String threeDSSessionData) {
        this.threeDSSessionData = threeDSSessionData;
    }

    public String getThreeDSv2Token() {
        return threeDSv2Token;
    }

    public void setThreeDSv2Token(String threeDSv2Token) {
        this.threeDSv2Token = threeDSv2Token;
    }
}
