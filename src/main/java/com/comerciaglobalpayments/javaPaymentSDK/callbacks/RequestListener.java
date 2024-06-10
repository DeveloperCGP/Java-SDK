package com.comerciaglobalpayments.javaPaymentSDK.callbacks;

import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import okhttp3.ResponseBody;

public interface RequestListener {

    void onError(Error error, String errorMessage);

    void onResponse(int code, ResponseBody responseBody);

}
