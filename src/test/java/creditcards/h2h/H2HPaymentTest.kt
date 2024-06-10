package creditcards.h2h

import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HRedirection
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class H2HPaymentTest {

    @Test
    fun successResponseH2HPayment() {

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

        val h2HRedirection = H2HRedirection()
        h2HRedirection.amount = "50"
        h2HRedirection.currency = Currency.EUR
        h2HRedirection.country = CountryCode.ES
        h2HRedirection.cardNumber = "4907270002222227"
        h2HRedirection.customerId = "903"
        h2HRedirection.chName = "First name Last name"
        h2HRedirection.cvnNumber = "123"
        h2HRedirection.expDate = "0625"
        h2HRedirection.paymentSolution = PaymentSolutions.creditcards
        h2HRedirection.statusURL = "https://test.com/status"
        h2HRedirection.successURL = "https://test.com/success"
        h2HRedirection.errorURL = "https://test.com/error"
        h2HRedirection.awaitingURL = "https://test.com/waiting"
        h2HRedirection.cancelURL = "https://test.com/cancel"
        h2HRedirection.merchantTransactionId = "12345678"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        h2HPaymentAdapter.sendH2hPaymentRequest(h2HRedirection, mockedResponseListener)

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
            "7QDv+7cYdagtQmVfr38p1+HRNOMcBrftirm7FfE6+GOSF52tAfECBNLpz0a9jfI8Vlr7QWy4vIfNXFdl+saLSXIVvsH8bn31IcWKU3OeMVXo7oK9uHWbv+xCWoUVvCigNVKwnNRhH3hs4+pxifzJH0+t0lnb/P9KViOOqGRL/v7BeDN8BHaCF3rMLeUwE0q0os5MjtPjgmIC+jQVmjHlRb2KE+RhLewh/oazUTgOIMo75uj4DHuvlj9qhfRdU/iLhqgZd7P2jeQ0/zMa0Uv+N34NE4dzfeMNQ+leBbsbCiXKH15RubBngQ3MbT3OVd0+qfQE4bKc3ibITXuexUX/y/AufxXCvhX2hRGUxAaQeD0V1pmYkVAdzzIdQz0feLuZyLSY0QlxRRaME6lE1Re+fiWAik1ryPPY1EsBTDIF1/RgeMfH31KkLa1rgyNAv8G3kO4DNxV1ROmELsILCX4jDApkAlME+3Zx6gc/1BeDiRSwY+yssSBXZ6fJniAX39UzqaEsLeywtjdMadlYyjlvIKkh+x70gpiNIqfoYqVMV4Sr6CHzaMSaivIyWTttcYV+BmrvcbmTdRhKlI8QmwLmccQm0i655HFkmlIqm3Fqszbx2NfynZz0mB7MGLWwpmvB7dGl1rkVBTIr9RMc6uQqupsuukYDYvCfrEz0zcnmqMVI5CvBJQx7Vsj3sH6Rqwtj",
            queryParameterSlot.captured["encrypted"]
        )
        assertEquals(
            "cc2453521d7e5ab37ac96f580afbcf72e7f6a42530c3e9e5295cd3fc3122d58f",
            queryParameterSlot.captured["integrityCheck"]
        )

        assertEquals(Endpoints.H2H_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.h2HRedirectionSuccessResponse

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

        assertEquals(NotificationResponses.h2HRedirectionSuccessResponse, rawResponseSlot.captured)
        assertEquals(2, notificationSlot.captured.operations.size)
        assertNull(notificationSlot.captured.operations.last().paymentSolution)
        assertEquals("TRA", notificationSlot.captured.operations.first().service)
        assertEquals("3DSv2", notificationSlot.captured.operations.last().service)
        assertEquals("SUCCESS", notificationSlot.captured.operations.first().status)
        assertEquals("REDIRECTED", notificationSlot.captured.operations.last().status)
    }

    @Test
    fun missingFieldH2HPayment() {
        val mockedResponseListener = mockk<ResponseListener>();
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any() ) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val h2HRedirection = H2HRedirection()
        h2HRedirection.currency = Currency.EUR
        h2HRedirection.country = CountryCode.ES
        h2HRedirection.cardNumber = "4907270002222227"
        h2HRedirection.customerId = "903"
        h2HRedirection.chName = "First name Last name"
        h2HRedirection.cvnNumber = "123"
        h2HRedirection.expDate = "0625"
        h2HRedirection.paymentSolution = PaymentSolutions.creditcards
        h2HRedirection.statusURL = "https://test.com/status"
        h2HRedirection.successURL = "https://test.com/success"
        h2HRedirection.errorURL = "https://test.com/error"
        h2HRedirection.awaitingURL = "https://test.com/waiting"
        h2HRedirection.cancelURL = "https://test.com/cancel"
        h2HRedirection.merchantTransactionId = "12345678"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            h2HPaymentAdapter.sendH2hPaymentRequest(h2HRedirection, mockedResponseListener)
        }

        assertEquals("Missing amount", exception.message)
    }

    @Test
    fun failInvalidAmountH2HPayment() {
        val mockedResponseListener = mockk<ResponseListener>();
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any() ) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val h2HRedirection = H2HRedirection()

        val exception = assertThrows<InvalidFieldException> {
            h2HRedirection.amount = "50,9"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}