package com.comerciaglobalpayments.javaPaymentSDK.adapters;

import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.TransactionResult;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;

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
