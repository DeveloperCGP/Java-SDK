package creditcards.hosted

import com.comerciaglobalpayments.javaPaymentSDK.adapters.HostedPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.hosted.HostedPaymentRedirection
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HostedPaymentTest {

    @Test
    fun successHostedNotification() {

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

        val hostedPaymentRedirection = HostedPaymentRedirection()
        hostedPaymentRedirection.currency = Currency.EUR
        hostedPaymentRedirection.paymentSolution = PaymentSolutions.creditcards
        hostedPaymentRedirection.amount = "50"
        hostedPaymentRedirection.country = CountryCode.ES
        hostedPaymentRedirection.customerId = "903"
        hostedPaymentRedirection.statusURL = "https://test.com/status"
        hostedPaymentRedirection.successURL = "https://test.com/success"
        hostedPaymentRedirection.errorURL = "https://test.com/error"
        hostedPaymentRedirection.awaitingURL = "https://test.com/waiting"
        hostedPaymentRedirection.cancelURL = "https://test.com/cancel"
        hostedPaymentRedirection.merchantTransactionId = "12345678"

        val hostedPaymentAdapter = HostedPaymentAdapter(credentials)

        hostedPaymentAdapter.sendHostedPaymentRequest(hostedPaymentRedirection, mockedResponseListener)

        val headersSlot = slot<Map<String, String>>()
        val queryParameterSlot = slot<Map<String, String>>()
        val requestBodySlot = slot<RequestBody>()
        val urlSlot = slot<String>()
        val requestListenerSlot = slot<RequestListener>()

        verify {
            anyConstructed<NetworkAdapter>()["sendRequest"](
                capture(headersSlot),
                capture(queryParameterSlot),
                capture(requestBodySlot),
                capture(urlSlot),
                capture(requestListenerSlot)
            )
        }

        assertEquals(3, headersSlot.captured.size)
        assertEquals("5", headersSlot.captured["apiVersion"])
        assertEquals("CBC", headersSlot.captured["encryptionMode"])
        assertEquals("AQEBAQEBAQEBAQEBAQEBAQ==", headersSlot.captured["iv"])

        assertEquals(3, queryParameterSlot.captured.size)
        assertEquals("111222", queryParameterSlot.captured["merchantId"])
        assertEquals(
            "pDH/U+/gbuzXdYp84aiQKsVwdo0OluLSE7iid4fDTDsOp3Iz5PMaVkId+H/okm/5ojSCpu0HQXSboqv05ahL79AXdjrMykX0SUjI+vhZfBjsraQ90DXmSNG+HQtnnHdIT2X1I0V2hVbqwE8PL5h3g9BnBDvrrIYEhKB0pQjVA9QKKwREUpQtDgHtssQNKpMTGx7q4tdBYN74ymKr5fur6wCjWp7L1F1VMrythUzPBbS2ySWuEXrG8UaYxEgS4yWe1/pOkzfQdeyHenzy5VZrqM2VdF5rZLZ0puR04lg7ggcVXq+S2k/A84nyozkk2OjxRpyaiig1Ox+KorFDjikAjyrc9jt8cToUTSWu8lBTOMAeYS5/J+fYKyJ4AF3SnbhC3lkvg1U9Xa0V3NIyugnK9eQpqvabh1xtM0UO7MY01WtcWv+kiiyZNg9wPctTN7QQz5SRotw+Cm7nYGkgRwgDT5RWuYfbM5q9I2BQSiENUqRP3r4bbK1FQtBr540KPnUlijjpVIGf+rl05plFoDvIrwxER1sFdJQqwHScEnLZAwFM568IJOMK3fkY9DUzEk72IL1Ei6kNMR0svmuf4dtfaHhcfTimqZHlbp9euhs7TdzQDvdsyYeZcW4j5yirI6C9",
            queryParameterSlot.captured["encrypted"]
        )
        assertEquals(
            "23f2d114145cef5fcb4ae89b0122a67e23041f804a0cfe9fb4ab01e48233137a",
            queryParameterSlot.captured["integrityCheck"]
        )

        assertEquals(Endpoints.HOSTED_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns "http://test.com"

        requestListenerSlot.captured.onResponse(200, mockedResponseBody)

        val redirectionUrlSlot = slot<String>()

        verify {
            mockedResponseListener.onRedirectionURLReceived(
                capture(redirectionUrlSlot)
            )
        }

        verify(exactly = 0) {
            mockedResponseListener.onResponseReceived(any(), any(), any())
            mockedResponseListener.onError(any(), any())
        }

        assertEquals("http://test.com", redirectionUrlSlot.captured)
    }

    @Test
    fun failMissingParameterHosted() {

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

        val hostedPaymentRedirection = HostedPaymentRedirection()
        hostedPaymentRedirection.currency = Currency.EUR
        hostedPaymentRedirection.paymentSolution = PaymentSolutions.creditcards
        hostedPaymentRedirection.amount = "50"
        hostedPaymentRedirection.country = CountryCode.ES
        hostedPaymentRedirection.customerId = "903"
        hostedPaymentRedirection.successURL = "https://test.com/success"
        hostedPaymentRedirection.errorURL = "https://test.com/error"
        hostedPaymentRedirection.awaitingURL = "https://test.com/waiting"
        hostedPaymentRedirection.cancelURL = "https://test.com/cancel"
        hostedPaymentRedirection.merchantTransactionId = "12345678"

        val hostedPaymentAdapter = HostedPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            hostedPaymentAdapter.sendHostedPaymentRequest(hostedPaymentRedirection, mockedResponseListener)
        }

        assertEquals("Missing statusURL", exception.message)
    }

    @Test
    fun failInvalidAmountHosted() {

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

        val hostedPaymentRedirection = HostedPaymentRedirection()

        val exception = assertThrows<InvalidFieldException> {
            hostedPaymentRedirection.amount = "50,11"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}