package com.mp.javaPaymentSDK.models.responses.notification.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCode {

    @JacksonXmlProperty(localName = "code")
    @JsonProperty("code")
    private String code;
    @JacksonXmlProperty(localName = "message")
    @JsonProperty("message")
    private String message;
    @JacksonXmlProperty(localName = "uuid")
    @JsonProperty("uuid")
    private String uuid;

    public ResponseCode() {
    }

    public ResponseCode(String code, String message, String uuid) {
        this.code = code;
        this.message = message;
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
