package com.mp.javaPaymentSDK.adapters;

import com.mp.javaPaymentSDK.enums.Error;
import com.mp.javaPaymentSDK.callbacks.RequestListener;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;

public class NetworkAdapter {

    private OkHttpClient client = new OkHttpClient.Builder()
            .followRedirects(true).build();

    protected void sendRequest(
            HashMap<String, String> headers,
            HashMap<String, String> queryParameter,
            RequestBody requestBody,
            String url,
            RequestListener requestListener
    ) {

        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            requestListener.onError(Error.INVALID_URL, Error.INVALID_URL.getMessage());
            return;
        }
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();

        if (queryParameter != null) {
            queryParameter.forEach(urlBuilder::addQueryParameter);
        }

        Request.Builder builder = new Request.Builder()
                .url(urlBuilder.build().toString())
                .post(requestBody);

        if (headers != null) {
            headers.forEach(builder::addHeader);
        }

        Request request = builder.build();

        Call call = client.newCall(request);

        try (Response response = call.execute()) {
            System.out.println("A response received");
            requestListener.onResponse(response.code(), response.body());
        } catch (IOException e) {
            System.out.println("A Network error occurred while sending the request");
            requestListener.onError(Error.NETWORK_ERROR, Error.NETWORK_ERROR.getMessage());
        }
    }

}
