package com.mp.javaPaymentSDK.models.requests.h2h;

import kotlin.Pair;

public class H2HPreAuthorization extends H2HRedirection {

    public H2HPreAuthorization() {
        super();
        setAutoCapture(false);
    }

    @Override
    public Pair<Boolean, String> isMissingField() {
        if (isAutoCapture()) {
            return new Pair<>(true, "autoCapture");
        }
        return super.isMissingField();
    }
}
