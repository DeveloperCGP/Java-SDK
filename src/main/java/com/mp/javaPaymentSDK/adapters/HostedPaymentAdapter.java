package com.mp.javaPaymentSDK.adapters;

import com.mp.javaPaymentSDK.callbacks.ResponseListener;
import com.mp.javaPaymentSDK.callbacks.RequestListener;
import com.mp.javaPaymentSDK.enums.Endpoints;
import com.mp.javaPaymentSDK.enums.Error;
import com.mp.javaPaymentSDK.exceptions.MissingFieldException;
import com.mp.javaPaymentSDK.models.Credentials;
import com.mp.javaPaymentSDK.models.requests.hosted.HostedPaymentRecurrentInitial;
import com.mp.javaPaymentSDK.models.requests.hosted.HostedPaymentRedirection;
import com.mp.javaPaymentSDK.utils.HexUtils;
import com.mp.javaPaymentSDK.utils.SecurityUtils;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;
import okhttp3.FormBody;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HostedPaymentAdapter {

    private Credentials credentials;

    private final NetworkAdapter networkAdapter = new NetworkAdapter();

    public HostedPaymentAdapter(Credentials credentials) {
        this.credentials = credentials;
    }

    public void sendHostedPaymentRequest(HostedPaymentRedirection hostedPaymentRedirection, ResponseListener responseListener) throws MissingFieldException {

        Pair<Boolean, String> isMissingCred = hostedPaymentRedirection.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        String endpoint = Endpoints.HOSTED_ENDPOINT.getEndpoint(credentials.getEnvironment());

        hostedPaymentRedirection.setCredentials(credentials);

        Pair<Boolean, String> isMissingField = hostedPaymentRedirection.isMissingField();
        if (isMissingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingField.getSecond(), false));
        }

        String httpQuery = Utils.buildQuery(HostedPaymentRedirection.class, hostedPaymentRedirection);
        System.out.println("Clear Query = " + httpQuery);
        String finalQueryParameter = Utils.encodeUrl(httpQuery);
        System.out.println("Encoded Query = " + finalQueryParameter);
        byte[] formattedRequest = finalQueryParameter.getBytes(StandardCharsets.UTF_8);

        byte[] clearIV = SecurityUtils.generateIV();

        byte[] encryptedRequest = SecurityUtils.cbcEncryption(
                formattedRequest, // formatted request
                credentials.getMerchantPass().getBytes(),
                clearIV,
                true
        );

        byte[] signature = SecurityUtils.hash256(formattedRequest);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("apiVersion", String.valueOf(credentials.getApiVersion()));
        headers.put("encryptionMode", "CBC");
        headers.put("iv", SecurityUtils.base64Encode(clearIV));

        HashMap<String, String> queryParameters = new HashMap<>();
        queryParameters.put("merchantId", String.valueOf(hostedPaymentRedirection.getMerchantId()));
        queryParameters.put("encrypted", SecurityUtils.base64Encode(encryptedRequest));
        queryParameters.put("integrityCheck", HexUtils.bytesToHex(signature).toLowerCase());

        networkAdapter.sendRequest(headers, queryParameters, new FormBody.Builder().build(), endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Hosted Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200) {
                    try {
                        String url = responseBody.string();
                        System.out.println(url);
                        if (Utils.isValidURL(url)) {
                            responseListener.onRedirectionURLReceived(url);
                        } else {
                            responseListener.onError(Error.INVALID_RESPONSE_RECEIVED, Error.INVALID_RESPONSE_RECEIVED.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseListener.onError(Error.INVALID_RESPONSE_RECEIVED, Error.INVALID_RESPONSE_RECEIVED.getMessage());
                    }
                } else if (code >= 400 && code < 500) {
                    String errorMessage = Error.CLIENT_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    }
                    catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.CLIENT_ERROR, errorMessage);
                } else {
                    String errorMessage = Error.SERVER_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    }
                    catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.SERVER_ERROR, errorMessage);
                }
            }
        });
    }

    public void sendHostedRecurrentInitial(HostedPaymentRecurrentInitial hostedPaymentRecurrentInitial, ResponseListener responseListener) throws MissingFieldException {

        Pair<Boolean, String> isMissingCred = hostedPaymentRecurrentInitial.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        hostedPaymentRecurrentInitial.setCredentials(credentials);

        String endpoint = Endpoints.HOSTED_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> isMissingField = hostedPaymentRecurrentInitial.isMissingField();
        if (isMissingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingField.getSecond(), false));
        }

        String httpQuery = Utils.buildQuery(HostedPaymentRecurrentInitial.class, hostedPaymentRecurrentInitial);
        System.out.println("Clear Query = " + httpQuery);
        String finalQueryParameter = Utils.encodeUrl(httpQuery);
        System.out.println("Encoded Query = " + finalQueryParameter);
        byte[] formattedRequest = finalQueryParameter.getBytes(StandardCharsets.UTF_8);

        byte[] clearIV = SecurityUtils.generateIV();

        byte[] encryptedRequest = SecurityUtils.cbcEncryption(
                formattedRequest, // formatted request
                credentials.getMerchantPass().getBytes(),
                clearIV,
                true
        );

        byte[] signature = SecurityUtils.hash256(formattedRequest);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("apiVersion", String.valueOf(credentials.getApiVersion()));
        headers.put("encryptionMode", "CBC");
        headers.put("iv", SecurityUtils.base64Encode(clearIV));

        HashMap<String, String> queryParameters = new HashMap<>();
        queryParameters.put("merchantId", String.valueOf(hostedPaymentRecurrentInitial.getMerchantId()));
        queryParameters.put("encrypted", SecurityUtils.base64Encode(encryptedRequest));
        queryParameters.put("integrityCheck", HexUtils.bytesToHex(signature).toLowerCase());

        networkAdapter.sendRequest(headers, queryParameters, new FormBody.Builder().build(), endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Hosted Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200) {
                    try {
                        String url = responseBody.string();
                        System.out.println(url);
                        if (Utils.isValidURL(url)) {
                            responseListener.onRedirectionURLReceived(url);
                        } else {
                            responseListener.onError(Error.INVALID_RESPONSE_RECEIVED, Error.INVALID_RESPONSE_RECEIVED.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseListener.onError(Error.INVALID_RESPONSE_RECEIVED, Error.INVALID_RESPONSE_RECEIVED.getMessage());
                    }
                } else if (code >= 400 && code < 500) {
                    String errorMessage = Error.CLIENT_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    }
                    catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.CLIENT_ERROR, errorMessage);
                } else {
                    String errorMessage = Error.SERVER_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    }
                    catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.SERVER_ERROR, errorMessage);
                }
            }
        });
    }
}
