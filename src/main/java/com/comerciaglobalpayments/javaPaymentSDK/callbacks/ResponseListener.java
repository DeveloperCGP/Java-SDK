package com.comerciaglobalpayments.javaPaymentSDK.callbacks;

import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.TransactionResult;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;

public interface ResponseListener {

    void onError(Error error, String errorMessage);

    void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult);

    void onRedirectionURLReceived(String redirectionURL);

}
