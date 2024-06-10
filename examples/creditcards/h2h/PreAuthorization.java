package com.comerciaglobalpayments.javaPaymentSDK.examples.creditcards.h2h;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HPreAuthorization;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

public class PreAuthorization {

    public static void main(String[] args) throws InvalidFieldException {
        sendPreAuthorizationPaymentRequest();
    }

    public static void sendPreAuthorizationPaymentRequest() {
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
            H2HPreAuthorization h2HPreAuthorization = new H2HPreAuthorization();
            h2HPreAuthorization.setAmount("50");
            h2HPreAuthorization.setCurrency(Currency.EUR);
            h2HPreAuthorization.setCountry(CountryCode.ES);
            h2HPreAuthorization.setCardNumber("4907270002222227");
            h2HPreAuthorization.setCustomerId("903");
            h2HPreAuthorization.setChName("First name Last name");
            h2HPreAuthorization.setCvnNumber("123");
            h2HPreAuthorization.setExpDate("0625");
            h2HPreAuthorization.setPaymentSolution(PaymentSolutions.creditcards);
            h2HPreAuthorization.setStatusURL(Creds.statusUrl);
            h2HPreAuthorization.setSuccessURL(Creds.successUrl);
            h2HPreAuthorization.setErrorURL(Creds.errorUrl);
            h2HPreAuthorization.setAwaitingURL(Creds.awaitingUrl);
            h2HPreAuthorization.setCancelURL(Creds.cancelUrl);

            // Step 3 - Send Payment Request
            H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
            h2HPaymentAdapter.sendH2hPreAuthorizationRequest(h2HPreAuthorization, new ResponseListenerAdapter() {
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
