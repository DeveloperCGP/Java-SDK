package com.comerciaglobalpayments.javaPaymentSDK.examples.quix.js.flights;

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSQuixPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixAddress;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixBilling;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_flight.*;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_js.JSQuixFlight;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class ChargeFlights {

    public static void main(String[] args) {
        sendQuixJsFlightsRequest();
    }

    private static void sendQuixJsFlightsRequest() {
        try {
            // region Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantPass(Creds.merchantPass);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdFlight);
            credentials.setApiVersion(5);
            // endregion

            // region Step 2 - Configure Payment Parameters
            JSQuixFlight jsQuixFlight = new JSQuixFlight();
            jsQuixFlight.setAmount("99");
            jsQuixFlight.setPrepayToken("daf95d2b-f5a4-41a9-ae99-ad88736e4da3");
            jsQuixFlight.setCustomerId("55");
            jsQuixFlight.setStatusURL(Creds.statusUrl);
            jsQuixFlight.setCancelURL(Creds.cancelUrl);
            jsQuixFlight.setErrorURL(Creds.errorUrl);
            jsQuixFlight.setSuccessURL(Creds.successUrl);
            jsQuixFlight.setAwaitingURL(Creds.awaitingUrl);
            jsQuixFlight.setCustomerEmail("test@mail.com");
            jsQuixFlight.setCustomerNationalId("99999999R");
            jsQuixFlight.setDob("01-12-1999");
            jsQuixFlight.setFirstName("Name");
            jsQuixFlight.setLastName("Last Name");
            jsQuixFlight.setIpAddress("0.0.0.0");

            QuixPassengerFlight quixPassengerFlight = new QuixPassengerFlight();
            quixPassengerFlight.setFirstName("Pablo");
            quixPassengerFlight.setLastName("Navvaro");

            List<QuixPassengerFlight> passangers = new ArrayList<>();
            passangers.add(quixPassengerFlight);

            QuixSegmentFlight quixSegmentFlight = new QuixSegmentFlight();
            quixSegmentFlight.setIataDepartureCode("MAD");
            quixSegmentFlight.setIataDestinationCode("BCN");

            List<QuixSegmentFlight> segments = new ArrayList<>();
            segments.add(quixSegmentFlight);

            QuixArticleFlight quixArticleFlight = new QuixArticleFlight();
            quixArticleFlight.setName("Nombre del servicio 2");
            quixArticleFlight.setReference("4912345678903");
            quixArticleFlight.setDepartureDate("2024-12-31T23:59:59+01:00");
            quixArticleFlight.setPassengers(passangers);
            quixArticleFlight.setSegments(segments);
            quixArticleFlight.setUnitPriceWithTax(99);
            quixArticleFlight.setCategory(Category.digital);

            QuixItemCartItemFlight quixItemCartItemFlight = new QuixItemCartItemFlight();
            quixItemCartItemFlight.setArticle(quixArticleFlight);
            quixItemCartItemFlight.setUnits(1);
            quixItemCartItemFlight.setAutoShipping(true);
            quixItemCartItemFlight.setTotalPriceWithTax(99);

            List<QuixItemCartItemFlight> items = new ArrayList<>();
            items.add(quixItemCartItemFlight);

            QuixCartFlight quixCartFlight = new QuixCartFlight();
            quixCartFlight.setCurrency(Currency.EUR);
            quixCartFlight.setItems(items);
            quixCartFlight.setTotalPriceWithTax(99);

            QuixAddress quixAddress = new QuixAddress();
            quixAddress.setCity("Barcelona");
            quixAddress.setCountry(CountryCode.ES);
            quixAddress.setStreetAddress("Nombre de la vía y nº");
            quixAddress.setPostalCode("28003");

            QuixBilling quixBilling = new QuixBilling();
            quixBilling.setAddress(quixAddress);
            quixBilling.setFirstName("Nombre");
            quixBilling.setLastName("Apellido");

            QuixFlightPaySolExtendedData quixFlightPaySolExtendedData = new QuixFlightPaySolExtendedData();
            quixFlightPaySolExtendedData.setCart(quixCartFlight);
            quixFlightPaySolExtendedData.setBilling(quixBilling);
            quixFlightPaySolExtendedData.setProduct("instalments");

            jsQuixFlight.setPaySolExtendedData(quixFlightPaySolExtendedData);
            // endregion

            // Step 3 - Send Payment Request
            JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);
            jsQuixPaymentAdapter.sendJSQuixFlightRequest(jsQuixFlight, new ResponseListenerAdapter() {
                // Step 4 - Handle the Response
                @Override
                public void onError(Error error, String errorMessage) {
                    System.out.println("Error received - " + error.name() + " - " + errorMessage);
                }

                @Override
                public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
                    System.out.println("Intermediate Notification Received");
                    System.out.println(rawResponse);
                    System.out.println("Use the next two variables in the JS Library to complete the payment");
                    System.out.println("nemuruCartHash = " + notification.getNemuruCartHash());
                    System.out.println("nemuruAuthToken = " + notification.getNemuruAuthToken());
                    System.out.println("HTML Code: window['NEMURU'].checkoutNemuru(response.nemuru_auth_token, response.nemuru_cart_hash);\n" +
                            "window['NEMURU'].setStatusCallback(() => {\n" +
                            "    window.location = 'https://test.com/notification.html';\n" +
                            "});\n");
                }
            });
        } catch (FieldException fieldException) {
            fieldException.printStackTrace();
        }
    }
}
