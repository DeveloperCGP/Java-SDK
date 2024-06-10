package com.comerciaglobalpayments.javaPaymentSDK.examples.quix.js.service;

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSQuixPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixAddress;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixBilling;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixArticleService;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixCartService;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixItemCartItemService;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixServicePaySolExtendedData;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_js.JSQuixService;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class ChargeService {

    public static void main(String[] args) {
        sendQuixJsServiceRequest();
    }

    private static void sendQuixJsServiceRequest() {
        try {
            // region Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdService);
            credentials.setApiVersion(5);
            // endregion

            // region Step 2 - Configure Payment Parameters
            JSQuixService jsQuixService = new JSQuixService();
            jsQuixService.setAmount("99");
            jsQuixService.setPrepayToken("0bcf287f-7687-40c2-ab7a-6d8e86f3d75e");
            jsQuixService.setCustomerId("55");
            jsQuixService.setStatusURL(Creds.statusUrl);
            jsQuixService.setCancelURL(Creds.cancelUrl);
            jsQuixService.setErrorURL(Creds.errorUrl);
            jsQuixService.setSuccessURL(Creds.successUrl);
            jsQuixService.setAwaitingURL(Creds.awaitingUrl);
            jsQuixService.setCustomerEmail("test@mail.com");
            jsQuixService.setCustomerNationalId("99999999R");
            jsQuixService.setDob("01-12-1999");
            jsQuixService.setFirstName("Name");
            jsQuixService.setLastName("Last Name");
            jsQuixService.setIpAddress("0.0.0.0");

            QuixArticleService quixArticleService = new QuixArticleService();
            quixArticleService.setName("Nombre del servicio 2");
            quixArticleService.setReference("4912345678903");
            quixArticleService.setEndDate("2024-12-31T23:59:59+01:00");
            quixArticleService.setUnitPriceWithTax(99);
            quixArticleService.setCategory(Category.digital);

            QuixItemCartItemService quixItemCartItemService = new QuixItemCartItemService();
            quixItemCartItemService.setArticle(quixArticleService);
            quixItemCartItemService.setUnits(1);
            quixItemCartItemService.setAutoShipping(true);
            quixItemCartItemService.setTotalPriceWithTax(99);

            List<QuixItemCartItemService> items = new ArrayList<>();
            items.add(quixItemCartItemService);

            QuixCartService quixCartService = new QuixCartService();
            quixCartService.setCurrency(Currency.EUR);
            quixCartService.setItems(items);
            quixCartService.setTotalPriceWithTax(99);

            QuixAddress quixAddress = new QuixAddress();
            quixAddress.setCity("Barcelona");
            quixAddress.setCountry(CountryCode.ES);
            quixAddress.setStreetAddress("Nombre de la vía y nº");
            quixAddress.setPostalCode("28003");

            QuixBilling quixBilling = new QuixBilling();
            quixBilling.setAddress(quixAddress);
            quixBilling.setFirstName("Nombre");
            quixBilling.setLastName("Apellido");

            QuixServicePaySolExtendedData quixServicePaySolExtendedData = new QuixServicePaySolExtendedData();
            quixServicePaySolExtendedData.setCart(quixCartService);
            quixServicePaySolExtendedData.setBilling(quixBilling);
            quixServicePaySolExtendedData.setProduct("instalments");

            jsQuixService.setPaySolExtendedData(quixServicePaySolExtendedData);
            // endregion

            // Step 3 - Send Payment Request
            JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);
            jsQuixPaymentAdapter.sendJSQuixServiceRequest(jsQuixService, new ResponseListenerAdapter() {
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
