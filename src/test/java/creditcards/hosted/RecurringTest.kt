package creditcards.hosted

import com.comerciaglobalpayments.javaPaymentSDK.adapters.HostedPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.hosted.HostedPaymentRecurrentInitial
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RecurringTest {

    @Test
    fun successRecurringNotification() {

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

        val hostedPaymentRecurrentInitial = HostedPaymentRecurrentInitial()
        hostedPaymentRecurrentInitial.currency = Currency.EUR
        hostedPaymentRecurrentInitial.paymentSolution = PaymentSolutions.creditcards
        hostedPaymentRecurrentInitial.amount = "50"
        hostedPaymentRecurrentInitial.country = CountryCode.ES
        hostedPaymentRecurrentInitial.customerId = "903"
        hostedPaymentRecurrentInitial.statusURL = "https://test.com/status"
        hostedPaymentRecurrentInitial.successURL = "https://test.com/success"
        hostedPaymentRecurrentInitial.errorURL = "https://test.com/error"
        hostedPaymentRecurrentInitial.awaitingURL = "https://test.com/waiting"
        hostedPaymentRecurrentInitial.cancelURL = "https://test.com/cancel"
        hostedPaymentRecurrentInitial.merchantTransactionId = "12345678"

        val hostedPaymentAdapter = HostedPaymentAdapter(credentials)

        hostedPaymentAdapter.sendHostedRecurrentInitial(hostedPaymentRecurrentInitial, mockedResponseListener)

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

        Assertions.assertEquals(3, headersSlot.captured.size)
        Assertions.assertEquals("5", headersSlot.captured["apiVersion"])
        Assertions.assertEquals("CBC", headersSlot.captured["encryptionMode"])
        Assertions.assertEquals("AQEBAQEBAQEBAQEBAQEBAQ==", headersSlot.captured["iv"])

        Assertions.assertEquals(3, queryParameterSlot.captured.size)
        Assertions.assertEquals("111222", queryParameterSlot.captured["merchantId"])
        Assertions.assertEquals(
            "sHvhUSPhOaPaZKqBZkTwkvgW1Xnk+zBqu6BbOJoITrNNjKiwDMAxtIouC6iPn8aW/9mtL9j+VsaekqXS4ZRqN5x20ioUQ4QrhO/BWSAm58FbB80V2z8ejX+js5fmKxwXXUM/aCwmVrj0a2sBJuSq1cSDXLXC2DFW9C0x+ndJ5iAcfLZaKQ9Kn/6nMLu4cr6bFNyxx0iLoG7iKvJ13WBv+ItHzp9GQFCQ5OZsRIZjQ7uONqK8w3Sojlgwpwq/L9qJ1KsgIT0FgoNnzLMSV8FtldkObVA8N1DNxL6gulFDImQp6wKZJC8eF+DPfNOOzBpm4fMNZXh1HeKSE6VzZTOgZ6b8VvS8n+pFHdaNEV8O54aE6gsUPw6CEOHGspaOcODeUHs9/LaffUzd/rdcsRBxlOB/qM+sdkOdCWHWEtfnhmwOM0wYPR710VakiMG5od6r7E5fbHy5b0kyIsYrzJtTj+Xcx7ecdFggHY+IV2evGJ9/xYPOoXHDe+6BpYrsua+TGzZhKPDATZDKOV3E33T71kN5i/FyJKWPoPv2KHAv4l4zn1W2usjD53VUV7x2kibUdJjnr5blq+YJgY5C2o+LeS7NsbMgi+LNrAtzpQXyWzjHM26EGYMpf+0Vjprfhvu4pu+ouMW6Q4IdhlsCKlzo94obJHoM2Fv/NIe2/P0b2v8=",
            queryParameterSlot.captured["encrypted"]
        )
        Assertions.assertEquals(
            "0054eb75fb4ae23f300a54afa001e18d3f8f441854c0f707d51ecd159797864c",
            queryParameterSlot.captured["integrityCheck"]
        )

        Assertions.assertEquals(Endpoints.HOSTED_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

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

        Assertions.assertEquals("http://test.com", redirectionUrlSlot.captured)
    }

    @Test
    fun failMissingParameterRecurring() {

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

        val hostedPaymentRecurrentInitial = HostedPaymentRecurrentInitial()
        hostedPaymentRecurrentInitial.currency = Currency.EUR
        hostedPaymentRecurrentInitial.paymentSolution = PaymentSolutions.creditcards
        hostedPaymentRecurrentInitial.amount = "50"
        hostedPaymentRecurrentInitial.country = CountryCode.ES
        hostedPaymentRecurrentInitial.statusURL = "https://test.com/status"
        hostedPaymentRecurrentInitial.successURL = "https://test.com/success"
        hostedPaymentRecurrentInitial.errorURL = "https://test.com/error"
        hostedPaymentRecurrentInitial.awaitingURL = "https://test.com/waiting"
        hostedPaymentRecurrentInitial.cancelURL = "https://test.com/cancel"
        hostedPaymentRecurrentInitial.merchantTransactionId = "12345678"

        val hostedPaymentAdapter = HostedPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            hostedPaymentAdapter.sendHostedRecurrentInitial(hostedPaymentRecurrentInitial, mockedResponseListener)
        }

        Assertions.assertEquals("Missing customerId", exception.message)
    }

    @Test
    fun failInvalidAmountRecurring() {

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

        val hostedPaymentRecurrentInitial = HostedPaymentRecurrentInitial()
        val exception = assertThrows<InvalidFieldException> {
            hostedPaymentRecurrentInitial.amount = "50-123"
        }

        Assertions.assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}