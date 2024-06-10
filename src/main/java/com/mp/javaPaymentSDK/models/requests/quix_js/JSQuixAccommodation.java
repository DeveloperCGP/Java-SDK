package com.mp.javaPaymentSDK.models.requests.quix_js;

import com.mp.javaPaymentSDK.models.quix_models.QuixJSRequest;
import com.mp.javaPaymentSDK.models.quix_models.quix_accommodation.QuixAccommodationPaySolExtendedData;
import kotlin.Pair;

public class JSQuixAccommodation extends QuixJSRequest {

    private QuixAccommodationPaySolExtendedData paySolExtendedData;

    public JSQuixAccommodation() {
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
