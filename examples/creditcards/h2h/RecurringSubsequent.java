package com.comerciaglobalpayments.javaPaymentSDK.examples.creditcards.h2h;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HPaymentRecurrentSuccessive;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

public class RecurringSubsequent {

    public static void main(String[] args) {
        sendRecurringSubsequentPaymentRequest();
    }

    public static void sendRecurringSubsequentPaymentRequest() {
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
            H2HPaymentRecurrentSuccessive h2HPaymentRecurrentSuccessive = new H2HPaymentRecurrentSuccessive();

            h2HPaymentRecurrentSuccessive.setAmount("50");
            h2HPaymentRecurrentSuccessive.setCurrency(Currency.EUR);
            h2HPaymentRecurrentSuccessive.setCountry(CountryCode.ES);
            h2HPaymentRecurrentSuccessive.setCustomerId("55");
            h2HPaymentRecurrentSuccessive.setChName("First name Last name");
            h2HPaymentRecurrentSuccessive.setPaymentSolution(PaymentSolutions.creditcards);
            h2HPaymentRecurrentSuccessive.setCardNumberToken("6529405841342227");
            h2HPaymentRecurrentSuccessive.setSubscriptionPlan("301676347745850");
            h2HPaymentRecurrentSuccessive.setStatusURL(Creds.statusUrl);
            h2HPaymentRecurrentSuccessive.setSuccessURL(Creds.successUrl);
            h2HPaymentRecurrentSuccessive.setErrorURL(Creds.errorUrl);
            h2HPaymentRecurrentSuccessive.setAwaitingURL(Creds.awaitingUrl);
            h2HPaymentRecurrentSuccessive.setCancelURL(Creds.cancelUrl);
            h2HPaymentRecurrentSuccessive.setPaymentRecurringType(PaymentRecurringType.cof);
            h2HPaymentRecurrentSuccessive.setMerchantExemptionsSca(MerchantExemptionsSca.MIT);

            // Step 3 - Send Payment Request
            H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
            h2HPaymentAdapter.sendH2hPaymentRecurrentSuccessive(h2HPaymentRecurrentSuccessive, new ResponseListenerAdapter() {
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
