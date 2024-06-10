package com.mp.javaPaymentSDK.models.requests.quix_hosted;

import com.mp.javaPaymentSDK.models.quix_models.QuixHostedRequest;
import com.mp.javaPaymentSDK.models.quix_models.quix_product.QuixItemPaySolExtendedData;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

public class HostedQuixItem extends QuixHostedRequest {

    private QuixItemPaySolExtendedData paySolExtendedData;

    public HostedQuixItem() {
        super();
    }

    public QuixItemPaySolExtendedData getPaySolExtendedData() {
        return paySolExtendedData;
    }

    public void setPaySolExtendedData(QuixItemPaySolExtendedData paySolExtendedData) {
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
