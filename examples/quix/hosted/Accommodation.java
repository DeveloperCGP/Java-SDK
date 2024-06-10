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
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixAccommodationPaySolExtendedData;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixArticleAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixCartAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixItemCartItemAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class Accommodation {

    public static void main(String[] args) {
        sendQuixHostedAccommodationRequest();
    }

    private static void sendQuixHostedAccommodationRequest() {
        try {
            // region Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantPass(Creds.merchantPass);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdAccommodation);
            credentials.setApiVersion(5);
            // endregion

            // region Step 2 - Configure Payment Parameters
            HostedQuixAccommodation hostedQuixAccommodation = new HostedQuixAccommodation();
            hostedQuixAccommodation.setAmount("99");
            hostedQuixAccommodation.setCustomerId("903");
            hostedQuixAccommodation.setStatusURL(Creds.statusUrl);
            hostedQuixAccommodation.setCancelURL(Creds.cancelUrl);
            hostedQuixAccommodation.setErrorURL(Creds.errorUrl);
            hostedQuixAccommodation.setSuccessURL(Creds.successUrl);
            hostedQuixAccommodation.setAwaitingURL(Creds.awaitingUrl);
            hostedQuixAccommodation.setCustomerEmail("test@mail.com");
            hostedQuixAccommodation.setDob("01-12-1999");
            hostedQuixAccommodation.setCustomerNationalId("99999999R");
            hostedQuixAccommodation.setFirstName("Name");
            hostedQuixAccommodation.setLastName("Last Name");
            hostedQuixAccommodation.setIpAddress("0.0.0.0");

            QuixAddress quixAddress = new QuixAddress();
            quixAddress.setCity("Barcelona");
            quixAddress.setCountry(CountryCode.ES);
            quixAddress.setStreetAddress("Nombre de la vía y nº");
            quixAddress.setPostalCode("28003");

            QuixArticleAccommodation quixArticleAccommodation = new QuixArticleAccommodation();
            quixArticleAccommodation.setName("Nombre del servicio 2");
            quixArticleAccommodation.setReference("4912345678903");
            quixArticleAccommodation.setCheckinDate("2024-10-30T00:00:00+01:00");
            quixArticleAccommodation.setCheckoutDate("2024-12-31T23:59:59+01:00");
            quixArticleAccommodation.setGuests(1);
            quixArticleAccommodation.setEstablishmentName("Hotel");
            quixArticleAccommodation.setAddress(quixAddress);
            quixArticleAccommodation.setUnitPriceWithTax(99);
            quixArticleAccommodation.setCategory(Category.digital);

            QuixItemCartItemAccommodation quixItemCartItemAccommodation = new QuixItemCartItemAccommodation();
            quixItemCartItemAccommodation.setArticle(quixArticleAccommodation);
            quixItemCartItemAccommodation.setUnits(1);
            quixItemCartItemAccommodation.setAuto_shipping(true);
            quixItemCartItemAccommodation.setTotalPriceWithTax(99);

            List<QuixItemCartItemAccommodation> items = new ArrayList<>();
            items.add(quixItemCartItemAccommodation);

            QuixCartAccommodation quixCartAccommodation = new QuixCartAccommodation();
            quixCartAccommodation.setCurrency(Currency.EUR);
            quixCartAccommodation.setItems(items);
            quixCartAccommodation.setTotal_price_with_tax(99);

            QuixBilling quixBilling = new QuixBilling();
            quixBilling.setAddress(quixAddress);
            quixBilling.setFirstName("Nombre");
            quixBilling.setLastName("Apellido");

            QuixAccommodationPaySolExtendedData quixAccommodationPaySolExtendedData = new QuixAccommodationPaySolExtendedData();
            quixAccommodationPaySolExtendedData.setCart(quixCartAccommodation);
            quixAccommodationPaySolExtendedData.setBilling(quixBilling);
            quixAccommodationPaySolExtendedData.setProduct("instalments");

            hostedQuixAccommodation.setPaySolExtendedData(quixAccommodationPaySolExtendedData);
            // endregion

            // Step 3 - Send Payment Request
            HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);
            hostedQuixPaymentAdapter.sendHostedQuixAccommodationRequest(hostedQuixAccommodation, new ResponseListenerAdapter() {
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
