package com.comerciaglobalpayments.javaPaymentSDK.examples.quix.js.accommodation;

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSQuixPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixAddress;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixBilling;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixAccommodationPaySolExtendedData;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixArticleAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixCartAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixItemCartItemAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_js.JSQuixAccommodation;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class ChargeAccommodation {

    public static void main(String[] args) {
        sendQuixJsAccommodationRequest();
    }

    private static void sendQuixJsAccommodationRequest() {
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
            JSQuixAccommodation jsQuixAccommodation = new JSQuixAccommodation();
            jsQuixAccommodation.setAmount("99.555555");
            jsQuixAccommodation.setCustomerId("55");
            jsQuixAccommodation.setPrepayToken("2795f021-f31c-4533-a74d-5d3d887a003b");
            jsQuixAccommodation.setStatusURL(Creds.statusUrl);
            jsQuixAccommodation.setCancelURL(Creds.cancelUrl);
            jsQuixAccommodation.setErrorURL(Creds.errorUrl);
            jsQuixAccommodation.setSuccessURL(Creds.successUrl);
            jsQuixAccommodation.setAwaitingURL(Creds.awaitingUrl);
            jsQuixAccommodation.setCustomerEmail("test@mail.com");
            jsQuixAccommodation.setCustomerNationalId("99999999R");
            jsQuixAccommodation.setDob("01-12-1999");
            jsQuixAccommodation.setFirstName("Name");
            jsQuixAccommodation.setLastName("Last Name");
            jsQuixAccommodation.setIpAddress("0.0.0.0");

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
            quixArticleAccommodation.setUnitPriceWithTax(99.555555);
            quixArticleAccommodation.setCategory(Category.digital);

            QuixItemCartItemAccommodation quixItemCartItemAccommodation = new QuixItemCartItemAccommodation();
            quixItemCartItemAccommodation.setArticle(quixArticleAccommodation);
            quixItemCartItemAccommodation.setUnits(1);
            quixItemCartItemAccommodation.setAuto_shipping(true);
            quixItemCartItemAccommodation.setTotalPriceWithTax(99.555555);

            List<QuixItemCartItemAccommodation> items = new ArrayList<>();
            items.add(quixItemCartItemAccommodation);

            QuixCartAccommodation quixCartAccommodation = new QuixCartAccommodation();
            quixCartAccommodation.setCurrency(Currency.EUR);
            quixCartAccommodation.setItems(items);
            quixCartAccommodation.setTotal_price_with_tax(99.555555);

            QuixBilling quixBilling = new QuixBilling();
            quixBilling.setAddress(quixAddress);
            quixBilling.setFirstName("Nombre");
            quixBilling.setLastName("Apellido");

            QuixAccommodationPaySolExtendedData quixAccommodationPaySolExtendedData = new QuixAccommodationPaySolExtendedData();
            quixAccommodationPaySolExtendedData.setCart(quixCartAccommodation);
            quixAccommodationPaySolExtendedData.setBilling(quixBilling);
            quixAccommodationPaySolExtendedData.setProduct("instalments");

            jsQuixAccommodation.setPaySolExtendedData(quixAccommodationPaySolExtendedData);
            //endregion

            // Step 3 - Send Payment Request
            JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);
            jsQuixPaymentAdapter.sendJSQuixAccommodationRequest(jsQuixAccommodation, new ResponseListenerAdapter() {
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
