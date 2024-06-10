package com.comerciaglobalpayments.javaPaymentSDK.examples.quix.hosted;

import com.comerciaglobalpayments.javaPaymentSDK.adapters.HostedQuixPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Category;
import com.comerciaglobalpayments.javaPaymentSDK.enums.CountryCode;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Currency;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixAddress;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixBilling;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_flight.*;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixFlight;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class Flights {

    public static void main(String[] args) {
        sendQuixHostedFlightsRequest();
    }

    private static void sendQuixHostedFlightsRequest() {
        try {
            // region Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantPass(Creds.merchantPass);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdFlight);
            credentials.setApiVersion(5);
            // endregion

            // Step 2 - Configure Payment Parameters
            HostedQuixFlight hostedQuixFlight = new HostedQuixFlight();
            hostedQuixFlight.setAmount("99");
            hostedQuixFlight.setCustomerId("903");
            hostedQuixFlight.setStatusURL(Creds.statusUrl);
            hostedQuixFlight.setCancelURL(Creds.cancelUrl);
            hostedQuixFlight.setErrorURL(Creds.errorUrl);
            hostedQuixFlight.setSuccessURL(Creds.successUrl);
            hostedQuixFlight.setAwaitingURL(Creds.awaitingUrl);
            hostedQuixFlight.setCustomerEmail("test@mail.com");
            hostedQuixFlight.setDob("01-12-1999");
            hostedQuixFlight.setCustomerNationalId("99999999R");
            hostedQuixFlight.setFirstName("Name");
            hostedQuixFlight.setLastName("Last Name");
            hostedQuixFlight.setIpAddress("0.0.0.0");

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

            hostedQuixFlight.setPaySolExtendedData(quixFlightPaySolExtendedData);
            // endregion

            // Step 3 - Send Payment Request
            HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);
            hostedQuixPaymentAdapter.sendHostedQuixFlightRequest(hostedQuixFlight, new ResponseListenerAdapter() {
                // Step 4 - Handle the Response
                @Override
                public void onError(Error error, String errorMessage) {
                    System.out.println("Error received - " + error.name() + " - " + errorMessage);
                }

                @Override
                public void onRedirectionURLReceived(String redirectionURL) {
                    System.out.println("Redirection Url Received");
                    System.out.println("Url = " + redirectionURL);
                }
            });
        } catch (FieldException fieldException) {
            fieldException.printStackTrace();
        }
    }
}
