package com.mp.javaPaymentSDK.adapters;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mp.javaPaymentSDK.models.responses.notification.Notification;
import com.mp.javaPaymentSDK.models.responses.notification.NotificationInnerResponse;
import org.json.JSONObject;

public class NotificationAdapter {

    public static Notification parseNotification(String notificationString) throws Exception {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        XmlMapper xmlMapper = new XmlMapper();
        System.out.println("Parsing Notification \n" + notificationString);

        if (isJson(notificationString)) {
            JSONObject jsonObject = new JSONObject(notificationString);
            if (jsonObject.has("response")) {
                String responseString = jsonObject.get("response").toString();
                if (isJson(responseString)) {
                    return gson.fromJson(responseString, Notification.class);
                }
                else {
                    return xmlMapper.readValue(responseString, Notification.class);
                }
            }
            else {
                return gson.fromJson(notificationString, Notification.class);
            }
        }
        else {
            NotificationInnerResponse notificationInnerResponse = xmlMapper.readValue(notificationString, NotificationInnerResponse.class);
            if (notificationInnerResponse.getResponse() == null || notificationInnerResponse.getResponse().trim().isEmpty()) {
                String xmlStringResponse = xmlMapper.readValue(notificationString, String.class).trim();
                if (isJson(xmlStringResponse)) {
                    return gson.fromJson(xmlStringResponse, Notification.class);
                }
                else {
                    return xmlMapper.readValue(notificationString, Notification.class);
                }
            }
            else {
                notificationInnerResponse.setResponse(notificationInnerResponse.getResponse().trim());
                if (isJson(notificationInnerResponse.getResponse())) {
                    return gson.fromJson(notificationInnerResponse.getResponse(), Notification.class);
                }
                else {
                    return xmlMapper.readValue(notificationInnerResponse.getResponse(), Notification.class);
                }
            }
        }
    }

    public static boolean isJson(String text) {
        text = text.trim();
        return text.startsWith("{") && text.endsWith("}") || text.startsWith("[") && text.endsWith("]");
    }

    public static boolean isXML(String text) {
        text = text.trim();
        return text.startsWith("<") && text.endsWith(">");
    }

}
