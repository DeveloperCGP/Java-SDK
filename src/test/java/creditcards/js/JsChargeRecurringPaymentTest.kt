package creditcards.js

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.ResponseListenerAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.js.JSPaymentRecurrentInitial
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class JsChargeRecurringPaymentTest {

    @Test
    fun successJSRecurringNotification() {
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

        val mockedResponseListener = mockk<ResponseListenerAdapter>();
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

        val jsPaymentRecurrentInitial = JSPaymentRecurrentInitial()

        jsPaymentRecurrentInitial.amount = "30"
        jsPaymentRecurrentInitial.prepayToken = "2795f021-f31c-4533-a74d-5d3d887a003b"
        jsPaymentRecurrentInitial.country = CountryCode.ES
        jsPaymentRecurrentInitial.customerId = "55"
        jsPaymentRecurrentInitial.currency = Currency.EUR
        jsPaymentRecurrentInitial.operationType = OperationTypes.DEBIT
        jsPaymentRecurrentInitial.paymentSolution = PaymentSolutions.creditcards
        jsPaymentRecurrentInitial.statusURL = "https://test.com/paymentNotification"
        jsPaymentRecurrentInitial.successURL = "https://test.com/success"
        jsPaymentRecurrentInitial.errorURL = "https://test.com/error"
        jsPaymentRecurrentInitial.awaitingURL = "https://test.com/awaiting"
        jsPaymentRecurrentInitial.cancelURL = "https://test.com/cancel"
        jsPaymentRecurrentInitial.apiVersion = 5

        val jsPaymentAdapter = JSPaymentAdapter(credentials)
        jsPaymentAdapter.sendJSPaymentRecurrentInitial(jsPaymentRecurrentInitial, mockedResponseListener)

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

        assertEquals(Endpoints.CHARGE_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)
        assertEquals(2, headersSlot.captured.size)
        assertEquals("2795f021-f31c-4533-a74d-5d3d887a003b", headersSlot.captured["prepayToken"])

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.ChargeResponse

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
                NotificationResponses.ChargeResponse,
                rawResponseSlot.captured
        )

        assertNotNull(notificationSlot.captured.redirectUrl)
    }

    @Test
    fun failMissingParameterJSRecurring() {
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

        val mockedResponseListener = mockk<ResponseListenerAdapter>();
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

        val jsPaymentRecurrentInitial = JSPaymentRecurrentInitial()

        jsPaymentRecurrentInitial.amount = "30"
        jsPaymentRecurrentInitial.country = CountryCode.ES
        jsPaymentRecurrentInitial.customerId = "55"
        jsPaymentRecurrentInitial.currency = Currency.EUR
        jsPaymentRecurrentInitial.operationType = OperationTypes.DEBIT
        jsPaymentRecurrentInitial.paymentSolution = PaymentSolutions.creditcards
        jsPaymentRecurrentInitial.statusURL = "https://test.com/paymentNotification"
        jsPaymentRecurrentInitial.successURL = "https://test.com/success"
        jsPaymentRecurrentInitial.errorURL = "https://test.com/error"
        jsPaymentRecurrentInitial.awaitingURL = "https://test.com/awaiting"
        jsPaymentRecurrentInitial.cancelURL = "https://test.com/cancel"
        jsPaymentRecurrentInitial.apiVersion = 5

        val jsPaymentAdapter = JSPaymentAdapter(credentials)
        val exception = assertThrows<MissingFieldException> {
            jsPaymentAdapter.sendJSPaymentRecurrentInitial(jsPaymentRecurrentInitial, mockedResponseListener)
        }

        assertEquals("Missing prepayToken", exception.message)
    }

    @Test
    fun failInvalidAmountJSRecurring() {
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

        val mockedResponseListener = mockk<ResponseListenerAdapter>();
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

        val jsPaymentRecurrentInitial = JSPaymentRecurrentInitial()

        val exception = assertThrows<InvalidFieldException> {
            jsPaymentRecurrentInitial.amount = "30,123.123"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}