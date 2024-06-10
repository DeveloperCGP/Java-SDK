package com.comerciaglobalpayments.javaPaymentSDK.examples.creditcards.h2h;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.PaymentSolutions;
import com.comerciaglobalpayments.javaPaymentSDK.enums.TransactionResult;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HVoid;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

public class Void {

    public static void main(String[] args) {
        sendVoidPaymentRequest();
    }


    public static void sendVoidPaymentRequest() {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            // Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantPass(Creds.merchantPass);
            credentials.setEnvironment(Creds.environment);
            credentials.setApiVersion(5);

            // Step 2 - Configure Payment Parameters
            H2HVoid h2HVoid = new H2HVoid();
            h2HVoid.setPaymentSolution(PaymentSolutions.caixapucpuce);
            h2HVoid.setTransactionId("7817740");
            h2HVoid.setMerchantTransactionId("76969499");

            // Step 3 - Send Payment Request
            H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
            h2HPaymentAdapter.sendH2hVoidRequest(h2HVoid, new ResponseListenerAdapter() {
                // Step 4 - Handle the Response
                @Override
                public void onError(Error error, String errorMessage) {
                    System.out.println("Error received - " + error.name() + " - " + errorMessage);
                }

                @Override
                public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
                    System.out.println("Final Notification Received");
                    System.out.println(gson.toJson(notification));
                    System.out.println("Transaction Result = " + transactionResult.name());
                }
            });
        }
        catch (FieldException fieldException)
        {
            fieldException.printStackTrace();
        }
    }
}
