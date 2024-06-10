package com.comerciaglobalpayments.javaPaymentSDK.examples.creditcards.js;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.js.JSCharge;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

public class Charge {

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
            JSCharge jsCharge = new JSCharge();
            jsCharge.setAmount("30");
            jsCharge.setPrepayToken("45357b66-8f04-4276-84f4-d35c885bde8e");
            jsCharge.setCountry(CountryCode.ES);
            jsCharge.setCustomerId("55");
            jsCharge.setCurrency(Currency.EUR);
            jsCharge.setOperationType(OperationTypes.DEBIT);
            jsCharge.setPaymentSolution(PaymentSolutions.creditcards);
            jsCharge.setStatusURL(Creds.statusUrl);
            jsCharge.setSuccessURL(Creds.successUrl);
            jsCharge.setErrorURL(Creds.errorUrl);
            jsCharge.setAwaitingURL(Creds.awaitingUrl);
            jsCharge.setCancelURL(Creds.cancelUrl);
            jsCharge.setApiVersion(5);

            // Step 3 - Send Payment Request
            JSPaymentAdapter jsPaymentAdapter = new JSPaymentAdapter(credentials);
            jsPaymentAdapter.sendJSChargeRequest(jsCharge, new ResponseListenerAdapter() {
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
