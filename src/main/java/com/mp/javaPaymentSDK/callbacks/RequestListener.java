package com.mp.javaPaymentSDK.callbacks;

import com.mp.javaPaymentSDK.enums.Error;
import okhttp3.ResponseBody;

public interface RequestListener {

    void onError(Error error, String errorMessage);

    void onResponse(int code, ResponseBody responseBody);

}
