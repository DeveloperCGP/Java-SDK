package quix.js

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSQuixPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixAddress
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixBilling
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixAccommodationPaySolExtendedData
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixArticleAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixCartAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixItemCartItemAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_js.JSQuixAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class JsPaymentChargeAccommodationTest {

    @Test
    fun successJsNotification() {
        mockkConstructor(NetworkAdapter::class, recordPrivateCalls = true)
        every {
            anyConstructed<NetworkAdapter>()["sendRequest"](
                any<HashMap<String, String>>(),
                any<HashMap<String, String>>(),
                any<RequestBody>(),
                any<String>(),
                any<RequestListener>()
            )
        } answers { }

        val mockedResponseListener = mockk<ResponseListener>()
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onRedirectionURLReceived(any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs


        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val jsQuixAccommodation = JSQuixAccommodation()
        jsQuixAccommodation.amount = "99.0"
        jsQuixAccommodation.customerId = "55"
        jsQuixAccommodation.prepayToken = "2795f021-f31c-4533-a74d-5d3d887a003b"
        jsQuixAccommodation.statusURL = "https://test.com/paymentNotification"
        jsQuixAccommodation.cancelURL = "https://test.com/cancel"
        jsQuixAccommodation.errorURL = "https://test.com/error"
        jsQuixAccommodation.successURL = "https://test.com/success"
        jsQuixAccommodation.awaitingURL = "https://test.com/awaiting"
        jsQuixAccommodation.customerEmail = "test@mail.com"
        jsQuixAccommodation.customerNationalId = "99999999RR"
        jsQuixAccommodation.dob = "01-12-1999"
        jsQuixAccommodation.firstName = "Name"
        jsQuixAccommodation.lastName = "Last Name"
        jsQuixAccommodation.ipAddress = "0.0.0.0"

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixArticleAccommodation = QuixArticleAccommodation()
        quixArticleAccommodation.name = "Nombre del servicio 2"
        quixArticleAccommodation.reference = "4912345678903"
        quixArticleAccommodation.checkinDate = "2024-10-30T00:00:00+01:00"
        quixArticleAccommodation.checkoutDate = "2024-12-31T23:59:59+01:00"
        quixArticleAccommodation.guests = 1
        quixArticleAccommodation.establishmentName = "Hotel"
        quixArticleAccommodation.address = quixAddress
        quixArticleAccommodation.unitPriceWithTax = 99.0
        quixArticleAccommodation.category = Category.digital

        val quixItemCartItemAccommodation = QuixItemCartItemAccommodation()
        quixItemCartItemAccommodation.article = quixArticleAccommodation
        quixItemCartItemAccommodation.units = 1
        quixItemCartItemAccommodation.isAuto_shipping = true
        quixItemCartItemAccommodation.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemAccommodation> = java.util.ArrayList()
        items.add(quixItemCartItemAccommodation)

        val quixCartAccommodation = QuixCartAccommodation()
        quixCartAccommodation.currency = Currency.EUR
        quixCartAccommodation.items = items
        quixCartAccommodation.total_price_with_tax = 99.0

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixAccommodationPaySolExtendedData = QuixAccommodationPaySolExtendedData()
        quixAccommodationPaySolExtendedData.cart = quixCartAccommodation
        quixAccommodationPaySolExtendedData.billing = quixBilling
        quixAccommodationPaySolExtendedData.product = "instalments"

        jsQuixAccommodation.paySolExtendedData = quixAccommodationPaySolExtendedData

        val jsQuixPaymentAdapter = JSQuixPaymentAdapter(credentials)

        jsQuixPaymentAdapter.sendJSQuixAccommodationRequest(jsQuixAccommodation, mockedResponseListener)

        val headersSlot = slot<Map<String, String>>()
        val requestBodySlot = slot<RequestBody>()
        val urlSlot = slot<String>()
        val requestListenerSlot = slot<RequestListener>()

        verify {
            anyConstructed<NetworkAdapter>()["sendRequest"](
                capture(headersSlot),
                any<HashMap<String, String>>(),
                capture(requestBodySlot),
                capture(urlSlot),
                capture(requestListenerSlot)
            )
        }

        assertEquals(2, headersSlot.captured.size)
        assertEquals("2795f021-f31c-4533-a74d-5d3d887a003b", headersSlot.captured["prepayToken"])
        assertEquals(Endpoints.CHARGE_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.JsQuixItemsResponse

        requestListenerSlot.captured.onResponse(200, mockedResponseBody)

        val rawResponseSlot = slot<String>()
        val notificationSlot = slot<Notification>()
        val transactionResult = slot<TransactionResult>()

        verify {
            mockedResponseListener.onResponseReceived(
                capture(rawResponseSlot),
                capture(notificationSlot),
                capture(transactionResult)
            )
        }

        assertEquals(
            NotificationResponses.JsQuixItemsResponse,
            rawResponseSlot.captured
        )

       assertEquals(1, notificationSlot.captured.operations.size)
       assertEquals("af24252b-e8c9-4fb2-9da2-7a476b2d8cd4", notificationSlot.captured.nemuruCartHash)
       assertEquals("62WBmZM44eDS2gZfVbgvEg5Cydea7IcY", notificationSlot.captured.nemuruAuthToken)
    }

    @Test
    fun failMissingParameterJS() {

        mockkStatic(SecurityUtils::class)
        every { SecurityUtils.generateIV() } returns ByteArray(16) { 1 }
        every { SecurityUtils.hash256(any()) } answers { callOriginal() }
        every { SecurityUtils.base64Encode(any()) } answers { callOriginal() }
        every { SecurityUtils.applyAESPadding(any()) } answers { callOriginal() }
        every { SecurityUtils.cbcEncryption(any(), any(), any(), any()) } answers { callOriginal() }

        mockkConstructor(NetworkAdapter::class, recordPrivateCalls = true)
        every {
            anyConstructed<NetworkAdapter>()["sendRequest"](
                any<HashMap<String, String>>(),
                any<HashMap<String, String>>(),
                any<RequestBody>(),
                any<String>(),
                any<RequestListener>()
            )
        } answers { }

        val mockedResponseListener = mockk<ResponseListener>();
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onRedirectionURLReceived(any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val jsQuixAccommodation = JSQuixAccommodation()
        jsQuixAccommodation.amount = "99.0"
        jsQuixAccommodation.customerId = "55"
        jsQuixAccommodation.prepayToken = "2795f021-f31c-4533-a74d-5d3d887a003b"
        jsQuixAccommodation.statusURL = "https://test.com/paymentNotification"
        jsQuixAccommodation.cancelURL = "https://test.com/cancel"
        jsQuixAccommodation.errorURL = "https://test.com/error"
        jsQuixAccommodation.successURL = "https://test.com/success"
        jsQuixAccommodation.customerEmail = "test@mail.com"
        jsQuixAccommodation.customerNationalId = "99999999RR"
        jsQuixAccommodation.dob = "01-12-1999"
        jsQuixAccommodation.firstName = "Name"
        jsQuixAccommodation.lastName = "Last Name"

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixArticleAccommodation = QuixArticleAccommodation()
        quixArticleAccommodation.name = "Nombre del servicio 2"
        quixArticleAccommodation.reference = "4912345678903"
        quixArticleAccommodation.checkinDate = "2024-10-30T00:00:00+01:00"
        quixArticleAccommodation.checkoutDate = "2024-12-31T23:59:59+01:00"
        quixArticleAccommodation.guests = 1
        quixArticleAccommodation.establishmentName = "Hotel"
        quixArticleAccommodation.address = quixAddress
        quixArticleAccommodation.unitPriceWithTax = 99.0

        val quixItemCartItemAccommodation = QuixItemCartItemAccommodation()
        quixItemCartItemAccommodation.article = quixArticleAccommodation
        quixItemCartItemAccommodation.isAuto_shipping = true
        quixItemCartItemAccommodation.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemAccommodation> = java.util.ArrayList()
        items.add(quixItemCartItemAccommodation)

        val quixCartAccommodation = QuixCartAccommodation()
        quixCartAccommodation.currency = Currency.EUR
        quixCartAccommodation.items = items
        quixCartAccommodation.total_price_with_tax = 99.0


        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixAccommodationPaySolExtendedData = QuixAccommodationPaySolExtendedData()
        quixAccommodationPaySolExtendedData.cart = quixCartAccommodation
        quixAccommodationPaySolExtendedData.billing = quixBilling
        quixAccommodationPaySolExtendedData.product = "instalments"

        jsQuixAccommodation.paySolExtendedData = quixAccommodationPaySolExtendedData

        val jsQuixPaymentAdapter = JSQuixPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            jsQuixPaymentAdapter.sendJSQuixAccommodationRequest(jsQuixAccommodation, mockedResponseListener)
        }

        assertEquals("Missing units", exception.message)
    }

    @Test
    fun failInvalidAmountJS() {

        mockkStatic(SecurityUtils::class)
        every { SecurityUtils.generateIV() } returns ByteArray(16) { 1 }
        every { SecurityUtils.hash256(any()) } answers { callOriginal() }
        every { SecurityUtils.base64Encode(any()) } answers { callOriginal() }
        every { SecurityUtils.applyAESPadding(any()) } answers { callOriginal() }
        every { SecurityUtils.cbcEncryption(any(), any(), any(), any()) } answers { callOriginal() }

        mockkConstructor(NetworkAdapter::class, recordPrivateCalls = true)
        every {
            anyConstructed<NetworkAdapter>()["sendRequest"](
                any<HashMap<String, String>>(),
                any<HashMap<String, String>>(),
                any<RequestBody>(),
                any<String>(),
                any<RequestListener>()
            )
        } answers { }

        val mockedResponseListener = mockk<ResponseListener>();
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onRedirectionURLReceived(any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val jsQuixAccommodation = JSQuixAccommodation()

        val exception = assertThrows<InvalidFieldException> {
            jsQuixAccommodation.amount = "99,0"
        }
        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}