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
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixArticleProduct;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixCartProduct;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixItemCartItemProduct;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixItemPaySolExtendedData;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixItem;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class Items {

    public static void main(String[] args) {
        sendQuixHostedItemRequest();
    }

    private static void sendQuixHostedItemRequest() {
        try {
            // region Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantPass(Creds.merchantPass);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdItem);
            credentials.setApiVersion(5);
            // endregion

            // region Step 2 - Configure Payment Parameters
            HostedQuixItem hostedQuixItem = new HostedQuixItem();
            hostedQuixItem.setAmount("99");
            hostedQuixItem.setCustomerId("903");
            hostedQuixItem.setStatusURL(Creds.statusUrl);
            hostedQuixItem.setCancelURL(Creds.cancelUrl);
            hostedQuixItem.setErrorURL(Creds.errorUrl);
            hostedQuixItem.setSuccessURL(Creds.successUrl);
            hostedQuixItem.setAwaitingURL(Creds.awaitingUrl);
            hostedQuixItem.setCustomerEmail("test@mail.com");
            hostedQuixItem.setCustomerNationalId("99999999R");
            hostedQuixItem.setDob("01-12-1999");
            hostedQuixItem.setFirstName("Name");
            hostedQuixItem.setLastName("Last Name");
            hostedQuixItem.setIpAddress("0.0.0.0");

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
            quixAddress.setPostalCode("28003");

            QuixBilling quixBilling = new QuixBilling();
            quixBilling.setAddress(quixAddress);
            quixBilling.setFirstName("Nombre");
            quixBilling.setLastName("Apellido");

            QuixItemPaySolExtendedData quixItemPaySolExtendedData = new QuixItemPaySolExtendedData();
            quixItemPaySolExtendedData.setCart(quixCartProduct);
            quixItemPaySolExtendedData.setBilling(quixBilling);
            quixItemPaySolExtendedData.setProduct("instalments");

            hostedQuixItem.setPaySolExtendedData(quixItemPaySolExtendedData);
            // endregion

            // Step 3 - Send Payment Request
            HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);
            hostedQuixPaymentAdapter.sendHostedQuixItemRequest(hostedQuixItem, new ResponseListenerAdapter() {
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
