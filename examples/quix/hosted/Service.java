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
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixArticleService;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixCartService;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixItemCartItemService;
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixServicePaySolExtendedData;
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixService;
import com.comerciaglobalpayments.javaPaymentSDK.utils.Creds;

import java.util.ArrayList;
import java.util.List;

public class Service {

    public static void main(String[] args) {
        sendQuixHostedServiceRequest();
    }

    private static void sendQuixHostedServiceRequest() {
        try {
            // region Step 1 - Creating Credentials Object
            Credentials credentials = new Credentials();
            credentials.setMerchantId(Creds.merchantId);
            credentials.setMerchantPass(Creds.merchantPass);
            credentials.setEnvironment(Creds.environment);
            credentials.setProductId(Creds.productIdService);
            credentials.setApiVersion(5);
            // endregion

            // region Step 2 - Configure Payment Parameters
            HostedQuixService hostedQuixService = new HostedQuixService();
            hostedQuixService.setAmount("99");
            hostedQuixService.setCustomerId("903");
            hostedQuixService.setStatusURL(Creds.statusUrl);
            hostedQuixService.setCancelURL(Creds.cancelUrl);
            hostedQuixService.setErrorURL(Creds.errorUrl);
            hostedQuixService.setSuccessURL(Creds.successUrl);
            hostedQuixService.setAwaitingURL(Creds.awaitingUrl);
            hostedQuixService.setCustomerEmail("test@mail.com");
            hostedQuixService.setCustomerNationalId("99999999R");
            hostedQuixService.setDob("01-12-1999");
            hostedQuixService.setFirstName("Name");
            hostedQuixService.setLastName("Last Name");
            hostedQuixService.setIpAddress("0.0.0.0");

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

            hostedQuixService.setPaySolExtendedData(quixServicePaySolExtendedData);
            // endregion

            // Step 3 - Send Payment Request
            HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);
            hostedQuixPaymentAdapter.sendHostedQuixServiceRequest(hostedQuixService, new ResponseListenerAdapter() {
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
