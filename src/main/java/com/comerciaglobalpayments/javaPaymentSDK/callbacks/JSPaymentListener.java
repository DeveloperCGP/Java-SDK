package com.comerciaglobalpayments.javaPaymentSDK.callbacks;

import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.JSAuthorizationResponse;

public interface JSPaymentListener {

    void onError(Error error, String errorMessage);

    void onAuthorizationResponseReceived(String rawResponse, JSAuthorizationResponse jsAuthorizationResponse);

}
