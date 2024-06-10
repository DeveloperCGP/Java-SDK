package com.mp.javaPaymentSDK.callbacks;

import com.mp.javaPaymentSDK.enums.Error;

public interface NotificationListener {

    void onError(Error error, String errorMessage);

    void onNotificationReceived(String notificationResponse);

}
