package com.mp.javaPaymentSDK.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mp.javaPaymentSDK.callbacks.RequestListener;
import com.mp.javaPaymentSDK.callbacks.ResponseListener;
import com.mp.javaPaymentSDK.enums.Endpoints;
import com.mp.javaPaymentSDK.enums.Error;
import com.mp.javaPaymentSDK.exceptions.MissingFieldException;
import com.mp.javaPaymentSDK.models.Credentials;
import com.mp.javaPaymentSDK.models.quix_models.quix_accommodation.QuixItemCartItemAccommodation;
import com.mp.javaPaymentSDK.models.quix_models.quix_flight.QuixItemCartItemFlight;
import com.mp.javaPaymentSDK.models.quix_models.quix_service.QuixItemCartItemService;
import com.mp.javaPaymentSDK.models.requests.quix_js.JSQuixAccommodation;
import com.mp.javaPaymentSDK.models.requests.quix_js.JSQuixFlight;
import com.mp.javaPaymentSDK.models.requests.quix_js.JSQuixItem;
import com.mp.javaPaymentSDK.models.requests.quix_js.JSQuixService;
import com.mp.javaPaymentSDK.models.responses.notification.Notification;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class JSQuixPaymentAdapter {

    private Credentials credentials;

    private final NetworkAdapter networkAdapter = new NetworkAdapter();

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public JSQuixPaymentAdapter(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void sendJSQuixServiceRequest(JSQuixService jsQuixService, ResponseListener responseListener) throws MissingFieldException {
        Pair<Boolean, String> isMissingCred = jsQuixService.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        jsQuixService.setCredentials(credentials);

        String endpoint = Endpoints.CHARGE_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = jsQuixService.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        List<QuixItemCartItemService> items = jsQuixService.getPaySolExtendedData().getCart().getItems();
        for (QuixItemCartItemService item : items) {
            String endDate = item.getArticle().getEndDate();
            if (endDate.contains(":")) {
                item.getArticle().setEndDate(URLEncoder.encode(endDate, StandardCharsets.UTF_8));
            }
            if (item.getArticle().getStartDate() != null)
            {
                String startDate = item.getArticle().getStartDate();
                if (startDate.contains(":")) {
                    item.getArticle().setStartDate(URLEncoder.encode(startDate, StandardCharsets.UTF_8));
                }
            }
        }

        JSONObject bodyJson = new JSONObject(gson.toJson(jsQuixService));
        bodyJson.remove("paysolExtendedData");
        bodyJson.remove("prepayToken");

        bodyJson.put("paysolExtendedData", gson.toJson(jsQuixService.getPaySolExtendedData()));
        System.out.println("Request Body = " + bodyJson.toString(2));

        RequestBody requestBody = RequestBody.create(bodyJson.toString(), MediaType.parse("application/json"));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("prepayToken", jsQuixService.getPrepayToken());
        headers.put("apiVersion", String.valueOf(credentials.getApiVersion()));

        networkAdapter.sendRequest(headers, null, requestBody, endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200 || code == 307) {
                    try {
                        String rawResponse = responseBody.string();
                        System.out.println(rawResponse);
                        Notification notification = NotificationAdapter.parseNotification(rawResponse);
                        responseListener.onResponseReceived(rawResponse, notification, notification.getTransactionResult());
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

    public void sendJSQuixFlightRequest(JSQuixFlight jsQuixFlight, ResponseListener responseListener) throws MissingFieldException {

        Pair<Boolean, String> isMissingCred = jsQuixFlight.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        jsQuixFlight.setCredentials(credentials);

        String endpoint = Endpoints.CHARGE_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = jsQuixFlight.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        List<QuixItemCartItemFlight> items = jsQuixFlight.getPaySolExtendedData().getCart().getItems();
        for (QuixItemCartItemFlight item : items) {
            String departureDate = item.getArticle().getDepartureDate();
            if (departureDate.contains(":")) {
                item.getArticle().setDepartureDate(URLEncoder.encode(departureDate, StandardCharsets.UTF_8));
            }
        }

        JSONObject bodyJson = new JSONObject(gson.toJson(jsQuixFlight));
        bodyJson.remove("paysolExtendedData");
        bodyJson.remove("prepayToken");

        bodyJson.put("paysolExtendedData", gson.toJson(jsQuixFlight.getPaySolExtendedData()));
        System.out.println("Request Body = " + bodyJson.toString(2));

        RequestBody requestBody = RequestBody.create(bodyJson.toString(), MediaType.parse("application/json"));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("prepayToken", jsQuixFlight.getPrepayToken());
        headers.put("apiVersion", String.valueOf(credentials.getApiVersion()));

        networkAdapter.sendRequest(headers, null, requestBody, endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200 || code == 307) {
                    try {
                        String rawResponse = responseBody.string();
                        System.out.println(rawResponse);
                        Notification notification = NotificationAdapter.parseNotification(rawResponse);
                        responseListener.onResponseReceived(rawResponse, notification, notification.getTransactionResult());
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

    public void sendJSQuixAccommodationRequest(JSQuixAccommodation jsQuixAccommodation, ResponseListener responseListener) throws MissingFieldException {

        Pair<Boolean, String> isMissingCred = jsQuixAccommodation.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        jsQuixAccommodation.setCredentials(credentials);

        String endpoint = Endpoints.CHARGE_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = jsQuixAccommodation.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        List<QuixItemCartItemAccommodation> items = jsQuixAccommodation.getPaySolExtendedData().getCart().getItems();
        for (QuixItemCartItemAccommodation item : items) {
            String checkinDate = item.getArticle().getCheckinDate();
            String checkoutDate = item.getArticle().getCheckoutDate();
            if (checkinDate.contains(":")) {
                item.getArticle().setCheckinDate(URLEncoder.encode(checkinDate, StandardCharsets.UTF_8));
            }
            if (checkoutDate.contains(":")) {
                item.getArticle().setCheckoutDate(URLEncoder.encode(checkoutDate, StandardCharsets.UTF_8));
            }
        }

        JSONObject bodyJson = new JSONObject(gson.toJson(jsQuixAccommodation));
        bodyJson.remove("paysolExtendedData");
        bodyJson.remove("prepayToken");

        bodyJson.put("paysolExtendedData", gson.toJson(jsQuixAccommodation.getPaySolExtendedData()));
        System.out.println("Request Body = " + bodyJson.toString(2));

        RequestBody requestBody = RequestBody.create(bodyJson.toString(), MediaType.parse("application/json"));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("prepayToken", jsQuixAccommodation.getPrepayToken());
        headers.put("apiVersion", String.valueOf(credentials.getApiVersion()));

        networkAdapter.sendRequest(headers, null, requestBody, endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200 || code == 307) {
                    try {
                        String rawResponse = responseBody.string();
                        System.out.println(rawResponse);
                        Notification notification = NotificationAdapter.parseNotification(rawResponse);
                        responseListener.onResponseReceived(rawResponse, notification, notification.getTransactionResult());

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

    public void sendJSQuixItemRequest(JSQuixItem jsQuixItem, ResponseListener responseListener) throws MissingFieldException {

        Pair<Boolean, String> isMissingCred = jsQuixItem.checkCredentials(credentials);
        if (isMissingCred.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(isMissingCred.getSecond(), true));
        }

        jsQuixItem.setCredentials(credentials);

        String endpoint = Endpoints.CHARGE_ENDPOINT.getEndpoint(credentials.getEnvironment());

        Pair<Boolean, String> missingField = jsQuixItem.isMissingFields();
        if (missingField.getFirst()) {
            throw new MissingFieldException(MissingFieldException.createMessage(missingField.getSecond(), false));
        }

        JSONObject bodyJson = new JSONObject(jsQuixItem);
        bodyJson.remove("paysolExtendedData");
        bodyJson.remove("prepayToken");

        bodyJson.put("paysolExtendedData", gson.toJson(jsQuixItem.getPaySolExtendedData()));
        System.out.println("Request Body = " + bodyJson.toString(2));

        RequestBody requestBody = RequestBody.create(bodyJson.toString(), MediaType.parse("application/json"));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("prepayToken", jsQuixItem.getPrepayToken());
        headers.put("apiVersion", String.valueOf(credentials.getApiVersion()));

        networkAdapter.sendRequest(headers, null, requestBody, endpoint, new RequestListener() {
            @Override
            public void onError(Error error, String errorMessage) {
                System.out.println("An error occurred in Quix Payment - " + error.getMessage() + " - " + errorMessage);
                responseListener.onError(error, errorMessage);
            }

            @Override
            public void onResponse(int code, ResponseBody responseBody) {
                if (code == 200 || code == 307) {
                    try {
                        String rawResponse = responseBody.string();
                        System.out.println(rawResponse);
                        Notification notification = NotificationAdapter.parseNotification(rawResponse);
                        responseListener.onResponseReceived(rawResponse, notification, notification.getTransactionResult());

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
