package com.comerciaglobalpayments.javaPaymentSDK.examples.creditcards.h2h;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HRedirection;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class H2HExample {

    public static void main(String[] args) {
        sendH2hPaymentRequest();
    }

    public static void sendH2hPaymentRequest() {
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
            H2HRedirection h2HRedirection = new H2HRedirection();
            h2HRedirection.setAmount("50.4321222");
            h2HRedirection.setCurrency(Currency.EUR);
            h2HRedirection.setCountry(CountryCode.ES);
            h2HRedirection.setCardNumber("4907270002222227");
            h2HRedirection.setCustomerId("903");
            h2HRedirection.setChName("First name Last name");
            h2HRedirection.setCvnNumber("123");
            h2HRedirection.setExpDate("0625");
            h2HRedirection.setPaymentSolution(PaymentSolutions.creditcards);
            h2HRedirection.setStatusURL(Creds.statusUrl);
            h2HRedirection.setSuccessURL(Creds.successUrl);
            h2HRedirection.setErrorURL(Creds.errorUrl);
            h2HRedirection.setAwaitingURL(Creds.awaitingUrl);
            h2HRedirection.setCancelURL(Creds.cancelUrl);
            h2HRedirection.setForceTokenRequest(false);

            List<Pair<String, String>> merchantParams = new ArrayList<>();
            merchantParams.add(new Pair<>("name", "pablo"));
            merchantParams.add(new Pair<>("surname", "ferrer"));

            h2HRedirection.setMerchantParameters(merchantParams);

            System.out.println("Merchant Parameters:");
            h2HRedirection.getMerchantParameters().forEach(parameter -> {
                System.out.println("- key = " + parameter.getFirst() + ", value = " + parameter.getSecond());
            });

            // Step 3 - Send Payment Request
            H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
            h2HPaymentAdapter.sendH2hPaymentRequest(h2HRedirection, new ResponseListenerAdapter() {
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
