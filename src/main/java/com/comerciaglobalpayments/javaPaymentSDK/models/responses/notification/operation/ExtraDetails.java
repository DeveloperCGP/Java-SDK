package com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.operation;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement
public class ExtraDetails {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "entry")
    public List<Entry> entry;

    public ExtraDetails() {
    }

    public ExtraDetails(List<Entry> entry) {
        this.entry = entry;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public String getNemuruTxnId() {
        for (Entry value : entry) {
            if (value.getKey().equalsIgnoreCase("nemuruTxnId")) {
                return value.getValue();
            }
        }
        return null;
    }


    public String getNemuruCartHash() {
        for (Entry value : entry) {
            if (value.getKey().equalsIgnoreCase("nemuruCartHash")) {
                return value.getValue();
            }
        }
        return null;
    }

    public String getNemuruAuthToken() {
        for (Entry value : entry) {
            if (value.getKey().equalsIgnoreCase("nemuruAuthToken")) {
                return value.getValue();
            }
        }
        return null;
    }

    public String getNemuruDisableFormEdition() {
        for (Entry value : entry) {
            if (value.getKey().equalsIgnoreCase("nemuruDisableFormEdition")) {
                return value.getValue();
            }
        }
        return null;
    }

    public String getStatus() {
        for (Entry value : entry) {
            if (value.getKey().equalsIgnoreCase("status")) {
                return value.getValue();
            }
        }
        return null;
    }

    public String getDisableFormEdition() {
        for (Entry value : entry) {
            if (value.getKey().equalsIgnoreCase("disableFormEdition")) {
                return value.getValue();
            }
        }
        return null;
    }
}
