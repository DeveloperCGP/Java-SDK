package com.mp.javaPaymentSDK.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mp.javaPaymentSDK.callbacks.RequestListener;
import com.mp.javaPaymentSDK.callbacks.ResponseListener;
import com.mp.javaPaymentSDK.enums.Endpoints;
import com.mp.javaPaymentSDK.enums.Error;
import com.mp.javaPaymentSDK.exceptions.MissingFieldException;
import com.mp.javaPaymentSDK.models.Credentials;
import com.mp.javaPaymentSDK.models.quix_models.QuixHostedRequest;
import com.mp.javaPaymentSDK.models.requests.quix_hosted.HostedQuixAccommodation;
import com.mp.javaPaymentSDK.models.requests.quix_hosted.HostedQuixFlight;
import com.mp.javaPaymentSDK.models.requests.quix_hosted.HostedQuixItem;
import com.mp.javaPaymentSDK.models.requests.quix_hosted.HostedQuixService;
import com.mp.javaPaymentSDK.utils.HexUtils;
import com.mp.javaPaymentSDK.utils.SecurityUtils;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;
import okhttp3.FormBody;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HostedQuixPaymentAdapter {

    private Credentials credentials;

    private final NetworkAdapter networkAdapter = new NetworkAdapter();

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public HostedQuixPaymentAdapter(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void sendHostedQuixServiceRequest(HostedQuixService hostedQuixService, ResponseListener responseListener) throws MissingFieldException {
        Pair<Boolean, String> isMissingCred = hostedQuixService.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        hostedQuixService.setCredentials(credentials);

        String endpoint = Endpoints.HOSTED_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = hostedQuixService.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        String httpQuery = Utils.buildQuery(QuixHostedRequest.class, hostedQuixService);
        httpQuery += "&paysolExtendedData=" + gson.toJson(hostedQuixService.getPaySolExtendedData());
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
        queryParameters.put("merchantId", String.valueOf(hostedQuixService.getMerchantId()));
        queryParameters.put("encrypted", SecurityUtils.base64Encode(encryptedRequest));
        queryParameters.put("integrityCheck", HexUtils.bytesToHex(signature).toLowerCase());

        networkAdapter.sendRequest(headers, queryParameters, new FormBody.Builder().build(), endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200 || code == 307) {
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
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.CLIENT_ERROR, errorMessage);
                } else {
                    String errorMessage = Error.SERVER_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.SERVER_ERROR, errorMessage);
                }
            }
        });
    }

    public void sendHostedQuixFlightRequest(HostedQuixFlight hostedQuixFlight, ResponseListener responseListener) throws MissingFieldException {
        Pair<Boolean, String> isMissingCred = hostedQuixFlight.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        hostedQuixFlight.setCredentials(credentials);

        String endpoint = Endpoints.HOSTED_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = hostedQuixFlight.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        String httpQuery = Utils.buildQuery(QuixHostedRequest.class, hostedQuixFlight);
        httpQuery += "&paysolExtendedData=" + gson.toJson(hostedQuixFlight.getPaySolExtendedData());
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
        queryParameters.put("merchantId", String.valueOf(hostedQuixFlight.getMerchantId()));
        queryParameters.put("encrypted", SecurityUtils.base64Encode(encryptedRequest));
        queryParameters.put("integrityCheck", HexUtils.bytesToHex(signature).toLowerCase());

        networkAdapter.sendRequest(headers, queryParameters, new FormBody.Builder().build(), endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
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
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.CLIENT_ERROR, errorMessage);
                } else {
                    String errorMessage = Error.SERVER_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.SERVER_ERROR, errorMessage);
                }
            }
        });
    }

    public void sendHostedQuixAccommodationRequest(HostedQuixAccommodation hostedQuixAccommodation, ResponseListener responseListener) throws MissingFieldException {
        Pair<Boolean, String> isMissingCred = hostedQuixAccommodation.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        hostedQuixAccommodation.setCredentials(credentials);

        String endpoint = Endpoints.HOSTED_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = hostedQuixAccommodation.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        String httpQuery = Utils.buildQuery(QuixHostedRequest.class, hostedQuixAccommodation);
        httpQuery += "&paysolExtendedData=" + gson.toJson(hostedQuixAccommodation.getPaySolExtendedData());
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
        queryParameters.put("merchantId", String.valueOf(hostedQuixAccommodation.getMerchantId()));
        queryParameters.put("encrypted", SecurityUtils.base64Encode(encryptedRequest));
        queryParameters.put("integrityCheck", HexUtils.bytesToHex(signature).toLowerCase());

        networkAdapter.sendRequest(headers, queryParameters, new FormBody.Builder().build(), endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
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
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.CLIENT_ERROR, errorMessage);
                } else {
                    String errorMessage = Error.SERVER_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.SERVER_ERROR, errorMessage);
                }
            }
        });
    }

    public void sendHostedQuixItemRequest(HostedQuixItem hostedQuixItem, ResponseListener responseListener) throws MissingFieldException {
        Pair<Boolean, String> isMissingCred = hostedQuixItem.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        hostedQuixItem.setCredentials(credentials);

        String endpoint = Endpoints.HOSTED_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = hostedQuixItem.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        String httpQuery = Utils.buildQuery(QuixHostedRequest.class, hostedQuixItem);
        httpQuery += "&paysolExtendedData=" + gson.toJson(hostedQuixItem.getPaySolExtendedData());
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
        queryParameters.put("merchantId", String.valueOf(hostedQuixItem.getMerchantId()));
        queryParameters.put("encrypted", SecurityUtils.base64Encode(encryptedRequest));
        queryParameters.put("integrityCheck", HexUtils.bytesToHex(signature).toLowerCase());

        networkAdapter.sendRequest(headers, queryParameters, new FormBody.Builder().build(), endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200 || code == 307) {
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
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.CLIENT_ERROR, errorMessage);
                } else {
                    String errorMessage = Error.SERVER_ERROR.getMessage();
                    try {
                        errorMessage = responseBody.string();
                        System.out.println("Error Received = " + errorMessage);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    responseListener.onError(Error.SERVER_ERROR, errorMessage);
                }
            }
        });
    }
}
