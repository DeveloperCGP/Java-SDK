# H2H Payment Integration

## Table of Contents
- [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object)
- [H2H Request](#h2h-request)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters)
    - [Step 3: Send Payment Request](#step-3-send-payment-request)
    - [Step 4: Handle the Response](#step-4-handle-the-response)
- [Pre-Authorization Request](#pre-authorization-request)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-1)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters-1)
    - [Step 3: Send Payment Request](#step-3-send-payment-request-1)
    - [Step 4: Handle the Response](#step-4-handle-the-response-1)
- [Capture Pre-Authorization](#capture-pre-authorization)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-2)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters-2)
    - [Step 3: Send Payment Request](#step-3-send-payment-request-2)
    - [Step 4: Handle the Response](#step-4-handle-the-response-2)
- [Recurrent Initial](#recurrent-initial)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-3)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters-3)
    - [Step 3: Send Payment Request](#step-3-send-payment-request-3)
    - [Step 4: Handle the Response](#step-4-handle-the-response-3)
- [Recurrent Subsequent](#recurrent-subsequent)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-4)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters-4)
    - [Step 3: Send Payment Request](#step-3-send-payment-request-4)
    - [Step 4: Handle the Response](#step-4-handle-the-response-4)
- [Refund](#refund)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-5)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters-5)
    - [Step 3: Send Payment Request](#step-3-send-payment-request-5)
    - [Step 4: Handle the Response](#step-4-handle-the-response-5)
- [Void](#void)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-6)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters-6)
    - [Step 3: Send Payment Request](#step-3-send-payment-request-6)
    - [Step 4: Handle the Response](#step-4-handle-the-response-6)

This section provides a step-by-step guide for implementing Host-to-Host payment transactions using the Java SDK. This method enables direct communication between the merchant's server and the AddonPayments API, offering a more integrated and seamless payment processing experience.

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
credentials.setEnvironment(Environment.STAGING);
credentials.setProductId(Creds.productId);
```

## H2H Request

Sending a normal payment H2H request which is used in a normal payment.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Create an instance of H2HRedirection and set the necessary parameters for the payment transaction. This includes card details, transaction amount, currency, and various URLs for handling different outcomes.

* setAmount(String): The transaction amount.
* setCurrency(Currency): The currency code for the transaction.
* setCountry(CountryCode): The customer's country.
* setCustomerId(String): A unique identifier for the customer.
* setCardNumber(String): The customer's card number.
* setChName(String): The cardholder's name.
* setCvnNumber(String): The card verification number.
* setExpDate(String): The card's expiry date in MMYY format.
* setPaymentSolution(PaymentSolutions): The payment solution identifier.
* setStatusURL(String): URL for status updates.
* setSuccessURL(String): URL to redirect on success.
* setErrorURL(String): URL to redirect on error.
* setAwaitingURL(String): URL to redirect when awaiting further action.
* setCancelURL(String): URL to redirect on cancellation.

```java
H2HRedirection h2HRedirection = new H2HRedirection();
h2HRedirection.setAmount("50");
h2HRedirection.setCurrency(Currency.EUR);
h2HRedirection.setCountry(CountryCode.ES);
h2HRedirection.setCardNumber("4907270002222227");
h2HRedirection.setCustomerId("903");
h2HRedirection.setChName("First name Last name");
h2HRedirection.setCvnNumber("123");
h2HRedirection.setExpDate("0625");
h2HRedirection.setPaymentSolution(PaymentSolutions.creditcards);
h2HRedirection.setStatusURL(Creds.statusUrl);
h2HRedirection.setCancelURL(Creds.cancelUrl);
h2HRedirection.setErrorURL(Creds.errorUrl);
h2HRedirection.setSuccessURL(Creds.successUrl);
h2HRedirection.setAwaitingURL(Creds.awaitingUrl);
```

### Step 3: Send Payment Request

Instantiate H2HPaymentAdapter with your credentials and call the sendH2hPaymentRequest method, passing in the h2HRedirection object and a ResponseListenerAdapter to handle callbacks.

```java
H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
h2HPaymentAdapter.sendH2hPaymentRequest(h2HRedirection, new ResponseListenerAdapter() {
    // Implement callback methods...
});
```

### Step 4: Handle the Response

Implement the ResponseListener interface methods to process the response from the AddonPayments API. This includes handling errors, processing notifications, and extracting the redirection URL if needed.

* *onError:* Handle errors encountered during the transaction.
* *onResponseReceived:* Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) { 
    System.out.println("Response Received");
    System.out.println(gson.toJson(notification));
    String redirectionURL = notification.getRedirectUrl();
    System.out.println("Redirection url = " + redirectionURL);
}
```

## Pre-Authorization Request

Sending a normal payment H2H request which is used in a normal payment.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Create an instance of H2HPreAuthorization and set the necessary parameters for the payment transaction. This includes card details, transaction amount, currency, and various URLs for handling different outcomes.

* setAmount(String): The transaction amount.
* setCurrency(Currency): The currency code for the transaction.
* setCountry(CountryCode): The customer's country.
* setCustomerId(String): A unique identifier for the customer.
* setCardNumber(String): The customer's card number.
* setChName(String): The cardholder's name.
* setCvnNumber(String): The card verification number.
* setExpDate(String): The card's expiry date in MMYY format.
* setPaymentSolution(PaymentSolutions): The payment solution identifier.
* setStatusURL(String): URL for status updates.
* setSuccessURL(String

): URL to redirect on success.
* setErrorURL(String): URL to redirect on error.
* setAwaitingURL(String): URL to redirect when awaiting further action.
* setCancelURL(String): URL to redirect on cancellation.

```java
H2HPreAuthorization h2HPreAuthorization = new H2HPreAuthorization();
h2HPreAuthorization.setAmount("50");
h2HPreAuthorization.setCurrency(Currency.EUR);
h2HPreAuthorization.setCountry(CountryCode.ES);
h2HPreAuthorization.setCardNumber("4907270002222227");
h2HPreAuthorization.setCustomerId("903");
h2HPreAuthorization.setChName("First name Last name");
h2HPreAuthorization.setCvnNumber("123");
h2HPreAuthorization.setExpDate("0625");
h2HPreAuthorization.setPaymentSolution(PaymentSolutions.creditcards);
h2HPreAuthorization.setStatusURL(Creds.statusUrl);
h2HPreAuthorization.setCancelURL(Creds.cancelUrl);
h2HPreAuthorization.setErrorURL(Creds.errorUrl);
h2HPreAuthorization.setSuccessURL(Creds.successUrl);
h2HPreAuthorization.setAwaitingURL(Creds.awaitingUrl);
```

### Step 3: Send Payment Request

Instantiate H2HPaymentAdapter with your credentials and call the sendH2hPreAuthorizationRequest method, passing in the h2HPreAuthorization object and a ResponseListenerAdapter to handle callbacks.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
h2HPaymentAdapter.sendH2hPreAuthorizationRequest(h2HPreAuthorization, new ResponseListenerAdapter() {
    // Implement callback methods...
});
```

### Step 4: Handle the Response

Implement the ResponseListener interface methods to process the response from the AddonPayments API. This includes handling errors, processing notifications, and extracting the redirection URL if needed.

* *onError:* Handle errors encountered during the transaction.
* *onResponseReceived:* Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) { 
    System.out.println("Response Received");
    System.out.println(gson.toJson(notification));
    String redirectionURL = notification.getRedirectUrl();
    System.out.println("Redirection url = " + redirectionURL);
}
```

## Capture Pre-Authorization

Sending Capture Pre-Authorization H2H Request which is used to capture the amount from the normal pre-authorization request.

*Note:* The "Capture Pre-Authorization" is a follow-up request that should be initiated only after successfully completing a "Pre-Authorization Request." This step is crucial as it captures the amount previously authorized during the pre-authorization phase.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Create an instance of H2HPreAuthorizationCapture and set the necessary parameters for the payment transaction. This includes Merchant Id, Payment Solution, Transaction Id, Merchant Transaction Id.

* setPaymentSolution(PaymentSolutions): The payment solution identifier received in payment solution operation in pre-authorization request notification ex: PaymentSolutions.caixapucpuce.
* setMerchantTransactionId(String): The same merchant transaction id of the pre-authorization request.
* setTransactionId(String): Received in the pre-authorization request.

```java
H2HPreAuthorizationCapture h2HPreAuthorizationCapture = new H2HPreAuthorizationCapture();

h2HPreAuthorizationCapture.setPaymentSolution(PaymentSolutions.caixapucpuce);
h2HPreAuthorizationCapture.setTransactionId("7817556");
h2HPreAuthorizationCapture.setMerchantTransactionId("46604547");
```

### Step 3: Send Payment Request

Instantiate H2HPaymentAdapter with your credentials and call the sendH2hPreAuthorizationCapture method, passing in the h2HPreAuthorizationCapture object and a ResponseListenerAdapter to handle callbacks.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
h2HPaymentAdapter.sendH2hPreAuthorizationCapture(h2HPreAuthorizationCapture, new ResponseListenerAdapter() {
    // Implement callback methods...
});
```

### Step 4: Handle the Response

Implement the ResponseListener interface methods to process the response from the AddonPayments API. This includes handling errors, processing notifications, and extracting the redirection URL if needed.

* *onError:* Handle errors encountered during the transaction.
* *onResponseReceived:* Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Final Notification Received");
    System.out.println(gson.toJson(notification));
    System.out.println("Transaction Result = " + transactionResult.name());
}
```

## Recurrent Initial

Through this integration, you can register a subscription plan, allowing the customer to save their card for later payments. In the response, you will receive the "subscriptionPlan", which will be the identifier for subsequent operations and is directly linked to the "cardNumberToken" of the response, and to the "customerId" sent by you in the payment request.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Create an instance of H2HPaymentRecurrentInitial and set the necessary parameters for the payment transaction. This includes card details, transaction amount, currency, and various URLs for handling different outcomes.

* setAmount(String): The transaction amount.
* setCurrency(Currency): The currency code for the transaction.
* setCountry(CountryCode): The customer's country.
* setCustomerId(String): A unique identifier for the customer.
* setCardNumber(String): The customer's card number.
* setChName(String): The cardholder's name.
* setCvnNumber(String): The card verification number.
* setExpDate(String): The card's expiry date in MMYY format.
* setPaymentSolution(PaymentSolutions): The payment solution identifier.
* setStatusURL(String): URL for status updates.
* setSuccessURL(String): URL to redirect on success.
* setErrorURL(String): URL to redirect on error.
* setAwaitingURL(String): URL to redirect when awaiting further action.
* setCancelURL(String): URL to redirect on cancellation.

```java
H2HPaymentRecurrentInitial h2HPaymentRecurrentInitial = new H2HPaymentRecurrentInitial();

h2HPaymentRecurrentInitial.setAmount("50");
h2HPaymentRecurrentInitial.setCurrency(Currency.EUR);
h2HPaymentRecurrentInitial.setCountry(CountryCode.ES);
h2HPaymentRecurrentInitial.setCardNumber("4907270002222227");
h2HPaymentRecurrentInitial.setCustomerId("903");
h2HPaymentRecurrentInitial.setChName("First name Last name");
h2HPaymentRecurrentInitial.setCvnNumber("123");
h2HPaymentRecurrentInitial.setExpDate("0625");
h2HPaymentRecurrentInitial.setPaymentSolution(PaymentSolutions.creditcards);
h2HPaymentRecurrentInitial.setStatusURL(Creds.statusUrl);
h2HPaymentRecurrentInitial.setCancelURL(Creds.cancelUrl);
h2HPaymentRecurrentInitial.setErrorURL(Creds.errorUrl);
h2HPaymentRecurrentInitial.setSuccessURL(Creds.successUrl);
h2HPaymentRecurrentInitial.setAwaitingURL(Creds.awaitingUrl);
```

### Step 3: Send Payment Request

Instantiate H2HPaymentAdapter with your credentials and call the sendH2hPaymentRecurrentInitial method, passing in the h2HPaymentRecurrentInitial object and a ResponseListenerAdapter to handle callbacks.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
h2HPaymentAdapter.sendH2hPaymentRecurrentInitial(h2HPaymentRecurrentInitial, new ResponseListenerAdapter() {
    // Implement callback methods...
});
```

### Step 4: Handle the Response

Implement the ResponseListenerAdapter to process the response from the AddonPayments API. This includes handling errors, processing notifications, and extracting the redirection URL if needed.

* *onError:* Handle errors encountered during the transaction.
* *onResponseReceived:* Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Final Notification Received");
    System.out.println(gson.toJson(notification));
    System.out.println("Transaction Result = " +

 transactionResult.name());
}
```

## Recurrent Subsequent

Payment for subscriptions is a recurring transaction in which the merchant sends the payment request to the customer with the data it has previously stored. For this operation, a series of data is required that is obtained from the client when [registering for the subscription plan](#recurrent-initial).

The payment of subscriptions is an operation initiated by the merchant (MIT), and requires data such as the "cardNumberToken" or the "subscriptionPlan", obtained previously.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Create an instance of H2HPaymentRecurrentSuccessive and set the necessary parameters for the payment transaction. This includes card number token, subscription plan, transaction amount, currency, and various URLs for handling different outcomes.

* setAmount(String): The transaction amount.
* setCurrency(Currency): The currency code for the transaction.
* setCountry(CountryCode): The customer's country.
* setMerchantTransactionId: The merchant transaction id of the original transaction.
* setCustomerId(String): A unique identifier for the customer.
* setPaymentSolution(PaymentSolutions): The payment solution identifier.
* setChName(String): The customer name.
* setCardNumberToken(String): Obtained previously from Recurrent Initial request.
* setSubscriptionPlan(String): Obtained previously from Recurrent Initial request.
* setMerchantExemptionsSca(MerchantExemptionsSca): PSD2 exemption to apply. Normally only sent in successive payments with the value “MIT”.
* setStatusURL(String): URL for status updates.
* setSuccessURL(String): URL to redirect on success.
* setErrorURL(String): URL to redirect on error.
* setAwaitingURL(String): URL to redirect when awaiting further action.
* setCancelURL(String): URL to redirect on cancellation.

```java
H2HPaymentRecurrentSuccessive h2HPaymentRecurrentSuccessive = new H2HPaymentRecurrentSuccessive();

h2HPaymentRecurrentSuccessive.setAmount("50");
h2HPaymentRecurrentSuccessive.setCurrency(Currency.EUR);
h2HPaymentRecurrentSuccessive.setCountry(CountryCode.ES);
h2HPaymentRecurrentSuccessive.setMerchantTransactionId("80004931");
h2HPaymentRecurrentSuccessive.setCustomerId("903");
h2HPaymentRecurrentSuccessive.setChName("First name Last name");
h2HPaymentRecurrentSuccessive.setPaymentSolution(PaymentSolutions.creditcards);
h2HPaymentRecurrentSuccessive.setCardNumberToken("6537275043632227");
h2HPaymentRecurrentSuccessive.setSubscriptionPlan("511845609608301");
h2HPaymentRecurrentSuccessive.setMerchantExemptionsSca(MerchantExemptionsSca.MIT);
h2HPaymentRecurrentSuccessive.setStatusURL(Creds.statusUrl);
h2HPaymentRecurrentSuccessive.setCancelURL(Creds.cancelUrl);
h2HPaymentRecurrentSuccessive.setErrorURL(Creds.errorUrl);
h2HPaymentRecurrentSuccessive.setSuccessURL(Creds.successUrl);
h2HPaymentRecurrentSuccessive.setAwaitingURL(Creds.awaitingUrl);
```

### Step 3: Send Payment Request

Instantiate H2HPaymentAdapter with your credentials and call the sendH2hPaymentRecurrentSuccessive method, passing in the h2HPaymentRecurrentSuccessive object and a ResponseListenerAdapter to handle callbacks.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
h2HPaymentAdapter.sendH2hPaymentRecurrentSuccessive(h2HPaymentRecurrentSuccessive, new ResponseListenerAdapter() {
    // Implement callback methods...
});
```

### Step 4: Handle the Response

Implement the ResponseListenerAdapter to process the response from the AddonPayments API. This includes handling errors, processing notifications, and extracting the redirection URL if needed.

* *onError:* Handle errors encountered during the transaction.
* *onResponseReceived:* Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Final Notification Received");
    System.out.println(gson.toJson(notification));
    System.out.println("Transaction Result = " + transactionResult.name());
}
```

## Refund

Refund an amount from the previous transaction. The transaction should be completed (not in pending status).

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Create an instance of H2HRefund and set the necessary parameters for the payment transaction. This includes Merchant Id, Payment Solution, Transaction Id, Merchant Transaction Id.

* setAmount(String): The amount to be refunded. You can use part of the previous transaction amount or the whole previous amount.
* setPaymentSolution(PaymentSolutions): The payment solution identifier received in the payment solution operation in pre-authorization request notification, e.g., PaymentSolutions.caixapucpuce.
* setTransactionId(String): Received in the pre-authorization request.
* setMerchantTransactionId(String): The merchant transaction id of the original transaction.

```java
H2HRefund h2HRefund = new H2HRefund();

h2HRefund.setAmount("20");
h2HRefund.setPaymentSolution(PaymentSolutions.caixapucpuce);
h2HRefund.setMerchantTransactionId("38559715");
h2HRefund.setTransactionId("7817556");
```

### Step 3: Send Payment Request

Instantiate H2HPaymentAdapter with your credentials and call the sendH2hRefundRequest method, passing in the h2HRefund object and a ResponseListenerAdapter to handle callbacks.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
h2HPaymentAdapter.sendH2hRefundRequest(h2HRefund, new ResponseListenerAdapter() {
    // Implement callback methods...
});
```

### Step 4: Handle the Response

Implement the ResponseListener interface methods to process the response from the AddonPayments API. This includes handling errors, processing notifications, and extracting the redirection URL if needed.

* *onError:* Handle errors encountered during the transaction.
* *onResponseReceived:* Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Final Notification Received");
    System.out.println(gson.toJson(notification));
    System.out.println("Transaction Result = " + transactionResult.name());
}
```

## Void

Sending Capture Pre-Authorization H2H Request which is used to capture the amount from the normal pre-authorization request.

*Note:* The "Void Pre-Authorization" is a follow-up request that should be initiated only after successfully completing a "Pre-Authorization Request." This step is crucial as it captures the amount previously authorized during the pre-authorization phase.

### Step 1: Refer to Common Prerequisite

Before proceeding with the Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Payment Parameters

Create an instance of H2HVoid and set the necessary parameters for the payment transaction. This includes Merchant Id, Payment Solution, Transaction Id, Merchant Transaction Id.

* setPaymentSolution(PaymentSolutions): The payment solution identifier received in the payment solution operation in pre-authorization request notification, e.g., PaymentSolutions.caixapucpuce.
* setMerchantTransactionId(String): The same merchant transaction id of the pre-authorization request.
* setTransactionId(String): Received in the pre-authorization request.

```java
H2HVoid h2HVoid = new H2HVoid();

h2HVoid.setPaymentSolution(PaymentSolutions.caixapucpuce);
h2HVoid.setTransactionId("7817740");
h2HVoid.setMerchantTransactionId("76969499");
```

### Step 3: Send Payment Request

Instantiate H2HPaymentAdapter with your credentials and call the sendH2hVoidRequest method, passing in the h2HVoid object and a ResponseListenerAdapter to handle callbacks.

```java
Gson gson = new GsonBuilder().disableHtmlEscaping().create();

H2HPaymentAdapter h2HPaymentAdapter = new H2HPaymentAdapter(credentials);
h2HPaymentAdapter.sendH2hVoidRequest(h2HVoid, new ResponseListenerAdapter() {
    // Implement callback methods...
});
```

### Step 4: Handle the Response

Implement the ResponseListener interface methods to process the response from the

AddonPayments API. This includes handling errors, processing notifications, and extracting the redirection URL if needed.

* *onError:* Handle errors encountered during the transaction.
* *onResponseReceived:* Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Final Notification Received");
    System.out.println(gson.toJson(notification));
    System.out.println("Transaction Result = " + transactionResult.name());
}
```