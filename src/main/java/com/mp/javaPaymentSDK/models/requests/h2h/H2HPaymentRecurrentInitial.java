package com.mp.javaPaymentSDK.models.requests.h2h;

import com.mp.javaPaymentSDK.enums.ChallengeInd;
import com.mp.javaPaymentSDK.enums.PaymentRecurringType;

public class H2HPaymentRecurrentInitial extends H2HRedirection {

    private PaymentRecurringType paymentRecurringType = PaymentRecurringType.newCof;
    private String challengeInd = ChallengeInd._04.getValue();

    public H2HPaymentRecurrentInitial() {
        super();
    }

    public PaymentRecurringType getPaymentRecurringType() {
        return paymentRecurringType;
    }

    public void setPaymentRecurringType(PaymentRecurringType paymentRecurringType) {
        this.paymentRecurringType = paymentRecurringType;
    }

    public ChallengeInd getChallengeInd() {
        return ChallengeInd.getChallengeInd(challengeInd);
    }

    public void setChallengeInd(ChallengeInd challengeInd) {
        this.challengeInd = challengeInd.getValue();
    }
}
