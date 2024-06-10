package com.mp.javaPaymentSDK.adapters;

import com.mp.javaPaymentSDK.callbacks.ResponseListener;
import com.mp.javaPaymentSDK.enums.Error;
import com.mp.javaPaymentSDK.enums.TransactionResult;
import com.mp.javaPaymentSDK.models.responses.notification.Notification;

public abstract class ResponseListenerAdapter implements ResponseListener {
    @Override
    public void onError(Error error, String errorMessage) {
        // Empty implementation
    }

    @Override
    public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
        // Empty implementation
    }

    @Override
    public void onRedirectionURLReceived(String redirectionURL) {
        // Empty implementation
    }
}
