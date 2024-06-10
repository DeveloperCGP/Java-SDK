package creditcards.h2h

import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HPaymentRecurrentInitial
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class RecurringTest {

    @Test
    fun successResponseRecurringPayment() {

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
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val h2HPaymentRecurrentInitial = H2HPaymentRecurrentInitial()

        h2HPaymentRecurrentInitial.amount = "50"
        h2HPaymentRecurrentInitial.currency = Currency.EUR
        h2HPaymentRecurrentInitial.country = CountryCode.ES
        h2HPaymentRecurrentInitial.cardNumber = "4907270002222227"
        h2HPaymentRecurrentInitial.customerId = "903"
        h2HPaymentRecurrentInitial.chName = "First name Last name"
        h2HPaymentRecurrentInitial.cvnNumber = "123"
        h2HPaymentRecurrentInitial.expDate = "0625"
        h2HPaymentRecurrentInitial.paymentSolution = PaymentSolutions.creditcards
        h2HPaymentRecurrentInitial.statusURL = "https://test.com/status"
        h2HPaymentRecurrentInitial.successURL = "https://test.com/success"
        h2HPaymentRecurrentInitial.errorURL = "https://test.com/error"
        h2HPaymentRecurrentInitial.awaitingURL = "https://test.com/waiting"
        h2HPaymentRecurrentInitial.cancelURL = "https://test.com/cancel"
        h2HPaymentRecurrentInitial.merchantTransactionId = "12345678"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        h2HPaymentAdapter.sendH2hPaymentRecurrentInitial(h2HPaymentRecurrentInitial, mockedResponseListener)

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
            "sHvhUSPhOaPaZKqBZkTwkvgW1Xnk+zBqu6BbOJoITrMGSqz6qegKVVZ1bYBZq05QonMNF3nJQH31vX0qE4r+3q1CwUR14JPcI2Owv61pC3cUEOWRqeb2Lg/yP9iN0S8o+V96GksX6KrvAvI0o57cMNEl7Ki2ZNHurHkGjIdLiNF5OVZMistZj75yhZvD0rKoZsQqakM8OBI/bA2F77/6IpgDVpt3LKQ7xKAmdW68dvPoOJxnuuG/iP2Br2QE7maQbvFRHCMD9Acdae95GwsfQcz3u/XbTn/UjSivTkDqWfqT7FdBCEkk3W1CkguGF65cguEMOShI4476dOECyEg/ZW+ev4c4TvozuD87pWXwju832j4zrHYHArukoYjSMKI3Wqs3VBmDY5G4wfBELhljcCuFgYaT0whGh0RKrApE354ZudF/JLQ3FUc2KiKUaJpTnLSQcfdDDo9muzoSEtVC/LQWauLwaBAlPl9ltm6b8qRFQ+fKpli9Gc47x5yiTvDWNrojcfqLUknroRsezUZrUzDY49f22F0YmXur52xArxUn053MwO47hgx3xbRfvySr7Kd1jWPJFOnh0XXQDixNAgx9kh6lLmpuL7tjih+l9mFMwXUcbXB7AG8ZF4Ee29CmeuDrcoEISWdMqyohc06Z5SNJHK/EeSN0VWRiZAp9Yl5vEZr0gtERQEX/QvAcywEXvU54hp3KrxrudvuTLaJ6XjErvmzGTPI+718zOIW4uh69iKQ91AfIorbEQJ+uVQOC",
            queryParameterSlot.captured["encrypted"]
        )
        Assertions.assertEquals(
            "31ed6347c17c644809c074f21016b67426085e862cbfe47b78e03b3ad6c4cd21",
            queryParameterSlot.captured["integrityCheck"]
        )

        Assertions.assertEquals(Endpoints.H2H_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.h2HRecurringSuccessResponse

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

        Assertions.assertEquals(
            NotificationResponses.h2HRecurringSuccessResponse,
            rawResponseSlot.captured
        )
        Assertions.assertEquals(2, notificationSlot.captured.operations.size)
        Assertions.assertNull(notificationSlot.captured.operations.last().paymentSolution)
        Assertions.assertEquals("TRA", notificationSlot.captured.operations.first().service)
        Assertions.assertEquals("3DSv2", notificationSlot.captured.operations.last().service)
        Assertions.assertEquals("SUCCESS", notificationSlot.captured.operations.first().status)
        Assertions.assertEquals("REDIRECTED", notificationSlot.captured.operations.last().status)
    }

    @Test
    fun missingFieldRecurringPayment() {
        val mockedResponseListener = mockk<ResponseListener>()
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val h2HPaymentRecurrentInitial = H2HPaymentRecurrentInitial()

        h2HPaymentRecurrentInitial.amount = "50"
        h2HPaymentRecurrentInitial.currency = Currency.EUR
        h2HPaymentRecurrentInitial.country = CountryCode.ES
        h2HPaymentRecurrentInitial.customerId = "903"
        h2HPaymentRecurrentInitial.chName = "First name Last name"
        h2HPaymentRecurrentInitial.cvnNumber = "123"
        h2HPaymentRecurrentInitial.expDate = "0625"
        h2HPaymentRecurrentInitial.paymentSolution = PaymentSolutions.creditcards
        h2HPaymentRecurrentInitial.statusURL = "https://test.com/status"
        h2HPaymentRecurrentInitial.successURL = "https://test.com/success"
        h2HPaymentRecurrentInitial.errorURL = "https://test.com/error"
        h2HPaymentRecurrentInitial.awaitingURL = "https://test.com/waiting"
        h2HPaymentRecurrentInitial.cancelURL = "https://test.com/cancel"
        h2HPaymentRecurrentInitial.merchantTransactionId = "12345678"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            h2HPaymentAdapter.sendH2hPaymentRecurrentInitial(h2HPaymentRecurrentInitial, mockedResponseListener)
        }

        Assertions.assertEquals("Missing cardNumber", exception.message)
    }

    @Test
    fun failInvalidAmountRecurringPayment() {
        val mockedResponseListener = mockk<ResponseListener>()
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val h2HPaymentRecurrentInitial = H2HPaymentRecurrentInitial()

        val exception = assertThrows<InvalidFieldException> {
            h2HPaymentRecurrentInitial.amount = "-7895"
        }

        Assertions.assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }

}