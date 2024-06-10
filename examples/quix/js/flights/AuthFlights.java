package com.comerciaglobalpayments.javaPaymentSDK.examples.quix.js.flights;

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.JSPaymentListener;
import com.comerciaglobalpayments.javaPaymentSDK.enums.CountryCode;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Currency;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.enums.OperationTypes;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.js.JSAuthorizationRequest;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.JSAuthorizationResponse;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

public class AuthFlights {

    public static void main(String[] args) {
        sendAuthPaymentRequest();
    }

    public static void sendAuthPaymentRequest() {
        try {
            // Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantKey(Creds.merchantKey);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdFlight);
            credentials.setApiVersion(5);

            // Step 2 - Configure Payment Parameters
            JSAuthorizationRequest jsAuthorizationRequest = new JSAuthorizationRequest();
            jsAuthorizationRequest.setCountry(CountryCode.ES);
            jsAuthorizationRequest.setCustomerId("55");
            jsAuthorizationRequest.setCurrency(Currency.EUR);
            jsAuthorizationRequest.setOperationType(OperationTypes.DEBIT);
            jsAuthorizationRequest.setAnonymousCustomer(true);

            // Step 3 - Send Payment Request
            JSPaymentAdapter jsPaymentAdapter = new JSPaymentAdapter(credentials);
            jsPaymentAdapter.sendJSAuthorizationRequest(jsAuthorizationRequest, new JSPaymentListener() {
                // Step 4 - Handle the Response
                @Override
                public void onError(Error error, String errorMessage) {
                    System.out.println("Error received - " + error.name() + " - " + errorMessage);
                }

                @Override
                public void onAuthorizationResponseReceived(String rawResponse, JSAuthorizationResponse response) {
                    System.out.println("AuthToken Received: " + response.getAuthToken());
                    System.out.println(rawResponse);
                }
            });
        } catch (FieldException fieldException) {
            fieldException.printStackTrace();
        }
    }
}
