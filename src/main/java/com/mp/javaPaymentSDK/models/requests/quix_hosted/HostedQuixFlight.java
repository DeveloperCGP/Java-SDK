package com.mp.javaPaymentSDK.models.requests.quix_hosted;

import com.mp.javaPaymentSDK.models.quix_models.quix_flight.QuixFlightPaySolExtendedData;
import com.mp.javaPaymentSDK.models.quix_models.QuixHostedRequest;
import kotlin.Pair;

public class HostedQuixFlight extends QuixHostedRequest {

    private QuixFlightPaySolExtendedData paySolExtendedData;

    public HostedQuixFlight() {
        super();
    }

    public QuixFlightPaySolExtendedData getPaySolExtendedData() {
        return paySolExtendedData;
    }

    public void setPaySolExtendedData(QuixFlightPaySolExtendedData paySolExtendedData) {
        this.paySolExtendedData = paySolExtendedData;
    }

    public Pair<Boolean, String> isMissingFields() {
        if (paySolExtendedData == null) {
            return new Pair<>(true, "paySolExtendedData");
        }

        Pair<Boolean, String> missingField = paySolExtendedData.isMissingField();
        if (missingField.getFirst()) {
            return missingField;
        }

        return super.isMissingFields();
    }

}
