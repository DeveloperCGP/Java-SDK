package com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted;

import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixHostedRequest;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixServicePaySolExtendedData;
import kotlin.Pair;

public class HostedQuixService extends QuixHostedRequest {

    private QuixServicePaySolExtendedData paySolExtendedData;

    public HostedQuixService() {
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
