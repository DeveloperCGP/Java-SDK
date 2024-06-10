package com.mp.javaPaymentSDK.models.requests.js;

import com.mp.javaPaymentSDK.enums.ChallengeInd;
import com.mp.javaPaymentSDK.enums.PaymentRecurringType;

public class JSPaymentRecurrentInitial extends JSCharge {

    private PaymentRecurringType paymentRecurringType = PaymentRecurringType.newCof;
    private String challengeInd = ChallengeInd._04.getValue();

    public JSPaymentRecurrentInitial() {
        super();
    }

    public PaymentRecurringType getPaymentRecurringType() {
        return paymentRecurringType;
    }

    public void setPaymentRecurringType(PaymentRecurringType paymentRecurringType) {
        this.paymentRecurringType = paymentRecurringType;
    }

    public void setChallengeInd(ChallengeInd challengeInd) {
        this.challengeInd = challengeInd.getValue();
    }

    public ChallengeInd getChallengeInd() {
        return ChallengeInd.getChallengeInd(challengeInd);
    }
}
