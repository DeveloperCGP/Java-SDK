package creditcards.h2h

import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HRefund
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class RefundTest {

    @Test
    fun successResponseRefund() {

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

        val h2HRefund = H2HRefund()

        h2HRefund.amount = "20"
        h2HRefund.paymentSolution = PaymentSolutions.creditcards
        h2HRefund.transactionId = "7817556"
        h2HRefund.merchantTransactionId = "12345678"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        h2HPaymentAdapter.sendH2hRefundRequest(h2HRefund, mockedResponseListener)

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
            "1S+NMvlkfH5v6C4OsX3gVra1H0R/N8aAKgYbWk2It8QT6pI7D7XKvHfuSXsziXxx0/ZjHhe4vDZdwZ+xezj8qb6Ilq1TYzFAAyiWzNTilkHtkxawFjfEfTrzqUCGKDzxzHDtnM+KUKb+hHZBvKxstTA4sI1Wh5TD0uNUxmXc4gk=",
            queryParameterSlot.captured["encrypted"]
        )
        Assertions.assertEquals(
            "4a812af8fa99e0ee2024a56157c0dbc751bba3a4c37aee1db1ccdf565c8e5a79",
            queryParameterSlot.captured["integrityCheck"]
        )

        Assertions.assertEquals(Endpoints.REFUND_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.RefundResponse

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
            NotificationResponses.RefundResponse,
            rawResponseSlot.captured
        )
        Assertions.assertEquals(1, notificationSlot.captured.operations.size)
        Assertions.assertEquals(
            PaymentSolutions.caixapucpuce,
            notificationSlot.captured.operations.last().paymentSolution
        )
        Assertions.assertEquals("SUCCESS", notificationSlot.captured.operations.last().status)
    }

    @Test
    fun missingFieldRefund() {
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

        val h2HRefund = H2HRefund()

        h2HRefund.amount = "20"
        h2HRefund.paymentSolution = PaymentSolutions.creditcards

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            h2HPaymentAdapter.sendH2hRefundRequest(h2HRefund, mockedResponseListener)
        }

        Assertions.assertEquals("Missing transactionId", exception.message)
    }

    @Test
    fun failInvalidAmountRefund() {
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

        val h2HRefund = H2HRefund()

        val exception = assertThrows<InvalidFieldException> {
            h2HRefund.amount = "20,2"
        }

        Assertions.assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }

}