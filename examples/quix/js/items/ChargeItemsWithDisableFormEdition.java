package com.comerciaglobalpayments.javaPaymentSDK.examples.quix.js.items;

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSQuixPaymentAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter;
import com.comerciaglobalpayments.javaPaymentSDK.enums.*;
import com.comerciaglobalpayments.javaPaymentSDK.enums.Error;
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.FieldException;
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixAddress;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixBilling;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixArticleProduct;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixCartProduct;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixItemCartItemProduct;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixItemPaySolExtendedData;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_js.JSQuixItem;
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class ChargeItemsWithDisableFormEdition {

    public static void main(String[] args) {
        sendQuixJsItemRequest();
    }

    private static void sendQuixJsItemRequest() {
        try {
            // region Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdItem);
            credentials.setApiVersion(5);
            // endregion

            // region Step 2 - Configure Payment Parameters
            JSQuixItem jsQuixItem = new JSQuixItem();
            jsQuixItem.setPrepayToken("4c8eb302-890f-43ff-aa8c-7ee77acec777");
            jsQuixItem.setAmount("99");
            jsQuixItem.setCustomerId("55");
            jsQuixItem.setStatusURL(Creds.statusUrl);
            jsQuixItem.setCancelURL(Creds.cancelUrl);
            jsQuixItem.setErrorURL(Creds.errorUrl);
            jsQuixItem.setSuccessURL(Creds.successUrl);
            jsQuixItem.setAwaitingURL(Creds.awaitingUrl);
            jsQuixItem.setCustomerEmail("test@mail.com");
            jsQuixItem.setCustomerNationalId("99999999R");
            jsQuixItem.setDob("01-12-1999");
            jsQuixItem.setFirstName("Name");
            jsQuixItem.setLastName("Last Name");
            jsQuixItem.setIpAddress("0.0.0.0");

            QuixArticleProduct quixArticleProduct = new QuixArticleProduct();
            quixArticleProduct.setName("Nombre del servicio 2");
            quixArticleProduct.setReference("4912345678903");
            quixArticleProduct.setUnitPriceWithTax(99);
            quixArticleProduct.setCategory(Category.digital);

            QuixItemCartItemProduct quixItemCartItemProduct = new QuixItemCartItemProduct();
            quixItemCartItemProduct.setArticle(quixArticleProduct);
            quixItemCartItemProduct.setUnits(1);
            quixItemCartItemProduct.setAutoShipping(true);
            quixItemCartItemProduct.setTotal_price_with_tax(99);

            List<QuixItemCartItemProduct> items = new ArrayList<>();
            items.add(quixItemCartItemProduct);

            QuixCartProduct quixCartProduct = new QuixCartProduct();
            quixCartProduct.setCurrency(Currency.EUR);
            quixCartProduct.setItems(items);
            quixCartProduct.setTotalPriceWithTax(99);

            QuixAddress quixAddress = new QuixAddress();
            quixAddress.setCity("Barcelona");
            quixAddress.setCountry(CountryCode.ES);
            quixAddress.setStreetAddress("Nombre de la vía y nº");
            quixAddress.setPostalCode("08003");

            QuixBilling quixBilling = new QuixBilling();
            quixBilling.setAddress(quixAddress);
            quixBilling.setFirstName("Nombre");
            quixBilling.setLastName("Apellido");

            QuixItemPaySolExtendedData quixItemPaySolExtendedData = new QuixItemPaySolExtendedData();
            quixItemPaySolExtendedData.setCart(quixCartProduct);
            quixItemPaySolExtendedData.setBilling(quixBilling);
            quixItemPaySolExtendedData.setProduct("instalments");
            quixItemPaySolExtendedData.setDisableFormEdition(true);

            jsQuixItem.setPaySolExtendedData(quixItemPaySolExtendedData);
            // endregion

            // Step 3 - Send Payment Request
            JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);
            jsQuixPaymentAdapter.sendJSQuixItemRequest(jsQuixItem, new ResponseListenerAdapter() {
                // Step 4 - Handle the Response
                @Override
                public void onError(Error error, String errorMessage) {
                    System.out.println("Error received - " + error.name() + " - " + errorMessage);
                }

                @Override
                public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
                    System.out.println("Response Received");
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
