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
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixArticleService
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixCartService
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixItemCartItemService
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixServicePaySolExtendedData
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_js.JSQuixService
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class JsPaymentServicesTest {

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

        val jsQuixService = JSQuixService()
        jsQuixService.amount = "99.0"
        jsQuixService.prepayToken = "2795f021-f31c-4533-a74d-5d3d887a003b"
        jsQuixService.customerId = "55"
        jsQuixService.statusURL = "https://test.com/paymentNotification"
        jsQuixService.cancelURL = "https://test.com/cancel"
        jsQuixService.errorURL = "https://test.com/error"
        jsQuixService.successURL = "https://test.com/success"
        jsQuixService.awaitingURL = "https://test.com/awaiting"
        jsQuixService.customerEmail = "test@mail.com"
        jsQuixService.customerNationalId = "99999999RR"
        jsQuixService.dob = "01-12-1999"
        jsQuixService.firstName = "Name"
        jsQuixService.lastName = "Last Name"
        jsQuixService.ipAddress = "0.0.0.0"

        val quixArticleService = QuixArticleService()
        quixArticleService.name = "Nombre del servicio 2"
        quixArticleService.reference = "4912345678903"
        quixArticleService.endDate = "2024-12-31T23:59:59+01:00"
        quixArticleService.unitPriceWithTax = 99.0
        quixArticleService.category = Category.digital

        val quixItemCartItemService = QuixItemCartItemService()
        quixItemCartItemService.article = quixArticleService
        quixItemCartItemService.units = 1
        quixItemCartItemService.isAutoShipping = true
        quixItemCartItemService.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemService> = java.util.ArrayList()
        items.add(quixItemCartItemService)

        val quixCartService = QuixCartService()
        quixCartService.currency = Currency.EUR
        quixCartService.items = items
        quixCartService.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixServicePaySolExtendedData = QuixServicePaySolExtendedData()
        quixServicePaySolExtendedData.cart = quixCartService
        quixServicePaySolExtendedData.billing = quixBilling
        quixServicePaySolExtendedData.product = "instalments"

        jsQuixService.paySolExtendedData = quixServicePaySolExtendedData

        val jsQuixPaymentAdapter = JSQuixPaymentAdapter(credentials)
        jsQuixPaymentAdapter.sendJSQuixServiceRequest(jsQuixService, mockedResponseListener)

        val headersSlot = slot<Map<String, String>>()
        val requestBodySlot = slot<RequestBody>()
        val urlSlot = slot<String>()
        val requestListenerSlot = slot<RequestListener>()

        verify {
            anyConstructed<NetworkAdapter>()["sendRequest"](
                    capture(headersSlot),
                    any <HashMap<String, String>>(),
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

        val jsQuixService = JSQuixService()
        jsQuixService.amount = "99.0"
        jsQuixService.prepayToken = "2795f021-f31c-4533-a74d-5d3d887a003b"
        jsQuixService.customerId = "55"
        jsQuixService.statusURL = "https://test.com/paymentNotification"
        jsQuixService.cancelURL = "https://test.com/cancel"
        jsQuixService.errorURL = "https://test.com/error"
        jsQuixService.successURL = "https://test.com/success"
        jsQuixService.customerEmail = "test@mail.com"
        jsQuixService.customerNationalId = "99999999RR"
        jsQuixService.dob = "01-12-1999"
        jsQuixService.firstName = "Name"
        jsQuixService.lastName = "Last Name"

        val quixArticleService = QuixArticleService()
        quixArticleService.name = "Nombre del servicio 2"
        quixArticleService.reference = "4912345678903"
        quixArticleService.endDate = "2024-12-31T23:59:59+01:00"
        quixArticleService.unitPriceWithTax = 99.0

        val quixItemCartItemService = QuixItemCartItemService()
        quixItemCartItemService.article = quixArticleService
        quixItemCartItemService.isAutoShipping = true
        quixItemCartItemService.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemService> = java.util.ArrayList()
        items.add(quixItemCartItemService)

        val quixCartService = QuixCartService()
        quixCartService.currency = Currency.EUR
        quixCartService.items = items
        quixCartService.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixServicePaySolExtendedData = QuixServicePaySolExtendedData()
        quixServicePaySolExtendedData.cart = quixCartService
        quixServicePaySolExtendedData.billing = quixBilling
        quixServicePaySolExtendedData.product = "instalments"

        jsQuixService.paySolExtendedData = quixServicePaySolExtendedData

        val jsQuixPaymentAdapter = JSQuixPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            jsQuixPaymentAdapter.sendJSQuixServiceRequest(jsQuixService, mockedResponseListener)
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

        val jsQuixService = JSQuixService()

        val exception = assertThrows<InvalidFieldException> {
            jsQuixService.amount = "test"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}