package com.comerciaglobalpayments.javaPaymentSDK.examples.creditcards.h2h;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HPaymentRecurrentInitial;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

public class Recurring {

    public static void main(String[] args) {
        sendRecurringPaymentRequest();
    }


    public static void sendRecurringPaymentRequest() {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            // Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantPass(Creds.merchantPass);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productId);
            credentials.setApiVersion(5);

            // Step 2 - Configure Payment Parameters
            H2HPaymentRecurrentInitial h2HPaymentRecurrentInitial = new H2HPaymentRecurrentInitial();

            h2HPaymentRecurrentInitial.setAmount("50");
            h2HPaymentRecurrentInitial.setCurrency(Currency.EUR);
            h2HPaymentRecurrentInitial.setCountry(CountryCode.ES);
            h2HPaymentRecurrentInitial.setCardNumber("4907270002222227");
            h2HPaymentRecurrentInitial.setCustomerId("55");
            h2HPaymentRecurrentInitial.setChName("First name Last name");
            h2HPaymentRecurrentInitial.setCvnNumber("123");
            h2HPaymentRecurrentInitial.setExpDate("0625");
            h2HPaymentRecurrentInitial.setPaymentSolution(PaymentSolutions.creditcards);
            h2HPaymentRecurrentInitial.setStatusURL(Creds.statusUrl);
            h2HPaymentRecurrentInitial.setSuccessURL(Creds.successUrl);
            h2HPaymentRecurrentInitial.setErrorURL(Creds.errorUrl);
            h2HPaymentRecurrentInitial.setAwaitingURL(Creds.awaitingUrl);
            h2HPaymentRecurrentInitial.setCancelURL(Creds.cancelUrl);
            h2HPaymentRecurrentInitial.setPaymentRecurringType(PaymentRecurringType.newCof);
            h2HPaymentRecurrentInitial.setForceTokenRequest(true);

            // Step 3 - Send Payment Request
            H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
            h2HPaymentAdapter.sendH2hPaymentRecurrentInitial(h2HPaymentRecurrentInitial, new ResponseListenerAdapter() {
                // Step 4 - Handle the Response
                @Override
                public void onError(Error error, String errorMessage) {
                    System.out.println("Error received - " + error.name() + " - " + errorMessage);
                }

                @Override
                public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
                    System.out.println("Response Notification Received");
                    System.out.println(gson.toJson(notification));
                    String redirectionURL = notification.getRedirectUrl();
                    System.out.println("Redirection url = " + redirectionURL);
                }
            });
        } catch (FieldException fieldException) {
            fieldException.printStackTrace();
        }
    }

}
