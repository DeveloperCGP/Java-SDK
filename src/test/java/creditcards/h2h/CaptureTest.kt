package creditcards.h2h

import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HPreAuthorizationCapture
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class CaptureTest {

    @Test
    fun successNotificationCapture() {

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
        credentials.apiVersion = 5

        val h2HPreAuthorizationCapture = H2HPreAuthorizationCapture()

        h2HPreAuthorizationCapture.paymentSolution = PaymentSolutions.creditcards
        h2HPreAuthorizationCapture.merchantTransactionId = "46604547"
        h2HPreAuthorizationCapture.transactionId = "7817556"
        h2HPreAuthorizationCapture.merchantTransactionId = "12345678"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        h2HPaymentAdapter.sendH2hPreAuthorizationCapture(h2HPreAuthorizationCapture, mockedResponseListener)

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
            "pDH/U+/gbuzXdYp84aiQKmAB1WoG3rPuYdEo0WU+k+I6Po6qoGum82RamnYAjsafVDBx2jk7xKlzE5alJVodXAOqiuCV3paKIRxM8ao64TsaSgiWG/8QQ8K2YYlrg3TPZf2xPhKoX78sjjnYUCDFcA==",
            queryParameterSlot.captured["encrypted"]
        )
        Assertions.assertEquals(
            "bb7b2df7cde5ab2f5988dd02de01206139494b36fb20e7794e65efb559408161",
            queryParameterSlot.captured["integrityCheck"]
        )

        Assertions.assertEquals(Endpoints.CAPTURE_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.CaptureResponse

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
            NotificationResponses.CaptureResponse,
            rawResponseSlot.captured
        )
        Assertions.assertEquals(1, notificationSlot.captured.operations.size)
        Assertions.assertEquals(PaymentSolutions.caixapucpuce, notificationSlot.captured.operations.last().paymentSolution)
        Assertions.assertEquals("SUCCESS", notificationSlot.captured.operations.last().status)
    }

    @Test
    fun missingFieldCapture() {
        val mockedResponseListener = mockk<ResponseListener>()
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.apiVersion = 5

        val h2HPreAuthorizationCapture = H2HPreAuthorizationCapture()

        h2HPreAuthorizationCapture.paymentSolution = PaymentSolutions.creditcards
        h2HPreAuthorizationCapture.merchantTransactionId = "46604547"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            h2HPaymentAdapter.sendH2hPreAuthorizationCapture(h2HPreAuthorizationCapture, mockedResponseListener)
        }

        Assertions.assertEquals("Missing transactionId", exception.message)
    }

    @Test
    fun missingApiVersionFieldCapture() {
        val mockedResponseListener = mockk<ResponseListener>()
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onResponseReceived(any(), any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING

        val h2HPreAuthorizationCapture = H2HPreAuthorizationCapture()

        h2HPreAuthorizationCapture.paymentSolution = PaymentSolutions.creditcards
        h2HPreAuthorizationCapture.merchantTransactionId = "46604547"
        h2HPreAuthorizationCapture.merchantTransactionId = "12345678"
        h2HPreAuthorizationCapture.transactionId = "1234567"

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            h2HPaymentAdapter.sendH2hPreAuthorizationCapture(h2HPreAuthorizationCapture, mockedResponseListener)
        }

        Assertions.assertEquals("Mandatory credentials are missing. Please ensure you provide: apiVersion", exception.message)
    }

}