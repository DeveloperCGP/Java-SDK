# Introduction and Quick Start

## Table of Contents
- [Introduction](#introduction)
- [Setup](#setup)
- [Environment Variables](#environment-variables)
- [Usage](#usage)
    - [Step 1: Set Up Credentials](#step-1-set-up-credentials)
    - [Step 2: Configure Payment Parameters](#step-2-configure-payment-parameters)
    - [Step 3: Send Payment Hosted Redirection Request](#step-3-send-payment-hosted-redirection-request)
    - [Step 4: Handle the Response](#step-4-handle-the-response)
- [Handling Payment Notifications from AddonPayments](#handling-payment-notifications-from-addonpayments)
    - [Overview](#overview)
    - [Integration with AddonPayments](#integration-with-addonpayments)
- [Error Handling in the Java SDK](#error-handling-in-the-java-sdk)
    - [Types of Errors](#types-of-errors)
    - [Error Handling Mechanism](#error-handling-mechanism)
- [SDK Logging Documentation](#sdk-logging-documentation)
## Introduction

The AddonPayments Java SDK facilitates the seamless integration of payment processing capabilities within your e-commerce project. This guide outlines the necessary steps to integrate any payment processing type quickly and efficiently. For comprehensive details, refer to the official [AddonPayments documentation site](https://docs.globalpayments.es/).

## Setup

To use this SDK in a Gradle project.

First, add the jar file in the *"libs"* folder inside the project.

Second, import the jar in the gradle by adding:

**Kotlin Gradle**

```kotlin
implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
```

**Groovy Gradle**

```groovy
implementation(fileTree(dir: 'libs', include: ['*.jar']))
```

## Environment Variables

To use the environment variables for the credential add:

* MERCHANT_PASS
* MERCHANT_KEY
* MERCHANT_ID
* ENVIRONMENT
    * STAGING
    * PRODUCTION
* PRODUCT_ID
* PRODUCT_ID_ACCOMMODATION
* PRODUCT_ID_ITEM
* PRODUCT_ID_SERVICE
* PRODUCT_ID_FLIGHT
* STATUS_URL
* SUCCESS_URL
* CANCEL_URL
* AWAITING_URL
* ERROR_URL

The default values for these environment variables are *NULL*.

The values will be added to class *Creds* with variables names:

* merchantPass (String)
* merchantKey (String)
* merchantId (String)
* environment (Environment Enum)
* productId (String)
* productIdAccommodation (String)
* productIdItem (String)
* productIdService (String)
* productIdFlight (String)
* statusUrl (String)
* successUrl (String)
* cancelUrl (String)
* awaitingUrl (String)
* errorUrl (String)

And then to use them:

```java
Creds.merchantPass
Creds.merchantKey
Creds.merchantId
Creds.environment
Creds.productId
Creds.productIdAccommodation
Creds.productIdItem
Creds.productIdService
Creds.productIdFlight
Creds.statusUrl
Creds.successUrl
Creds.cancelUrl
Creds.awaitingUrl
Creds.errorUrl
```

Or the environment variables can be used directly by:

```java
System.getenv().getOrDefault(<Name>, null)
```

## Usage

This section outlines the process of configuring and executing transactions using the Java SDK. The steps are designed to guide you through setting up your credentials, configuring transaction parameters, sending a request, and handling responses, including redirect URLs and notifications.

### Step 1: Set Up Credentials

First, instantiate the Credentials object with your merchant details. This includes your Merchant ID, Merchant Pass which are essential for authenticating requests to the AddonPayments API.

* Merchant ID: Identifier of your business on the Addon Payments platform.
* Merchant Password: The Secret Passphrase used inside AES-256 encryption provided by AddonPayments.
* Merchant Key: The key used in the JS payment.
* Environment: The environment that will be used (STAGING or PRODUCTION).
* Product Id: The product id that will be used in the request.

```java
Credentials credentials = new Credentials();
credentials.setMerchantId(Creds.merchantId);
credentials.setMerchantPass(Creds.merchantPass);
credentials.setEnvironment(Creds.environment);
credentials.setProductId(Creds.productId);
credentials.setMerchantKey(Creds.merchantKey);
```

### Step 2: Configure Payment Parameters

Next, create an instance of HostedPaymentRedirection and set the necessary payment parameters. This includes the currency, payment solution, product ID, amount, and various URLs for handling different outcomes (e.g., success, error, cancel).

* `setAmount(string)`: To set payment amount
* `setCurrency(CurrencyCodes Enum)`: To set the payment currency
* `setCountry(CountryCode Enum)`: To set the country
* `setPaymentSolution(PaymentSolutionsEnum)`: To set the payment solution
* `setCustomerId(string)`: To set the customer id
* `setMerchantTransactionId(string)`: To set the merchant transaction id
    * It needs to be unique for each transaction
    * It will be set to a random value at the initialization of the object, so don't use the setter to leave it random
* `setStatusURL(string)`: Sets the URL to which the payment gateway will send asynchronous notifications about the transaction's status.
* `setSuccessURL(string)`: Specifies the URL to redirect the customer to upon a successful payment.
* `setErrorURL(string)`: Defines the URL to redirect the customer to in case of a payment error.
* `setAwaitingURL(string)`: Sets the URL to redirect the customer to when the payment is in an awaiting state.
* `setCancelURL(string)`: Determines the URL to redirect the customer to if they decide to cancel the transaction at the payment gateway page.

#### Merchant Parameters

*Setters*

* `setMerchantParameter(key, value)`: To add a single additional parameter.
    * Parameters that are sent to modify the configuration of the trade or the processing of a transaction.
    * They are received back inside the label.
    * Does not support special characters.
* `setMerchantParameters(List<Pair<String, String>>)`:
    * To append to the current merchant parameters list.
    * Parameters that are sent to modify the configuration of the trade or the processing of a transaction.
    * They are received back inside the label.
    * Does not support special characters.

Example:

*Setting a list*

```java
List<Pair<String, String>> merchantParams = new ArrayList<>();
merchantParams.add(new Pair<>("name", "pablo"));
        merchantParams.add(new Pair<>("surname", "ferrer"));

        h2HRedirection.setMerchantParameters(merchantParams);
```

*Setting a single value*

```java
hostedPaymentRedirection.setMerchantParameter("name", "pablo");
hostedPaymentRedirection.setMerchantParameter("surname", "ferrer");
```

*Getter*

* `getMerchantParameters`: It returns a list of pairs for the merchant parameters added.

```java
System.out.println("Merchant Parameters:");
h2HRedirection.getMerchantParameters().forEach(parameter -> {
        System.out.println("- key = " + parameter.getFirst() + ", value = " + parameter.getSecond());
        });
```

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
hostedPaymentRedirection.setMerchantParameter("name", "pablo");
hostedPaymentRedirection.setMerchantParameter("surname", "ferrer");
```

### Step 3: Send Payment Hosted Redirection Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of *HostedPaymentAdapter* with your previously configured credentials and then calling the *sendHostedPaymentRequest* method with your *hostedPaymentRedirection* configuration and a new *ResponseListenerAdapter* to handle the callbacks.

```java
HostedPaymentAdapter hostedPaymentAdapter = new HostedPaymentAdapter(credentials);

hostedPaymentAdapter.sendHostedPaymentRequest(hostedPaymentRedirection, new ResponseListenerAdapter() {
  // Callback methods for handling responses will be implemented here...
});
```

### Step 4: Handle the Response

Implement the *ResponseListenerAdapter* Adapter to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* `onError`: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* `onRedirectionURLReceived`: This method receives the URL to which the user should be redirected to complete the payment process. Handling this callback is crucial for redirecting the user to the payment gateway, and it contains a callback that should be invoked when the notification is received in the status.

```java
@Override
public void onError(Error error, String errorMessage) {
  System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onRedirectionURLReceived(String redirectionURL, NotificationListener notificationListener) {
  System.out.println("Redirection Url Received");
  System.out.println("Url = " + redirectionURL);
}
```

## Handling Payment Notifications from AddonPayments

### Overview

This section details the implementation of a notification handling system for receiving and processing payment notifications from AddonPayments. After setting up the endpoint that is used in the status URL, it will receive a string with the notification, and then use *NotificationAdapter.parseNotification(String notificationString)*.

```java
@PostMapping("/paymentNotification")
public ResponseEntity<String> handlePaymentNotification(@RequestBody String requestBody) {
  logger.info("Notification Received");
  Notification notification = NotificationAdapter.parseNotification(notification);
  process(notification);
  return new ResponseEntity<>("Notification Processed Successfully", HttpStatus.OK);
}

public void process(Notification notification) {

  if (!notification.isLastNotification()) {
    logger.info("Intermediate Notification Ignoring...");
    return;
  }

  Notification.Operation finalOperation = notification.getOperations().get(notification.getOperations().size() -

          1);
  String merchantTransactionId = finalOperation.getMerchantTransactionId();

  switch (finalOperation.getStatus()) {
    case "SUCCESS":
      logger.info("Payment Completed Successfully for merchantTrxId: {}", merchantTransactionId);
      break;
    case "ERROR":
      logger.info("Payment Failed for merchantTrxId: {}", merchantTransactionId);
      break;
    default:
      logger.info("Unexpected status {} for merchantTrxId: {}", finalOperation.getStatus(), merchantTransactionId);
  }
}
```

### Integration with AddonPayments

Ensure your system is correctly configured to receive notifications from AddonPayments. This typically involves setting up a webhook URL in the AddonPayments dashboard pointing to the /paymentNotification endpoint of your application.

## Error Handling in the Java SDK

In the process of integrating and utilizing the Java SDK for payment gateway interactions, various types of errors can occur. These errors might originate from different layers of the application, including the SDK itself, the Payment Gateway (PGW), or the underlying network. Understanding and handling these errors appropriately is crucial for a robust and user-friendly application. Here's an overview of common error types and how to handle them in the Java SDK context.

### Types of Errors

1. **SDK Errors**:
* **FieldException**: A parent exception for field-related errors.
    * **MissingFieldException**: Raised when mandatory data is missing, ensuring all necessary information is provided before proceeding with any operations, particularly those critical to the payment process.
    * **InvalidFieldException**: Raised when provided data is outside the expected values or is inappropriate for the given context.

### Error Handling Mechanism

To effectively manage errors in the Java SDK, a try-catch block is used. This approach ensures that the application can gracefully handle exceptions and provide informative feedback to the user or the calling process. Here's an example of how to implement error handling:

```java
try {
        // Your code for SDK configuration and request preparation goes here.

        } catch (InvalidFieldException e) {
        System.out.println("InvalidFieldException: " + e.getMessage());
        e.printStackTrace();
} catch (MissingFieldException e) {
        System.out.println("MissingFieldException: " + e.getMessage());
        e.printStackTrace();
} catch (FieldException e) {
        System.out.println("FieldException: " + e.getMessage());
        e.printStackTrace();
} catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
}
```


## SDK Logging Documentation

### Overview
The SDK provides built-in logging functionality to capture and store detailed logs for both outgoing requests and internal validation errors. The logs are essential for debugging and monitoring the SDK's operations, and they are stored in a dedicated directory above the document root of your web server.

#### Log File Structure
The logs are divided into two main types:

1. **Error Logs**: Captures and logs all errors related to invalid fields in requests.
2. **Request Logs**: Captures detailed information about each outgoing request, including the request payload, headers, response from the server, and any missing fields.

### Directory Structure
The logs are stored within the project’s root directory in a folder named logs.

- **Log Directory Path**:
  ```
  /logs
  ```
  This path ensures that the logs are not directly accessible via the web, adding an extra layer of security.

### Log File Naming Convention
Each log file is named based on the date and time of the log entry, ensuring uniqueness and easy identification.

1. **Error Logs**:
- **Filename**: `YYYYMMDD_HHMMSS_ERROR.log`
- **Example**: `240830_091400_ERROR.log`
- **Contents**:
  ```
  [YYYYMMDD_HHMMSS] ERROR: The field 'statusURL' is invalid.
  Stack Trace:
  #0 com.example.sdk.Config.Parameters.validateField(Parameters.java:218)
  #1 com.example.sdk.Requests.PaymentRequest.process(PaymentRequest.java:42)
  ```

2. **Request Logs**:
- **Filename**: `YYMMDD_HHMMSS__IntType_TxnType_TxnID_Log`
- **Example**: `240830_090611__H2H_creditcards_456456_Log`
- **Contents**:
  ```
  Response:
  <XML response content>
  
  URL:
  <Request URL>
  
  StatusCode:
  <HTTP status code>
  
  Headers:
  <Request headers>
  
  Body:
  <Request body>
  
  Data Sent: 
  <Data sent in the request>
  ```

#### Enabling/Disabling Logs
The logging functionality can be controlled using an environment variable:

- **Environment Variable**: `LOGS_FILE_ENABLED`
- **Type**: Boolean (`true` or `false`)
- **Description**:
    - Set `LOGS_FILE_ENABLED` to `true` to enable logging.
    - Set it to `false` to disable logging.

#### Usage Example
To enable logging, add the following line to your environment configuration:
```bash
export LOGS_FILE_ENABLED=true
```

**Note: If the environment variable is not set it will be enabled by default**

#### Important Notes
- **File Management**: Regularly monitor and manage the log files to avoid excessive disk usage.

This logging mechanism is vital for tracking the behavior and performance of your integration with the SDK.
