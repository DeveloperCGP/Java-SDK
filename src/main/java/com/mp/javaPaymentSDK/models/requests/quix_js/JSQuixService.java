package com.mp.javaPaymentSDK.models.requests.quix_js;

import com.mp.javaPaymentSDK.models.quix_models.QuixJSRequest;
import com.mp.javaPaymentSDK.models.quix_models.quix_service.QuixServicePaySolExtendedData;
import kotlin.Pair;

public class JSQuixService extends QuixJSRequest {

    private QuixServicePaySolExtendedData paySolExtendedData;

    public JSQuixService() {
        super();
    }

    public QuixServicePaySolExtendedData getPaySolExtendedData() {
        return paySolExtendedData;
    }

    public void setPaySolExtendedData(QuixServicePaySolExtendedData paySolExtendedData) {
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
