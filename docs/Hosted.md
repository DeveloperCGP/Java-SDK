# Hosted Documentation

## Table of Contents
- [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object)
- [Hosted](#hosted)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters)
    - [Step 3: Send Payment Hosted Redirection Request](#step-3-send-payment-hosted-redirection-request)
    - [Step 4: Handle the Response](#step-4-handle-the-response)
- [Hosted Recurrent](#hosted-recurrent)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-1)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters-1)
    - [Step 3: Send Payment Hosted Redirection Request](#step-3-send-payment-hosted-redirection-request-1)
    - [Step 4: Handle the Response](#step-4-handle-the-response-1)

This documentation is focusing on how to make Hosted transactions using the SDK. This payment method focuses on sending the payment details and then showing a web page directed from the Addon Payments for the user to enter the card data and proceed with the transaction.

## Common Prerequisite: Creating Credentials Object

Set Up Credentials

First, instantiate the Credentials object with your merchant details. This includes your Merchant ID, Merchant Pass which are essential for authenticating requests to the AddonPayments API.

* Merchant ID
    * Identifier of your business on the Addon Payments platform.
* Merchant Password
    * merchantPassword is the Secret Passphrase used inside AES-256 encryption provided by AddonPayments.
* Environment
    * The environment that will be used.
    * It can be "STAGING" or "PRODUCTION".
* Product Id
    * The product id that will be used in the request.

```java
Credentials credentials = new Credentials();
credentials.setMerchantId(Creds.merchantId);
credentials.setMerchantPass(Creds.merchantPass);
credentials.setEnvironment(Creds.environment);
credentials.setProductId(Creds.productId);
```

## Hosted

The steps are designed to guide you through setting up your credentials, configuring transaction parameters, sending a request, and handling responses, including redirect URLs and notifications.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the ["Common Prerequisite: Creating Credentials Object"](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Next, create an instance of HostedPaymentRedirection and set the necessary payment parameters. This includes the currency, payment solution, product ID, amount, and various URLs for handling different outcomes (e.g., success, error, cancel).

* setAmount(string)
    * To set payment amount
* setCurrency(CurrencyCodes Enum)
    * To set the payment currency
* setCountry(CountryCode Enum)
    * To set the country
* setPaymentSolution(PaymentSolutionsEnum)
    * To set the payment solution
* setCustomerId(string)
    * To set the customer id
* setMerchantTransactionId(string)
    * To set the merchant transaction id
    * It needs to be unique for each transaction
    * It will be set to a random value at the initialization of the object, so don't use the setter to leave it random
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
HostedPaymentRedirection hostedPaymentRedirection = new HostedPaymentRedirection();
hostedPaymentRedirection.setAmount("50");
hostedPaymentRedirection.setCurrency(Currency.EUR);
hostedPaymentRedirection.setCountry(CountryCode.ES);
hostedPaymentRedirection.setPaymentSolution(PaymentSolutions.creditcards);
hostedPaymentRedirection.setCustomerId("903");
hostedPaymentRedirection.setMerchantTransactionId("12345678");
hostedPaymentRedirection.setStatusURL(Creds.statusUrl);
hostedPaymentRedirection.setCancelURL(Creds.cancelUrl);
hostedPaymentRedirection.setErrorURL(Creds.errorUrl);
hostedPaymentRedirection.setSuccessURL(Creds.successUrl);
hostedPaymentRedirection.setAwaitingURL(Creds.awaitingUrl);
```

### Step 3: Send Payment Hosted Redirection Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of HostedPaymentAdapter with your previously configured credentials and then calling the sendHostedPaymentRequest method with your hostedPaymentRedirection configuration and a new ResponseListenerAdapter to handle the callbacks.

```java
HostedPaymentAdapter hostedPaymentAdapter = new HostedPaymentAdapter(credentials);

hostedPaymentAdapter.sendHostedPaymentRequest(hostedPaymentRedirection, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 4: Handle the Response

Implement the ResponseListenerAdapter Adapter to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

*onError:* This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.

*onRedirectionURLReceived:* This method receives the URL to which the user should be redirected to complete the payment process. Handling this callback is crucial for redirecting the user to the payment gateway, and it contains a callback that should be invoked when the notification is received in the status.

```java
@Override
public void onError(Error error, String errorMessage) {
     System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onRedirectionURLReceived(String redirectionURL) {
     System.out.println("Redirection Url Received");
     System.out.println("Url = " + redirectionURL);
}
```

## Hosted Recurrent

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the ["Common Prerequisite: Creating Credentials Object"](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Next, create an instance of HostedPaymentRecurrentInitial and set the necessary payment parameters. This includes the currency, payment solution, product ID, amount, and various URLs for handling different outcomes (e.g., success, error, cancel).

* setAmount(int)
    * To set payment amount
* setCurrency(CurrencyCodes Enum)
    * To set the payment currency
* setCountry(CountryCode Enum)
    * Set the country
* setCustomerId(string)
    * To set the customer id
* setPaymentSolution(PaymentSolutions Enum)
    * To set the payment solution
* setMerchantTransactionId(string)
    * To set the merchant transaction id
    * It needs to be unique for each transaction
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
HostedPaymentRecurrentInitial hostedPaymentRecurrentInitial = new HostedPaymentRecurrentInitial();

hostedPaymentRecurrentInitial.setCurrency(Currency.EUR);
hostedPaymentRecurrentInitial.setPaymentSolution(PaymentSolutions.creditcards);
hostedPaymentRecurrentInitial.setAmount("50");
hostedPaymentRecurrentInitial.setCountry(CountryCode.ES);
hostedPaymentRecurrentInitial.setCustomerId("903");
hostedPaymentRecurrentInitial.setStatusURL(Creds.statusUrl);
hostedPaymentRecurrentInitial.setCancelURL(Creds.cancelUrl);
hostedPaymentRecurrentInitial.setErrorURL(Creds.errorUrl);
hostedPaymentRecurrentInitial.setSuccessURL(Creds.successUrl);
hostedPaymentRecurrentInitial.setAwaitingURL(Creds.awaitingUrl);
```

### Step 3: Send Payment Hosted Redirection Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of HostedPaymentAdapter with your previously configured credentials and then calling the sendHostedRecurrentInitial method with your hostedPaymentRecurrentInitial configuration and a new ResponseListenerAdapter to handle the callbacks.

```java
HostedPaymentAdapter hostedPaymentAdapter = new HostedPaymentAdapter(credentials);

hostedPaymentController.sendHostedRecurrentInitial(hostedPaymentRecurrentInitial, new ResponseListenerAdapter() {


    // Callback methods for handling responses will be implemented here...
});
```

### Step 4: Handle the Response

Implement the ResponseListenerAdapter Adapter to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

*onError:* This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.

*onRedirectionURLReceived:* This method receives the URL to which the user should be redirected to complete the payment process. Handling this callback is crucial for redirecting the user to the payment gateway, and it contains a callback that should be invoked when the notification is received in the status.

```java
@Override
public void onError(Error error, String errorMessage) {
     System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onRedirectionURLReceived(String redirectionURL) {
     System.out.println("Redirection Url Received");
     System.out.println("Url = " + redirectionURL);
}
```