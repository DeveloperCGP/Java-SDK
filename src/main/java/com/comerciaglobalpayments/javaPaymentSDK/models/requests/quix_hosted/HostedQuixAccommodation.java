package com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted;

import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixHostedRequest;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixAccommodationPaySolExtendedData;
import kotlin.Pair;

public class HostedQuixAccommodation extends QuixHostedRequest {

    private QuixAccommodationPaySolExtendedData paySolExtendedData = null;

    public HostedQuixAccommodation() {
        super();
    }

    public QuixAccommodationPaySolExtendedData getPaySolExtendedData() {
        return paySolExtendedData;
    }

    public void setPaySolExtendedData(QuixAccommodationPaySolExtendedData paySolExtendedData) {
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
