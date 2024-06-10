package com.mp.javaPaymentSDK.models.responses.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkFlowResponse {

    @JacksonXmlProperty(localName = "id")
    @JsonProperty("id")
    private String id;

    @JacksonXmlProperty(localName = "name")
    @JsonProperty("name")
    private String name;

    @JacksonXmlProperty(localName = "version")
    @JsonProperty("version")
    private String version;

    public WorkFlowResponse() {
    }

    public WorkFlowResponse(String id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
