package com.mp.javaPaymentSDK.models.quix_models.quix_flight;

import com.google.gson.annotations.SerializedName;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class QuixSegmentFlight {

    @SerializedName("iata_departure_code")
    private String iataDepartureCode = null;
    @SerializedName("iata_destination_code")
    private String iataDestinationCode = null;

    public String getIataDepartureCode() {
        return iataDepartureCode;
    }

    public void setIataDepartureCode(String iataDepartureCode) {
        this.iataDepartureCode = iataDepartureCode;
    }

    public String getIataDestinationCode() {
        return iataDestinationCode;
    }

    public void setIataDestinationCode(String iataDestinationCode) {
        this.iataDestinationCode = iataDestinationCode;
    }

    public Pair<Boolean, String> isMissingFields() {
        List<String> mandatoryFields = Arrays.asList(
                "iataDepartureCode", "iataDestinationCode"
        );
        return Utils.containsNull(
                this.getClass(), this, mandatoryFields
        );
    }
}
