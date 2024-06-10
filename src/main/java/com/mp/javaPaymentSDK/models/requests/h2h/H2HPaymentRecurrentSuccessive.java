package com.mp.javaPaymentSDK.models.requests.h2h;

import com.mp.javaPaymentSDK.enums.MerchantExemptionsSca;
import com.mp.javaPaymentSDK.enums.PaymentRecurringType;
import com.mp.javaPaymentSDK.models.Credentials;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class H2HPaymentRecurrentSuccessive extends H2HRedirection {
    private String subscriptionPlan = null;
    private PaymentRecurringType paymentRecurringType = PaymentRecurringType.cof;
    private MerchantExemptionsSca merchantExemptionsSca;

    public H2HPaymentRecurrentSuccessive() {
        super();
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public void setPaymentRecurringType(PaymentRecurringType paymentRecurringType) {
        this.paymentRecurringType = paymentRecurringType;
    }

    public PaymentRecurringType getPaymentRecurringType() {
        return paymentRecurringType;
    }

    public MerchantExemptionsSca getMerchantExemptionsSca() {
        return merchantExemptionsSca;
    }

    public void setMerchantExemptionsSca(MerchantExemptionsSca merchantExemptionsSca) {
        this.merchantExemptionsSca = merchantExemptionsSca;
    }

    public Pair<Boolean, String> isMissingField() {

        List<String> mandatoryFields = Arrays.asList(
                "cardNumberToken", "subscriptionPlan", "paymentRecurringType",
                "merchantExemptionsSca"
        );

        Pair<Boolean, String> isMissingFields = Utils.containsNull(
                H2HPaymentRecurrentSuccessive.class, this, mandatoryFields
        );

        if (isMissingFields.getFirst()) {
            return isMissingFields;
        } else {
            return super.isMissingField();
        }
    }

    public Pair<Boolean, String> checkCredentials(Credentials credentials) {
        if (credentials.getApiVersion() < 0)
        {
            return new Pair<>(true, "apiVersion");
        }
        List<String> mandatoryFields = Arrays.asList(
                "merchantId", "productId", "merchantPass", "environment"
        );

        return Utils.containsNull(
                Credentials.class, credentials, mandatoryFields
        );
    }
}
