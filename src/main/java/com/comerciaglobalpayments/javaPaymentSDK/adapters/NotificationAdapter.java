package com.comerciaglobalpayments.javaPaymentSDK.adapters;

import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.operation.Entry;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.operation.ExtraDetails;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.operation.OptionalTransactionParams;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.NotificationInnerResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter {

    public static Notification parseNotification(String notificationString) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        System.out.println("Parsing Notification \n" + notificationString);

        if (isJson(notificationString)) {
            JSONObject jsonObject = new JSONObject(notificationString);
            if (jsonObject.has("response")) {
                String responseString = jsonObject.get("response").toString();
                if (isJson(responseString)) {
                    return parseJsonNotification(responseString);
                }
                else {
                    return xmlMapper.readValue(responseString, Notification.class);
                }
            }
            else {
                return parseJsonNotification(notificationString);
            }
        }
        else {
            NotificationInnerResponse notificationInnerResponse = xmlMapper.readValue(notificationString, NotificationInnerResponse.class);
            if (notificationInnerResponse.getResponse() == null || notificationInnerResponse.getResponse().trim().isEmpty()) {
                String xmlStringResponse = xmlMapper.readValue(notificationString, String.class).trim();
                if (isJson(xmlStringResponse)) {
                    return parseJsonNotification(xmlStringResponse);
                }
                else {
                    return xmlMapper.readValue(notificationString, Notification.class);
                }
            }
            else {
                notificationInnerResponse.setResponse(notificationInnerResponse.getResponse().trim());
                if (isJson(notificationInnerResponse.getResponse())) {
                    return parseJsonNotification(notificationInnerResponse.getResponse());
                }
                else {
                    return xmlMapper.readValue(notificationInnerResponse.getResponse(), Notification.class);
                }
            }
        }
    }

    public static Notification parseJsonNotification(String jsonNotification) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Notification notification = gson.fromJson(jsonNotification, Notification.class);
        JSONObject notificationObject = new JSONObject(jsonNotification);
        if (notificationObject.has("optionalTransactionParams")) {
            notification.setOptionalTransactionParams(parseOptionalTransactionParams(notificationObject.getJSONObject("optionalTransactionParams")));
        }
        if (notificationObject.has("operationsArray") && !notificationObject.isNull("operationsArray")) {
            JSONArray array = notificationObject.getJSONArray("operationsArray");
            for (int i = 0; i < array.length(); i++) {
                JSONObject operationObject = array.getJSONObject(i);
                if (!operationObject.isNull("optionalTransactionParams")) {
                    JSONObject optionalTransactionParams = operationObject.getJSONObject("optionalTransactionParams");
                    notification.getOperations().get(i).setOptionalTransactionParams(parseOptionalTransactionParams((optionalTransactionParams)));
                }
                if (operationObject.has("paymentDetails") && !operationObject.isNull("paymentDetails")) {
                    JSONObject paymentDetailsObject = operationObject.getJSONObject("paymentDetails");
                    if (paymentDetailsObject.has("extraDetails") && !paymentDetailsObject.isNull("extraDetails")) {
                        notification.getOperations().get(i).getPaymentDetails().setExtraDetails(parseExtraDetails(paymentDetailsObject.getJSONObject("extraDetails")));
                    }
                }
            }
        }

        return notification;
    }

    private static OptionalTransactionParams parseOptionalTransactionParams(JSONObject optionalTransactionParamsJson) {
        if (optionalTransactionParamsJson == null) {
            return null;
        }

        OptionalTransactionParams optionalTransactionParams = new OptionalTransactionParams();
        List<Entry> entries = new ArrayList<>();
        optionalTransactionParamsJson.keySet().forEach(keyStr ->
                entries.add(new Entry(keyStr, optionalTransactionParamsJson.getString(keyStr))));

        optionalTransactionParams.setEntry(entries);

        return optionalTransactionParams;
    }

    private static ExtraDetails parseExtraDetails(JSONObject extraDetailsJson) {
        if (extraDetailsJson == null) {
            return null;
        }

        ExtraDetails extraDetails = new ExtraDetails();
        List<Entry> entries = new ArrayList<>();
        extraDetailsJson.keySet().forEach(keyStr ->
                entries.add(new Entry(keyStr, extraDetailsJson.getString(keyStr))));

        extraDetails.setEntry(entries);

        return extraDetails;
    }

    public static boolean isJson(String text) {
        text = text.trim();
        return text.startsWith("{") && text.endsWith("}") || text.startsWith("[") && text.endsWith("]");
    }

}
