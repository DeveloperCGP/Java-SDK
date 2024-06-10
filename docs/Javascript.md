# Javascript

## Table of Contents
- [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object)
- [JavaScript Authorization Request](#javascript-authorization-request)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite)
    - [Step 2: Configure JavaScript Authorization Request](#step-2-configure-javascript-authorization-request)
    - [Step 3: Send Authorization Request](#step-3-send-authorization-request)
    - [Step 4: Handle the Response](#step-4-handle-the-response)
- [JavaScript Charge Request](#javascript-charge-request)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-1)
    - [Step 2: Configure JavaScript Authorization Request](#step-2-configure-javascript-authorization-request-1)
    - [Step 3: Send Charge Request](#step-3-send-charge-request)
    - [Step 4: Handle the Response](#step-4-handle-the-response-1)
- [JavaScript Charge Recurring Request](#javascript-charge-recurring-request)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-2)
    - [Step 2: Configure JavaScript Authorization Request](#step-2-configure-javascript-authorization-request-2)
    - [Step 3: Send Charge Request](#step-3-send-charge-request-1)
    - [Step 4: Handle the Response](#step-4-handle-the-response-2)

## Common Prerequisite: Creating Credentials Object

Set Up Credentials

First, instantiate the Credentials object with your merchant details. This includes your Merchant ID, Merchant Pass which are essential for authenticating requests to the AddonPayments API.

* setMerchantId(string)
    * It is the indicator of your trade on the AP platform. It is provided by Support in the welcome email, it is common for both environments.
* setMerchantKey(string)
    * It is the JavaScript password. It is used to verify that the request is legitimate. For the staging environment it is sent in the welcome email, in production environments it is retrieved through the BackOffice.
* setEnvironment(Environment)
    * The environment that will be used.
    * It can be "STAGING" or "PRODUCTION".
* setProductId(String)
    * The product id that will be used in the request.

```java
Credentials credentials = new Credentials();
credentials.setMerchantId(Creds.merchantId);
credentials.setMerchantKey(Creds.merchantKey);
credentials.setEnvironment(Creds.environment);
credentials.setProductId(Creds.productId);
```

## JavaScript Authorization Request

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure JavaScript Authorization Request

Create an instance of JSAuthorizationRequest and configure it with necessary parameters. This includes setting the merchant ID, merchant key, product ID, country, customer ID, currency, and operation type.

* setCurrency(CurrencyCodes)
    * To set the payment currency.
* setCustomerId(string)
    * To set the customer id.
* setOperationType(OperationTypes)
    * Specifies the type of operation to perform.
* setCountry(CountryCode)
    * The customer's country.

```java
JSAuthorizationRequest jsAuthorizationRequest = new JSAuthorizationRequest();
jsAuthorizationRequest.setCountry(CountryCode.ES);
jsAuthorizationRequest.setCustomerId("55");
jsAuthorizationRequest.setCurrency(Currency.EUR);
jsAuthorizationRequest.setOperationType(OperationTypes.DEBIT);
```

### Step 3: Send Authorization Request

Instantiate JSPaymentAdapter with your credentials. Use the sendJSAuthorizationRequest method to send the authorization request. Implement the JSPaymentListener interface to handle the callback, which provides the authorization response or error.

```java
JSPaymentAdapter jsPaymentAdapter = new JSPaymentAdapter(credentials);
jsPaymentAdapter.sendJSAuthorizationRequest(jsAuthorizationRequest, new JSPaymentListener() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 4: Handle the Response

Implement the JSPaymentListener methods to handle the response from the AddonPayments API:

* onError: This method is invoked if there's an error during the authorization process. Use this to log the error or inform the user that the authorization failed.
* onAuthorizationResponseReceived: This method is called when an authorization response is successfully received. It provides an authorization token, which you can use for subsequent operations or to validate the transaction on the client side.
    * response: This "JSAuthorizationResponse" will contain a variable called "authToken" for the Authorization Token.

```java
jsPaymentController.sendJSAuthorizationRequest(jsAuthorizationRequest, new JSPaymentListener() {
    @Override
    public void onError(Error error, String errorMessage) {
        // Error handling code...
    }

    @Override
    public void onAuthorizationResponseReceived(String rawResponse, JSAuthorizationResponse response) {
        // Get the Auth Token
    }
});
```

## JavaScript Charge Request

Create and perform the charge request after using authToken to render the payment form at checkout and customer fills out all customer or payment information and submits the form. Addon Payments collects the payment information and generates a unique prepayToken that is linked to the original requested authToken values.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure JavaScript Authorization Request

Create an instance of JSCharge and configure it with necessary parameters. This includes setting the product ID, country, customer ID, transaction amount, currency, and various URLs for handling different outcomes.

* setAmount(int)
    * To set payment amount.
* setCurrency(CurrencyCodes Enums)
    * To set the payment currency.
* setCountry(CountryCode)
    * Set the country.
* setCustomerId(string)
    * To set the customer id.
* setPrepayToken(string)
    * PrepayToken that is linked to the original requested authToken values.
* setOperationType(OperationTypes)
    * Specifies the type of operation to perform.
* setPaymentSolution(PaymentSolutions)
    * To set the payment solution.
* setMerchantTransactionId(string)
    * To set the merchant transaction id.
    * It needs to be unique for each transaction.
* setStatusURL(string)
    * Sets the URL to which the payment gateway will send asynchronous notifications about the transaction's status.
* setSuccessURL(string)
    * Specifies the URL to redirect the customer to upon a successful payment.
* setErrorURL(string)
    * Defines the URL to redirect the customer to in case of a payment error.
* setAwaitingURL(string)
    * Sets the URL to redirect the customer to when the payment is in an awaiting state.
* setCancelURL(string)
    * Determines the URL to redirect the customer to if they decide to cancel the transaction at the payment gateway page.

```java
JSCharge jsCharge = new JSCharge();

jsCharge.setApiVersion("5");
jsCharge.setAmount("30");
jsCharge.setPrepayToken("56c9942b-d303-4421-b637-286d29b26dc3");
jsCharge.setCountry(CountryCode.ES);
jsCharge.setCustomerId("55");
jsCharge.setCurrency(Currency.EUR);
jsCharge.setOperationType(OperationTypes.DEBIT);
jsCharge.setPaymentSolution(PaymentSolutions.creditcards);
jsCharge.setStatusURL(Creds.statusUrl);
jsCharge.setCancelURL(Creds.cancelUrl);
jsCharge.setErrorURL(Creds.errorUrl);
jsCharge.setSuccessURL(Creds.successUrl);
jsCharge.setAwaitingURL(Creds.awaitingUrl);
```

### Step 3: Send Charge Request

Instantiate JSPaymentAdapter with your credentials. Use the sendJSChargeRequest method to send the authorization request. Implement the ResponseListenerAdapter interface to handle the callback, which provides the authorization response or error.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

JSPaymentAdapter jsPaymentAdapter = new JSPaymentAdapter(credentials);

jsPaymentAdapter.sendJSChargeRequest(jsCharge, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 4: Handle the Response

Implement the JSPaymentListener methods to handle the response from the AddonPayments API:

* onError: This method is invoked if there's an error during the authorization process. Use this to log the error or inform the user that the authorization failed.
* onResponseReceived: Process the response received from a transaction.

```java
jsPaymentController.sendJSAuthorizationRequest(jsAuthorizationRequest, new JSPaymentListener() {
    @Override
    public void onError(Error error, String errorMessage) {
        System.out.println("Error received - " + error.name() + " - " + errorMessage);
    }

    @Override
    public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
        String redirectionURL = notification.getRedirectUrl();
        System.out.println("Redirection url = " + redirectionURL);
    }
});
```

## JavaScript Charge Recurring Request

Create and perform the Recurring Charge request after using authToken

to render the payment form at checkout and customer fills out all customer or payment information and submits the form. Addon Payments collects the payment information and generates a unique prepayToken that is linked to the original requested authToken values.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure JavaScript Authorization Request

Create an instance of JSCharge and configure it with necessary parameters. This includes setting the product ID, country, customer ID, transaction amount, currency, and various URLs for handling different outcomes.

* setAmount(int)
    * To set payment amount.
* setCurrency(CurrencyCodes Enums)
    * To set the payment currency.
* setCountry(CountryCode)
    * Set the country.
* setCustomerId(string)
    * To set the customer id.
* setPrepayToken(string)
    * PrepayToken that is linked to the original requested authToken values.
* setOperationType(OperationTypes)
    * Specifies the type of operation to perform.
* setPaymentSolution(PaymentSolutions)
    * To set the payment solution.
* setMerchantTransactionId(string)
    * To set the merchant transaction id.
    * It needs to be unique for each transaction.
* setStatusURL(string)
    * Sets the URL to which the payment gateway will send asynchronous notifications about the transaction's status.
* setSuccessURL(string)
    * Specifies the URL to redirect the customer to upon a successful payment.
* setErrorURL(string)
    * Defines the URL to redirect the customer to in case of a payment error.
* setAwaitingURL(string)
    * Sets the URL to redirect the customer to when the payment is in an awaiting state.
* setCancelURL(string)
    * Determines the URL to redirect the customer to if they decide to cancel the transaction at the payment gateway page.

```java
JSPaymentRecurrentInitial jsPaymentRecurrentInitial = new JSPaymentRecurrentInitial();

jsPaymentRecurrentInitial.setApiVersion("5");
jsPaymentRecurrentInitial.setAmount("30");
jsPaymentRecurrentInitial.setPrepayToken("56c9942b-d303-4421-b637-286d29b26dc3");
jsPaymentRecurrentInitial.setCountry(CountryCode.ES);
jsPaymentRecurrentInitial.setCustomerId("55");
jsPaymentRecurrentInitial.setCurrency(Currency.EUR);
jsPaymentRecurrentInitial.setOperationType(OperationTypes.DEBIT);
jsPaymentRecurrentInitial.setPaymentSolution(PaymentSolutions.creditcards);
jsPaymentRecurrentInitial.setStatusURL("https://test/paymentNotification");
jsPaymentRecurrentInitial.setSuccessURL("https://test.com/success");
jsPaymentRecurrentInitial.setErrorURL("https://test.com/error");
jsPaymentRecurrentInitial.setAwaitingURL("https://test.com/awaiting");
jsPaymentRecurrentInitial.setCancelURL("https://test.com/cancel");
```

### Step 3: Send Charge Request

Instantiate JSPaymentAdapter with your credentials. Use the sendJSPaymentRecurrentInitial method to send the authorization request. Implement the ResponseListenerAdapter interface to handle the callback, which provides the authorization response or error.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

JSPaymentAdapter jsPaymentAdapter = new JSPaymentAdapter(credentials);

jsPaymentAdapter.sendJSPaymentRecurrentInitial(jsPaymentRecurrentInitial, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 4: Handle the Response

Implement the JSPaymentListener methods to handle the response from the AddonPayments API:

* onError: This method is invoked if there's an error during the authorization process. Use this to log the error or inform the user that the authorization failed.
* onResponseReceived: Process the response received from a transaction.

```java
jsPaymentController.sendJSPaymentRecurrentInitial(jsPaymentRecurrentInitial, new JSPaymentListener() {
    @Override
    public void onError(Error error, String errorMessage) {
        System.out.println("Error received - " + error.name() + " - " + errorMessage);
    }

    @Override
    public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
        String redirectionURL = notification.getRedirectUrl();
        System.out.println("Redirection url = " + redirectionURL);
    }
});
```