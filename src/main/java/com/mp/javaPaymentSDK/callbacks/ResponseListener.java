package com.mp.javaPaymentSDK.callbacks;

import com.mp.javaPaymentSDK.enums.Error;
import com.mp.javaPaymentSDK.enums.TransactionResult;
import com.mp.javaPaymentSDK.models.responses.notification.Notification;

public interface ResponseListener {

    void onError(Error error, String errorMessage);

    void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult);

    void onRedirectionURLReceived(String redirectionURL);

}
