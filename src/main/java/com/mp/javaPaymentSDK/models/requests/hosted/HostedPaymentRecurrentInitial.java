package com.mp.javaPaymentSDK.models.requests.hosted;

import com.mp.javaPaymentSDK.enums.ChallengeInd;
import com.mp.javaPaymentSDK.enums.PaymentRecurringType;

public class HostedPaymentRecurrentInitial extends HostedPaymentRedirection {

    private PaymentRecurringType paymentRecurringType = PaymentRecurringType.newCof;
    private String challengeInd = ChallengeInd._04.getValue();

    public HostedPaymentRecurrentInitial() {
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
