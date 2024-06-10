# Quix Hosted

## Table of Contents
- [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object)
- [Item Integration](#item-integration)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite)
    - [Step 2: Configure Quix Hosted Item Request](#step-2-configure-quix-hosted-item-request)
    - [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters)
    - [Step 4: Send the Quix Hosted Item Request](#step-4-send-the-quix-hosted-item-request)
    - [Step 5: Handle the Response](#step-5-handle-the-response)
- [Service Integration](#service-integration)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-1)
    - [Step 2: Configure Quix Hosted Item Request](#step-2-configure-quix-hosted-item-request-1)
    - [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters-1)
    - [Step 4: Send the Quix Hosted Service Request](#step-4-send-the-quix-hosted-service-request)
    - [Step 5: Handle the Response](#step-5-handle-the-response-1)
- [Flights Integration](#flights-integration)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-2)
    - [Step 2: Configure Quix Hosted Item Request](#step-2-configure-quix-hosted-item-request-2)
    - [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters-2)
    - [Step 4: Send the Quix Hosted Flight Request](#step-4-send-the-quix-hosted-flight-request)
    - [Step 5: Handle the Response](#step-5-handle-the-response-2)
- [Accommodation Integration](#accommodation-integration)
    - [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-3)
    - [Step 2: Configure Quix Hosted Item Request](#step-2-configure-quix-hosted-item-request-3)
    - [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters-3)
    - [Step 4: Send the Quix Hosted Accommodations Request](#step-4-send-the-quix-hosted-accommodations-request)
    - [Step 5: Handle the Response](#step-5-handle-the-response-3)

## Common Prerequisite: Creating Credentials Object

Set Up Credentials

First, instantiate the Credentials object with your merchant details. This includes your Merchant ID, Merchant Pass which are essential for authenticating requests to the AddonPayments API.

* setMerchantId(string)
    * Identifier of your business on the Addon Payments platform.
* setMerchantPass(string)
    * merchantPassword is the Secret Passphrase used inside AES-256 encryption provided by AddonPayments.
* setEnvironment(Environment)
    * The environment that will be used.
    * It can be "STAGING" or "PRODUCTION".
* setProductId(String)
    * The product id that will be used in the request.

```java
Credentials credentials = new Credentials();
credentials.setMerchantId(Creds.merchantId);
credentials.setMerchantPass(Creds.merchantPass);
credentials.setEnvironment(Creds.environment);
credentials.setProductId(Creds.productId);
```

## Item Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix Hosted Item Request

Create an instance of HostedQuixItem and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL : URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress : Customer details.

```java
HostedQuixItem hostedQuixItem = new HostedQuixItem();
hostedQuixItem.setAmount(99);
hostedQuixItem.setCustomerId("903");
hostedQuixItem.setStatusURL(Creds.statusUrl);
hostedQuixItem.setCancelURL(Creds.cancelUrl);
hostedQuixItem.setErrorURL(Creds.errorUrl);
hostedQuixItem.setSuccessURL(Creds.successUrl);
hostedQuixItem.setAwaitingURL(Creds.awaitingUrl);
hostedQuixItem.setCustomerEmail("test@mail.com");
hostedQuixItem.setDob("01-12-1999");
hostedQuixItem.setFirstName("Name");
hostedQuixItem.setLastName("Last Name");
hostedQuixItem.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.

#### Product and Cart Configuration

1. Define Products: Create QuixArticleProduct instances for each product or service, specifying the name, reference, and unit price.

```java
QuixArticleProduct quixArticleProduct = new QuixArticleProduct();
quixArticleProduct.setName("Nombre del servicio 2");
quixArticleProduct.setReference("4912345678903");
quixArticleProduct.setUnitPriceWithTax(99);
quixArticleProduct.setCategory(Category.digital);
```

2. Add Products to Cart Items: Wrap each product in a QuixItemCartItemProduct, detailing the quantity, whether auto shipping is applied, and the total price.

```java
QuixItemCartItemProduct quixItemCartItemProduct = new QuixItemCartItemProduct();
quixItemCartItemProduct.setArticle(quixArticleProduct);
quixItemCartItemProduct.setUnits(1);
quixItemCartItemProduct.setAutoShipping(true);
quixItemCartItemProduct.setTotalPriceWithTax(99);
```

3. Assemble the Cart: Create a QuixCartProduct and add all cart items, setting the currency and total cart price.

```java
List<QuixItemCartItemProduct> items = new ArrayList<>();
items.add(quixItemCartItemProduct);

QuixCartProduct quixCartProduct = new QuixCartProduct();
quixCartProduct.setCurrency(Currency.EUR);
quixCartProduct.setItems(items);
quixCartProduct.setTotalPriceWithTax(99);
```

4. Set Up Billing Details: Define a QuixBilling object with the customer's name and address.

```java
QuixAddress quixAddress = new QuixAddress();
quixAddress.setCity("Barcelona");
quixAddress.setCountry(CountryCode.ES);
quixAddress.setStreetAddress("Nombre de la vía y nº");
quixAddress.setPostalCode("28003");

QuixBilling quixBilling = new QuixBilling();
quixBilling.setAddress(quixAddress);
quixBilling.setFirstName("Nombre");
quixBilling.setLastName("Apellido");
```

5. Configure Payment Solution Extended Data: Use QuixItemPaySolExtendedData to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer

```java
QuixItemPaySolExtendedData quixItemPaySolExtendedData = new QuixItemPaySolExtendedData();
quixItemPaySolExtendedData.setCart(quixCartProduct);
quixItemPaySolExtendedData.setBilling(quixBilling);
quixItemPaySolExtendedData.setProduct("instalments");
quixItemPaySolExtendedData.setDisableFormEdition(true);
```

6. Set Payment Solution Extended Data: Incorporate all previously configured details into the HostedQuixItem instance. Ensure all necessary transaction information is accurately set.

```java
hostedQuixItem.setPaySolExtendedData(quixItemPaySolExtendedData);
```

### Step 4: Send the Quix Hosted Item Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of HostedQuixPaymentAdapter with your previously configured credentials and then calling the sendHostedQuixItemRequest method with your hostedQuixItem configuration and a new ResponseListenerAdapter to handle the callbacks.

```java
HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(hostedQuixItem));

hostedQuixPaymentAdapter.sendHostedQuixItemRequest(hostedQuixItem, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the ResponseListenerAdapter to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully

, such as logging the error or displaying a message to the user.
* onRedirectionURLReceived: This method receives the URL to which the user should be redirected to complete the payment process. Handling this callback is crucial for redirecting the user to the payment gateway, and it contains a callback that should be invoked when the notification is received in the status.

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

## Service Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix Hosted Item Request

Create an instance of HostedQuixService and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL: URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress: Customer details.

```java
HostedQuixService hostedQuixService = new HostedQuixService();
hostedQuixService.setAmount(99);
hostedQuixService.setCustomerId("903");
hostedQuixService.setStatusURL(Creds.statusUrl);
hostedQuixService.setCancelURL(Creds.cancelUrl);
hostedQuixService.setErrorURL(Creds.errorUrl);
hostedQuixService.setSuccessURL(Creds.successUrl);
hostedQuixService.setAwaitingURL(Creds.awaitingUrl);
hostedQuixService.setCustomerEmail("test@mail.com");
hostedQuixService.setDob("01-12-1999");
hostedQuixService.setFirstName("Name");
hostedQuixService.setLastName("Last Name");
hostedQuixService.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.

#### Product and Cart Configuration

1. Define Services: Create QuixArticleService instances for each service, specifying the name, reference, start and end dates, and unit price.

```java
QuixArticleService quixArticleService = new QuixArticleService();
quixArticleService.setName("Nombre del servicio 2");
quixArticleService.setReference("4912345678903");
quixArticleService.setStartDate("2024-10-30T00:00:00+01:00");
quixArticleService.setEndDate("2024-12-31T23:59:59+01:00");
quixArticleService.setUnit_price_with_tax(99);
quixArticleService.setCategory(Category.digital);
```

2. Add Services to Cart Items: Wrap each service in a QuixItemCartItemService, detailing the quantity, whether auto-shipping is applied, and the total price.

```java
QuixItemCartItemService quixItemCartItemService = new QuixItemCartItemService();
quixItemCartItemService.setArticle(quixArticleService);
quixItemCartItemService.setUnits(1);
quixItemCartItemService.setAuto_shipping(true);
quixItemCartItemService.setTotal_price_with_tax(99);
```

3. Assemble the Cart for Services: Create a QuixCartService and add all service cart items, setting the currency and total cart price.

```java
List<QuixItemCartItemService> items = new ArrayList<>();
items.add(quixItemCartItemService);

QuixCartService quixCartService = new QuixCartService();
quixCartService.setCurrency(Currency.EUR);
quixCartService.setItems(items);
quixCartService.setTotal_price_with_tax(99);
```

4. Set Up Billing Details: Define a QuixBilling object with the customer's name and address.

```java
QuixAddress quixAddress = new QuixAddress();
quixAddress.setCity("Barcelona");
quixAddress.setCountry(CountryCode.ES);
quixAddress.setStreet_address("Nombre de la vía y nº");
quixAddress.setPostal_code("28003");

QuixBilling quixBilling = new QuixBilling();
quixBilling.setAddress(quixAddress);
quixBilling.setFirst_name("Nombre");
quixBilling.setLast_name("Apellido");
```

5. Configure Payment Solution Extended Data: Use QuixServicePaySolExtendedData to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer.

```java
QuixServicePaySolExtendedData quixServicePaySolExtendedData = new QuixServicePaySolExtendedData();
quixServicePaySolExtendedData.setCart(quixCartService);
quixServicePaySolExtendedData.setBilling(quixBilling);
quixServicePaySolExtendedData.setProduct("instalments");
quixServicePaySolExtendedData.setDisableFormEdition(true);
```

6. Set Payment Solution Extended Data: Incorporate all previously configured details into the HostedQuixService instance. Ensure all necessary transaction information is accurately set.

```java
hostedQuixService.setPaySolExtendedData(quixServicePaySolExtendedData);
```

### Step 4: Send the Quix Hosted Service Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of HostedQuixPaymentAdapter with your previously configured credentials and then calling the sendHostedQuixServiceRequest method with your hostedQuixService configuration and a new ResponseListenerAdapter to handle the callbacks.

```java
HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(hostedQuixService));

hostedQuixPaymentAdapter.sendHostedQuixServiceRequest(hostedQuixService, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the ResponseListenerAdapter to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* onRedirectionURLReceived: This method receives the URL to which the user should be redirected to complete the payment process. Handling this callback is crucial for redirecting the user to the payment gateway, and it contains a callback that should be invoked when the notification is received in the status.

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

## Flights Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix Hosted Item Request

Create an instance of HostedQuixFlight and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL: URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress: Customer details.

```java
HostedQuixFlight hostedQuixFlight = new HostedQuixFlight();
hostedQuixFlight.setAmount(99);
hostedQuixFlight.setCustomerId("903");
hostedQuixFlight.setStatusURL(Creds.statusUrl);
hostedQuixFlight.setCancelURL(Creds.cancelUrl);
hostedQuixFlight.setErrorURL(Creds.errorUrl);
hostedQuixFlight.setSuccessURL(Creds.successUrl);
hostedQuixFlight.setAwaitingURL(Creds.awaitingUrl);
hostedQuixFlight.setCustomerCountry(CountryCode.ES);
hostedQuixFlight.setCustomerEmail("test@mail.com");
hostedQuixFlight.setDob("01-12-1999");
hostedQuixFlight.setFirstName("Name");
hostedQuixFlight.setLastName("Last Name");
hostedQuixFlight.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.



#### Product and Cart Configuration

1. Define Passengers: Create QuixPassengerFlight instances for each passenger, specifying their first and last names.

```java
QuixPassengerFlight quixPassengerFlight = new QuixPassengerFlight();
quixPassengerFlight.setFirstName("Pablo");
quixPassengerFlight.setLastName("Navvaro");

List<QuixPassengerFlight> passengers = new ArrayList<>();
passengers.add(quixPassengerFlight);
```

2. Set Up Flight Segments: Create QuixSegmentFlight instances for each flight segment, detailing departure and destination codes.

```java
QuixSegmentFlight quixSegmentFlight = new QuixSegmentFlight();
quixSegmentFlight.setIataDepartureCode("MAD");
quixSegmentFlight.setIataDestinationCode("BCN");

List<QuixSegmentFlight> segments = new ArrayList<>();
segments.add(quixSegmentFlight);
```

3. Define Flight Service: Create a QuixArticleFlight instance, populating it with service name, reference, customer member since date, departure date, passengers, flight segments, and unit price.

```java
QuixArticleFlight quixArticleFlight = new QuixArticleFlight();
quixArticleFlight.setName("Nombre del servicio 2");
quixArticleFlight.setReference("4912345678903");
quixArticleFlight.setCustomerMemberSince("2023-10-30T00:00:00+01:00");
quixArticleFlight.setDepartureDate("2024-12-31T23:59:59+01:00");
quixArticleFlight.setPassengers(passengers);
quixArticleFlight.setSegments(segments);
quixArticleFlight.setUnit_price_with_tax(99);
quixArticleFlight.setCategory(Category.digital);
```

4. Add Flight Service to Cart Items: Wrap the flight service in a QuixItemCartItemFlight, indicating the quantity, whether auto-shipping applies (metaphorically, for services like seat selection or priority boarding), and the total price.

```java
QuixItemCartItemFlight quixItemCartItemFlight = new QuixItemCartItemFlight();
quixItemCartItemFlight.setArticle(quixArticleFlight);
quixItemCartItemFlight.setUnits(1);
quixItemCartItemFlight.setAuto_shipping(true);
quixItemCartItemFlight.setTotal_price_with_tax(99);

List<QuixItemCartItemFlight> items = new ArrayList<>();
items.add(quixItemCartItemFlight);
```

5. Assemble the Flight Cart: Create a QuixCartFlight and add all flight service cart items, setting the currency and the total cart price.

```java
QuixCartFlight quixCartFlight = new QuixCartFlight();
quixCartFlight.setCurrency(Currency.EUR);
quixCartFlight.setItems(items);
quixCartFlight.setTotal_price_with_tax(99);
```

6. Set Up Billing Details: Define a QuixBilling object with the customer's name and address.

```java
QuixAddress quixAddress = new QuixAddress();
quixAddress.setCity("Barcelona");
quixAddress.setCountry(CountryCode.ES);
quixAddress.setStreet_address("Nombre de la vía y nº");
quixAddress.setPostal_code("28003");

QuixBilling quixBilling = new QuixBilling();
quixBilling.setAddress(quixAddress);
quixBilling.setFirst_name("Nombre");
quixBilling.setLast_name("Apellido");
```

7. Configure Payment Solution Extended Data: Use QuixFlightPaySolExtendedData to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer.

```java
QuixFlightPaySolExtendedData quixFlightPaySolExtendedData = new QuixFlightPaySolExtendedData();
quixFlightPaySolExtendedData.setCart(quixCartFlight);
quixFlightPaySolExtendedData.setBilling(quixBilling);
quixFlightPaySolExtendedData.setProduct("instalments");
quixFlightPaySolExtendedData.setDisableFormEdition(true);
```

8. Set Payment Solution Extended Data: Incorporate all previously configured details into the HostedQuixFlight instance, ensuring all necessary transaction information is accurately set.

```java
hostedQuixFlight.setPaysolExtendedData(quixFlightPaySolExtendedData);
```

### Step 4: Send the Quix Hosted Flight Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of HostedQuixPaymentAdapter with your previously configured credentials and then calling the sendHostedQuixFlightRequest method with your hostedQuixFlight configuration and a new ResponseListenerAdapter to handle the callbacks.

```java
HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(hostedQuixFlight));

hostedQuixPaymentAdapter.sendHostedQuixFlightRequest(hostedQuixFlight, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the ResponseListenerAdapter to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* onRedirectionURLReceived: This method receives the URL to which the user should be redirected to complete the payment process. Handling this callback is crucial for redirecting the user to the payment gateway, and it contains a callback that should be invoked when the notification is received in the status.

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

## Accommodation Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix Hosted Item Request

Create an instance of HostedQuixAccommodation and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL: URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress: Customer details.

```java
HostedQuixAccommodation hostedQuixAccommodation = new HostedQuixAccommodation();
hostedQuixAccommodation.setAmount(99);
hostedQuixAccommodation.setCustomerId("903");
hostedQuixAccommodation.setStatusURL(Creds.statusUrl);
hostedQuixAccommodation.setCancelURL(Creds.cancelUrl);
hostedQuixAccommodation.setErrorURL(Creds.errorUrl);
hostedQuixAccommodation.setSuccessURL(Creds.successUrl);
hostedQuixAccommodation.setAwaitingURL(Creds.awaitingUrl);
hostedQuixAccommodation.setCustomerCountry(CountryCode.ES);
hostedQuixAccommodation.setCustomerEmail("test@mail.com");
hostedQuixAccommodation.setDob("01-12-1999");
hostedQuixAccommodation.setFirstName("Name");
hostedQuixAccommodation.setLastName("Last Name");
hostedQuixAccommodation.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.

#### Product and Cart Configuration

1. Define Accommodation Details: First, create a QuixArticleAccommodation instance, specifying details such as service name, reference, check-in and check-out dates, number of guests, establishment name, and the accommodation's address.

```java
QuixAddress quixAddress = new QuixAddress();
quixAddress.setCity("Barcelona");
quixAddress.setCountry(CountryCode.ES);
quixAddress.setStreet_address("Nombre de la vía y nº");
quixAddress.setPostal_code("28003");

QuixArticleAccommodation quixArticleAccommodation = new QuixArticleAccommodation();
quixArticleAccommodation.setName("Nombre del servicio 2");
quixArticleAccommodation.setReference("4912345678903");
quixArticleAccommodation.setCheckinDate("2024-10-30T00:00:00+01:00");
quixArticleAccommodation.setCheckoutDate("2024-12-31T23:59:59+01:00");
quixArticleAccommodation.setGuests(1);
quixArticleAccommodation.setEstablishmentName("Hotel");
quixArticleAccommodation.setAddress(quixAddress);
quixArticleAccommodation.setUnit_price_with_tax(99);
quixArticleAccommodation.setCategory(Category.digital);
```

2. Add Accommodation to Cart Items: Wrap the accommodation in a QuixItemCartItemAccommodation, detailing the quantity and the total price, acknowledging any auto-shipping features as services or amenities included with the accommodation.

```java
QuixItemCartItemAccommodation quixItemCartItemAccommodation = new QuixItemCartItemAccommodation();
quixItemCartItemAccommodation.setArticle(quixArticleAccommodation);
quixItemCartItemAccommodation.setUnits(1);
quixItemCart

ItemAccommodation.setAuto_shipping(true); // This could represent included amenities or services.
quixItemCartItemAccommodation.setTotal_price_with_tax(99);

List<QuixItemCartItemAccommodation> items = new ArrayList<>();
items.add(quixItemCartItemAccommodation);
```

3. Assemble the Accommodation Cart: Create a QuixCartAccommodation and add all accommodation cart items, setting the currency and the total cart price.

```java
QuixCartAccommodation quixCartAccommodation = new QuixCartAccommodation();
quixCartAccommodation.setCurrency(Currency.EUR);
quixCartAccommodation.setItems(items);
quixCartAccommodation.setTotal_price_with_tax(99);
```

4. Set Up Billing Details: Define a QuixBilling object with the customer's name and address, matching the accommodation's address for billing simplicity.

```java
QuixBilling quixBilling = new QuixBilling();
quixBilling.setAddress(quixAddress); // Using the same address object as the accommodation.
quixBilling.setFirst_name("Nombre");
quixBilling.setLast_name("Apellido");
```

5. Configure Payment Solution Extended Data: Use QuixAccommodationPaySolExtendedData to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer.

```java
QuixAccommodationPaySolExtendedData quixAccommodationPaySolExtendedData = new QuixAccommodationPaySolExtendedData();
quixAccommodationPaySolExtendedData.setCart(quixCartAccommodation);
quixAccommodationPaySolExtendedData.setBilling(quixBilling);
quixAccommodationPaySolExtendedData.setProduct("instalments");
quixAccommodationPaySolExtendedData.setDisableFormEdition(true);
```

6. Set Payment Solution Extended Data: Incorporate all previously configured details into the HostedQuixAccommodation instance, ensuring all necessary transaction information is accurately set.

```java
hostedQuixAccommodation.setPaySolExtendedData(quixAccommodationPaySolExtendedData);
```

### Step 4: Send the Quix Hosted Accommodations Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of HostedQuixPaymentAdapter with your previously configured credentials and then calling the sendHostedQuixAccommodationRequest method with your hostedQuixAccommodation configuration and a new ResponseListenerAdapter to handle the callbacks.

```java
HostedQuixPaymentAdapter hostedQuixPaymentAdapter = new HostedQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(hostedQuixAccommodation));

hostedQuixPaymentAdapter.sendHostedQuixAccommodationRequest(hostedQuixAccommodation, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the ResponseListenerAdapter to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* onRedirectionURLReceived: This method receives the URL to which the user should be redirected to complete the payment process. Handling this callback is crucial for redirecting the user to the payment gateway, and it contains a callback that should be invoked when the notification is received in the status.

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