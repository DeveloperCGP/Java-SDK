package com.comerciaglobalpayments.javaPaymentSDK.examples.creditcards.js;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.js.JSPaymentRecurrentInitial;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

public class ChargeRecurring {

    public static void main(String[] args) {
        sendChargePaymentRequest();
    }

    public static void sendChargePaymentRequest() {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            // Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantKey(Creds.merchantKey);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productId);
            credentials.setApiVersion(5);

            // Step 2 - Configure Payment Parameters
            JSPaymentRecurrentInitial jsPaymentRecurrentInitial = new JSPaymentRecurrentInitial();
            jsPaymentRecurrentInitial.setAmount("30");
            jsPaymentRecurrentInitial.setPrepayToken("d0747288-d2c1-41e2-bbf8-fdd2ae8de9b8");
            jsPaymentRecurrentInitial.setCountry(CountryCode.ES);
            jsPaymentRecurrentInitial.setCustomerId("55");
            jsPaymentRecurrentInitial.setCurrency(Currency.EUR);
            jsPaymentRecurrentInitial.setOperationType(OperationTypes.DEBIT);
            jsPaymentRecurrentInitial.setPaymentSolution(PaymentSolutions.creditcards);
            jsPaymentRecurrentInitial.setStatusURL(Creds.statusUrl);
            jsPaymentRecurrentInitial.setSuccessURL(Creds.successUrl);
            jsPaymentRecurrentInitial.setErrorURL(Creds.errorUrl);
            jsPaymentRecurrentInitial.setAwaitingURL(Creds.awaitingUrl);
            jsPaymentRecurrentInitial.setCancelURL(Creds.cancelUrl);
            jsPaymentRecurrentInitial.setPaymentRecurringType(PaymentRecurringType.newSubscription);
            jsPaymentRecurrentInitial.setApiVersion(5);

            // Step 3 - Send Payment Request
            JSPaymentAdapter jsPaymentAdapter = new JSPaymentAdapter(credentials);
            jsPaymentAdapter.sendJSPaymentRecurrentInitial(jsPaymentRecurrentInitial, new ResponseListenerAdapter() {
                // Step 4 - Handle the Response
                public void onError(Error error, String errorMessage) {
                    System.out.println("Error received - " + error.name() + " - " + errorMessage);
                }

                @Override
                public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
                    System.out.println("Intermediate Notification Received");
                    System.out.println(gson.toJson(notification));
                }
            });
        } catch (FieldException fieldException) {
            fieldException.printStackTrace();
        }
    }
}
