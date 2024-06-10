package com.comerciaglobalpayments.javaPaymentSDK.callbacks;

import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;

public interface NotificationListener {

    void onError(Error error, String errorMessage);

    void onNotificationReceived(String notificationResponse);

}
