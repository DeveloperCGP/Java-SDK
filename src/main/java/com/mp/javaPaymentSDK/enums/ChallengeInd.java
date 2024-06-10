package com.mp.javaPaymentSDK.enums;

public enum ChallengeInd {
    _01("01"),
    _02("02"),
    _03("03"),
    _04("04"),
    _05("05"),
    _06("06"),
    _07("07"),
    _08("08"),
    _09("09");

    private final String value;

    ChallengeInd(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ChallengeInd getChallengeInd(String stringValue) {
        for (ChallengeInd challengeInd : ChallengeInd.values()) {
            if (stringValue.equalsIgnoreCase(challengeInd.value)) {
                return challengeInd;
            }
        }

        return null;
    }
}
