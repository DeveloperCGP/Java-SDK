package com.mp.javaPaymentSDK.callbacks;

import com.mp.javaPaymentSDK.enums.Error;
import com.mp.javaPaymentSDK.models.responses.JSAuthorizationResponse;

public interface JSPaymentListener {

    void onError(Error error, String errorMessage);

    void onAuthorizationResponseReceived(String rawResponse, JSAuthorizationResponse jsAuthorizationResponse);

}
