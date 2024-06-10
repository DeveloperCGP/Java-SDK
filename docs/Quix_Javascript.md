# Quix Javascript

## Table of Contents

1. [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object)
2. [Item Integration](#item-integration)
    1. [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite)
    2. [Step 2: Configure Quix Hosted Item Request](#step-2-configure-quix-hosted-item-request)
    3. [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters)
    4. [Step 4: Send the Quix JS Charge Item Request](#step-4-send-the-quix-js-charge-item-request)
    5. [Step 5: Handle the Response](#step-5-handle-the-response)
3. [Service Integration](#service-integration)
    1. [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite)
    2. [Step 2: Configure Quix JS Service Request](#step-2-configure-quix-js-service-request)
    3. [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters)
    4. [Step 4: Send the Quix JS Service Request](#step-4-send-the-quix-js-service-request)
    5. [Step 5: Handle the Response](#step-5-handle-the-response)
4. [Flights Integration](#flights-integration)
    1. [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-1)
    2. [Step 2: Configure Quix JS Flights Request](#step-2-configure-quix-js-flights-request)
    3. [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters-1)
    4. [Step 4: Send the Quix Hosted Flight Request](#step-4-send-the-quix-hosted-flight-request)
    5. [Step 5: Handle the Response](#step-5-handle-the-response-1)
5. [Accommodation Integration](#accommodation-integration)
    1. [Step 1: Refer to Common Prerequisite](#step-1-refer-to-common-prerequisite-2)
    2. [Step 2: Configure Quix JS Accommodation Request](#step-2-configure-quix-js-accommodation-request)
    3. [Step 3: Configure Quix Transaction Parameters](#step-3-configure-quix-transaction-parameters-2)
    4. [Step 4: Send the Quix JS Accommodations Request](#step-4-send-the-quix-js-accommodations-request)
    5. [Step 5: Handle the Response](#step-5-handle-the-response-2)

Create and perform the *charge request* after using *authToken* to render the payment form at checkout and customer fills out all customer or payment information and submits the form. Addon Payments collects the payment information and generates a unique *prepayToken* that is linked to the original requested authToken values.

## Common Prerequisite: Creating Credentials Object

Set Up Credentials

First, instantiate the Credentials object with your merchant details. This includes your Merchant ID, Merchant Pass which are essential for authenticating requests to the AddonPayments API.

* setMerchantId(string)
    * It is the indicator of your trade on the AP platform. It is provided by Support in the welcome email, it is common for both environments.
* setMerchantKey(string)
    * It is the JavaScript password. It is used to verify that the request is legitimate. For the staging environment, it is sent in the welcome email, in production environments it is retrieved through the BackOffice.
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

## Item Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix Hosted Item Request

Create an instance of *JSQuixItem* and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setPrepayToken: prepayToken that is linked to the original requested authToken values.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL: URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress: Customer details.

```java
JSQuixItem jsQuixItem = new JSQuixItem();
jsQuixItem.setPrepayToken("4c8eb302-890f-43ff-aa8c-7ee77acec777");
jsQuixItem.setAmount(99);
jsQuixItem.setCustomerId("55");
jsQuixItem.setStatusURL(Creds.statusUrl);
jsQuixItem.setCancelURL(Creds.cancelUrl);
jsQuixItem.setErrorURL(Creds.errorUrl);
jsQuixItem.setSuccessURL(Creds.successUrl);
jsQuixItem.setAwaitingURL(Creds.awaitingUrl);
jsQuixItem.setCustomerEmail("test@mail.com");
jsQuixItem.setDob("01-12-1999");
jsQuixItem.setFirstName("Name");
jsQuixItem.setLastName("Last Name");
jsQuixItem.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.

#### Product and Cart Configuration

1. Define Products: Create *QuixArticleProduct* instances for each product or service, specifying the name, reference, and unit price.

```java
QuixArticleProduct quixArticleProduct = new QuixArticleProduct();
quixArticleProduct.setName("Nombre del servicio 2");
quixArticleProduct.setReference("4912345678903");
quixArticleProduct.setUnitPriceWithTax(99);
quixArticleProduct.setCategory(Category.digital);
```

2. Add Products to Cart Items: Wrap each product in a *QuixItemCartItemProduct*, detailing the quantity, whether auto shipping is applied, and the total price.

```java
QuixItemCartItemProduct quixItemCartItemProduct = new QuixItemCartItemProduct();
quixItemCartItemProduct.setArticle(quixArticleProduct);
quixItemCartItemProduct.setUnits(1);
quixItemCartItemProduct.setAutoShipping(true);
quixItemCartItemProduct.setTotalPriceWithTax(99);
```

3. Assemble the Cart: Create a *QuixCartProduct* and add all cart items, setting the currency and total cart price.

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
quixAddress.setPostalCode("08003");

QuixBilling quixBilling = new QuixBilling();
quixBilling.setAddress(quixAddress);
quixBilling.setFirstName("Nombre");
quixBilling.setLastName("Apellido");
```

5. Configure Payment Solution Extended Data: Use *QuixItemPaySolExtendedData* to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer.

```java
QuixItemPaySolExtendedData quixItemPaySolExtendedData = new QuixItemPaySolExtendedData();
quixItemPaySolExtendedData.setCart(quixCartProduct);
quixItemPaySolExtendedData.setBilling(quixBilling);
quixItemPaySolExtendedData.setProduct("instalments");
quixItemPaySolExtendedData.setDisableFormEdition(true);
```

6. Set Payment Solution Extended Data: Incorporate all previously configured details into the *JSQuixItem* instance. Ensure all necessary transaction information is accurately set.

```java
jsQuixItem.setPaySolExtendedData(quixItemPaySolExtendedData);
```

### Step 4: Send the Quix JS Charge Item Request

Initiate the payment process by sending a JS Charge payment redirection request. This involves creating an instance of *JSQuixPaymentAdapter* with your previously configured credentials and then calling the *sendJSQuixItemRequest* method with your *jsQuixItem* configuration and a new *ResponseListenerAdapter* to handle the callbacks.

```java
JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(jsQuixItem));

jsQuixPaymentAdapter.sendJSQuixItemRequest(jsQuixItem, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the *ResponseListenerAdapter* to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* onResponseReceived: Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Intermediate Notification Received");
    System.out.println(gson.toJson(notification));
}
```

## Service Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix JS Service Request

Create an instance of *JSQuixService* and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setPrepayToken: prepayToken that is linked to the original requested authToken values.
* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL: URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress: Customer details.

```java
JSQuixService jsQuixService = new JSQuixService();
jsQuixService.setAmount(99);
jsQuixService.setPrepayToken("0bcf287f-7687-40c2-ab7a-6d8e86f3d75e");
jsQuixService.setCustomerId("55");
jsQuixService.setStatusURL(Creds.statusUrl);
jsQuixService.setCancelURL(Creds.cancelUrl);
jsQuixService.setErrorURL(Creds.errorUrl);
jsQuixService.setSuccessURL(Creds

.successUrl);
jsQuixService.setAwaitingURL(Creds.awaitingUrl);
jsQuixService.setCustomerCountry(CountryCode.ES);
jsQuixService.setCustomerEmail("test@mail.com");
jsQuixService.setDob("01-12-1999");
jsQuixService.setFirstName("Name");
jsQuixService.setLastName("Last Name");
jsQuixService.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.

#### Product and Cart Configuration

1. Define Services: Create *QuixArticleService* instances for each service, specifying the name, reference, start and end dates, and unit price.

```java
QuixArticleService quixArticleService = new QuixArticleService();
quixArticleService.setName("Nombre del servicio 2");
quixArticleService.setReference("4912345678903");
quixArticleService.setStartDate("2024-10-30T00:00:00+01:00");
quixArticleService.setEndDate("2024-12-31T23:59:59+01:00");
quixArticleService.setUnit_price_with_tax(99);
quixArticleService.setCategory(Category.digital);
```

2. Add Services to Cart Items: Wrap each service in a *QuixItemCartItemService*, detailing the quantity, whether auto-shipping is applied, and the total price.

```java
QuixItemCartItemService quixItemCartItemService = new QuixItemCartItemService();
quixItemCartItemService.setArticle(quixArticleService);
quixItemCartItemService.setUnits(1);
quixItemCartItemService.setAuto_shipping(true);
quixItemCartItemService.setTotal_price_with_tax(99);
```

3. Assemble the Cart for Services: Create a *QuixCartService* and add all service cart items, setting the currency and total cart price.

```java
List<QuixItemCartItemService> items = new ArrayList<>();
items.add(quixItemCartItemService);

QuixCartService quixCartService = new QuixCartService();
quixCartService.setCurrency(Currency.EUR);
quixCartService.setItems(items);
quixCartService.setTotal_price_with_tax(99);
```

4. Set Up Billing Details: Define a *QuixBilling* object with the customer's name and address.

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

5. Configure Payment Solution Extended Data for Services: Use *QuixServicePaySolExtendedData* to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer.

```java
QuixServicePaySolExtendedData quixServicePaySolExtendedData = new QuixServicePaySolExtendedData();
quixServicePaySolExtendedData.setCart(quixCartService);
quixServicePaySolExtendedData.setBilling(quixBilling);
quixServicePaySolExtendedData.setProduct("instalments");
quixServicePaySolExtendedData.setDisableFormEdition(true);
```

6. Set Payment Solution Extended Data: Incorporate all previously configured details into the *JSQuixService* instance. Ensure all necessary transaction information is accurately set.

```java
jsQuixService.setPaySolExtendedData(quixServicePaySolExtendedData);
```

### Step 4: Send the Quix JS Service Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of *JSQuixPaymentAdapter* with your previously configured credentials and then calling the *sendJSQuixServiceRequest* method with your *jsQuixService* configuration and a new *ResponseListenerAdapter* to handle the callbacks.

```java
JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(jsQuixService));

jsQuixPaymentAdapter.sendJSQuixServiceRequest(jsQuixService, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the *ResponseListenerAdapter* to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* onResponseReceived: Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Intermediate Notification Received");
    System.out.println(gson.toJson(notification));
}
```

## Flights Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix JS Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix JS Flights Request

Create an instance of *JSQuixFlight* and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setPrepayToken: prepayToken that is linked to the original requested authToken values.
* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL: URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress: Customer details.

```java
JSQuixFlight jsQuixFlight = new JSQuixFlight();
jsQuixFlight.setAmount(99);
jsQuixFlight.setPrepayToken("daf95d2b-f5a4-41a9-ae99-ad88736e4da3");
jsQuixFlight.setCustomerId("55");
jsQuixFlight.setStatusURL(Creds.statusUrl);
jsQuixFlight.setCancelURL(Creds.cancelUrl);
jsQuixFlight.setErrorURL(Creds.errorUrl);
jsQuixFlight.setSuccessURL(Creds.successUrl);
jsQuixFlight.setAwaitingURL(Creds.awaitingUrl);
jsQuixFlight.setCustomerEmail("test@mail.com");
jsQuixFlight.setDob("01-12-1999");
jsQuixFlight.setFirstName("Name");
jsQuixFlight.setLastName("Last Name");
jsQuixFlight.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.

#### Product and Cart Configuration

1. Define Passengers: Create *QuixPassengerFlight* instances for each passenger, specifying their first and last names.

```java
QuixPassengerFlight quixPassengerFlight = new QuixPassengerFlight();
quixPassengerFlight.setFirstName("Pablo");
quixPassengerFlight.setLastName("Navvaro");

List<QuixPassengerFlight> passengers = new ArrayList<>();
passengers.add(quixPassengerFlight);
```

2. Set Up Flight Segments: Create *QuixSegmentFlight* instances for each flight segment, detailing departure and destination codes.

```java
QuixSegmentFlight quixSegmentFlight = new QuixSegmentFlight();
quixSegmentFlight.setIataDepartureCode("MAD");
quixSegmentFlight.setIataDestinationCode("BCN");

List<QuixSegmentFlight> segments = new ArrayList<>();
segments.add(quixSegmentFlight);
```

3. Define Flight Service: Create a *QuixArticleFlight* instance, populating it with service name, reference, customer member since date, departure date, passengers, flight segments, and unit price.

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

4. Add Flight Service to Cart Items: Wrap the flight service in a *QuixItemCartItemFlight*, indicating the quantity, whether auto-shipping applies (metaphorically, for services like seat selection or priority boarding), and the total price.

```java
QuixItemCartItemFlight quixItemCartItemFlight =

 new QuixItemCartItemFlight();
quixItemCartItemFlight.setArticle(quixArticleFlight);
quixItemCartItemFlight.setUnits(1);
quixItemCartItemFlight.setAuto_shipping(true);
quixItemCartItemFlight.setTotal_price_with_tax(99);

List<QuixItemCartItemFlight> items = new ArrayList<>();
items.add(quixItemCartItemFlight);
```

5. Assemble the Flight Cart: Create a *QuixCartFlight* and add all flight service cart items, setting the currency and the total cart price.

```java
QuixCartFlight quixCartFlight = new QuixCartFlight();
quixCartFlight.setCurrency(Currency.EUR);
quixCartFlight.setItems(items);
quixCartFlight.setTotal_price_with_tax(99);
```

6. Set Up Billing Details: Define a *QuixBilling* object with the customer's name and address.

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

7. Configure Payment Solution Extended Data for Flights: Use *QuixFlightPaySolExtendedData* to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer.

```java
QuixFlightPaySolExtendedData quixFlightPaySolExtendedData = new QuixFlightPaySolExtendedData();
quixFlightPaySolExtendedData.setCart(quixCartFlight);
quixFlightPaySolExtendedData.setBilling(quixBilling);
quixFlightPaySolExtendedData.setProduct("instalments");
quixFlightPaySolExtendedData.setDisableFormEdition(true);
```

8. Set Payment Solution Extended Data: Incorporate all previously configured details into the *JSQuixFlight* instance, ensuring all necessary transaction information is accurately set.

```java
hostedQuixFlight.setPaysolExtendedData(quixFlightPaySolExtendedData);
```

### Step 4: Send the Quix Hosted Flight Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of *JSQuixPaymentAdapter* with your previously configured credentials and then calling the *sendJSQuixFlightRequest* method with your *jsQuixFlight* configuration and a new *ResponseListenerAdapter* to handle the callbacks.

```java
JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(jsQuixFlight));

jsQuixPaymentAdapter.sendJSQuixFlightRequest(jsQuixFlight, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the *ResponseListenerAdapter* to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* onResponseReceived: Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Intermediate Notification Received");
    System.out.println(gson.toJson(notification));
}
```

## Accommodation Integration

### Step 1: Refer to Common Prerequisite

Before proceeding with the Quix Hosted Request, please refer to the [Common Prerequisite: Creating Credentials Object](#common-prerequisite-creating-credentials-object) section at the beginning of this documentation for the initial setup of the SDK credentials. Ensure you have correctly configured your credentials as described there.

### Step 2: Configure Quix JS Accommodation Request

Create an instance of *JSQuixAccommodation* and set the necessary parameters for your transaction. This includes the customer and product details, along with URLs for handling different outcomes.

* setPrepayToken: prepayToken that is linked to the original requested authToken values.
* setAmount: The transaction amount.
* setCustomerId: A unique identifier for the customer.
* setStatusURL, setCancelURL, setErrorURL, setSuccessURL, setAwaitingURL: URLs to manage transaction outcomes.
* setCustomerEmail, setDob, setFirstName, setLastName, setIpAddress: Customer details.

```java
JSQuixAccommodation jsQuixAccommodation = new JSQuixAccommodation();
jsQuixAccommodation.setAmount(99);
jsQuixAccommodation.setCustomerId("55");
jsQuixAccommodation.setPrepayToken("2795f021-f31c-4533-a74d-5d3d887a003b");
jsQuixAccommodation.setStatusURL(Creds.statusUrl);
jsQuixAccommodation.setCancelURL(Creds.cancelUrl);
jsQuixAccommodation.setErrorURL(Creds.errorUrl);
jsQuixAccommodation.setSuccessURL(Creds.successUrl);
jsQuixAccommodation.setAwaitingURL(Creds.awaitingUrl);
jsQuixAccommodation.setCustomerEmail("test@mail.com");
jsQuixAccommodation.setDob("01-12-1999");
jsQuixAccommodation.setFirstName("Name");
jsQuixAccommodation.setLastName("Last Name");
jsQuixAccommodation.setIpAddress("0.0.0.0");
```

### Step 3: Configure Quix Transaction Parameters

To prepare for a Quix transaction, you need to populate the transaction with relevant product details, cart information, billing details, and necessary URLs for handling transaction outcomes.

#### Product and Cart Configuration

1. Define Accommodation Details: First, create a *QuixArticleAccommodation* instance, specifying details such as service name, reference, check-in and check-out dates, number of guests, establishment name, and the accommodation's address.

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

2. Add Accommodation to Cart Items: Wrap the accommodation in a *QuixItemCartItemAccommodation*, detailing the quantity and the total price, acknowledging any auto-shipping features as services or amenities included with the accommodation.

```java
QuixItemCartItemAccommodation quixItemCartItemAccommodation = new QuixItemCartItemAccommodation();
quixItemCartItemAccommodation.setArticle(quixArticleAccommodation);
quixItemCartItemAccommodation.setUnits(1);
quixItemCartItemAccommodation.setAuto_shipping(true); // This could represent included amenities or services.
quixItemCartItemAccommodation.setTotal_price_with_tax(99);

List<QuixItemCartItemAccommodation> items = new ArrayList<>();
items.add(quixItemCartItemAccommodation);
```

3. Assemble the Accommodation Cart: Create a *QuixCartAccommodation* and add all accommodation cart items, setting the currency and the total cart price.

```java
QuixCartAccommodation quixCartAccommodation = new QuixCartAccommodation();
quixCartAccommodation.setCurrency(Currency.EUR);
quixCartAccommodation.setItems(items);
quixCartAccommodation.setTotal_price_with_tax(99);
```

4. Set Up Billing Details: Define a *QuixBilling* object with the customer's name and address, matching the accommodation's address for billing simplicity.

```java
QuixBilling quixBilling = new QuixBilling();
quixBilling.setAddress(quixAddress); // Using the same address object as the accommodation.
quixBilling.setFirst_name("Nombre");
quixBilling.setLast_name("Apellido");
```

5. Configure Payment Solution Extended Data for Accommodations: Use *QuixAccommodationPaySolExtendedData* to include the cart and billing information within the transaction, specifying the type of product or solution being used.

* setDisableFormEdition: Is an optional parameter to disable editing the already sent data in the request for the customer.

```java
QuixAccommodationPaySolExtendedData quixAccommodationPaySolExtendedData = new QuixAccommodationPaySolExtendedData();
quixAccommodationPaySolExtendedData.setCart(quixCartAccommodation);
quixAccommodationPaySolExtendedData.setBilling(quixBilling);
quixAccommodationPaySolExtendedData.setProduct("instalments");
quixAccommodationPaySolExtendedData.setDisableFormEdition(true);
```

6. Set Payment Solution Extended Data: Incorporate all previously configured details into the *JSQuixAccommodation* instance, ensuring all necessary transaction information is accurately set.

```java
hostedQuixAccommodation.setPaySolExtendedData(quixAccommodationPaySolExtendedData

);
```

### Step 4: Send the Quix JS Accommodations Request

Initiate the payment process by sending a hosted payment redirection request. This involves creating an instance of *JSQuixPaymentAdapter* with your previously configured credentials and then calling the *sendJSQuixAccommodationRequest* method with your *jsQuixAccommodation* configuration and a new *ResponseListenerAdapter* to handle the callbacks.

```java
jsQuixAccommodation.setPaySolExtendedData(quixAccommodationPaySolExtendedData);

JSQuixPaymentAdapter jsQuixPaymentAdapter = new JSQuixPaymentAdapter(credentials);

System.out.println(gson.toJson(jsQuixAccommodation));

jsQuixPaymentAdapter.sendJSQuixAccommodationRequest(jsQuixAccommodation, new ResponseListenerAdapter() {
    // Callback methods for handling responses will be implemented here...
});
```

### Step 5: Handle the Response

Implement the *ResponseListenerAdapter* to manage the various outcomes of the payment request. This interface includes several methods, each designed to handle specific types of responses:

* onError: This method is invoked when an error occurs during the payment process. Implement this method to handle errors gracefully, such as logging the error or displaying a message to the user.
* onResponseReceived: Process the response received from a transaction.

```java
@Override
public void onError(Error error, String errorMessage) {
    System.out.println("Error received - " + error.name() + " - " + errorMessage);
}

@Override
public void onResponseReceived(String rawResponse, Notification notification, TransactionResult transactionResult) {
    System.out.println("Intermediate Notification Received");
    System.out.println(gson.toJson(notification));
}
```