package creditcards.h2h

import com.comerciaglobalpayments.javaPaymentSDK.adapters.H2HPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.h2h.H2HPaymentRecurrentSuccessive
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.notification.Notification
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class RecurringSubsequentTest {

    @Test
    fun successResponseRecurringSubsequentPayment() {

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

        val h2HPaymentRecurrentSuccessive = H2HPaymentRecurrentSuccessive()

        h2HPaymentRecurrentSuccessive.amount = "50"
        h2HPaymentRecurrentSuccessive.currency = Currency.EUR
        h2HPaymentRecurrentSuccessive.country = CountryCode.ES
        h2HPaymentRecurrentSuccessive.customerId = "903"
        h2HPaymentRecurrentSuccessive.chName = "First name Last name"
        h2HPaymentRecurrentSuccessive.paymentSolution = PaymentSolutions.creditcards
        h2HPaymentRecurrentSuccessive.cardNumberToken = "6537275043632227"
        h2HPaymentRecurrentSuccessive.subscriptionPlan = "511845609608301"
        h2HPaymentRecurrentSuccessive.statusURL = "https://test.com/status"
        h2HPaymentRecurrentSuccessive.successURL = "https://test.com/success"
        h2HPaymentRecurrentSuccessive.errorURL = "https://test.com/error"
        h2HPaymentRecurrentSuccessive.awaitingURL = "https://test.com/waiting"
        h2HPaymentRecurrentSuccessive.cancelURL = "https://test.com/cancel"
        h2HPaymentRecurrentSuccessive.merchantTransactionId = "12345678"
        h2HPaymentRecurrentSuccessive.merchantExemptionsSca = MerchantExemptionsSca.MIT

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        h2HPaymentAdapter.sendH2hPaymentRecurrentSuccessive(h2HPaymentRecurrentSuccessive, mockedResponseListener)

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
            "rlmg0gHehv5L1tkfHwzsAlSXHgkBb/iTwNUZVL3CQNT5oCDlxzm/ngnxudS+dNKzApOzVqhhH6cQxIhNINKB1LXtcawr5W7stR07WOhpy3NUZztRQS/dYSuuQ7sc8yIEG8P8AeQhUfglcJN/CIA8QYpF5RLzNPUt/zFdZFFwz2GpUq0iC/gJCWQOvLvIMlALGpz1XJrubJcf82hr/aXy7ROwchS98aYXGrYeP1WgdNSAa/J+E8UiQGHK6lc6T0wqepRGWZJCliNi92BVzRdya0WNf/l1RZMj0eRwlPl2x1FAs2kmg8kH3T/7mWCdlirzvZp1uz4ITvPH014Hii3Dg46SdQfGV9RSCOdfpV2CK6wYe5G1fiOgb2HLdEHyc45msmBWTLiZCAjQfUaCdgUvJNDy1YvSL7uOeWbHcyvWFA13BfU+pdUvOOt1OQ5eOGezxG1zdNu+Fk0lSVLOPm3fO7p2V9ZdszWchcsxWmqLuBRVJYcS5MzBVG3NXdBCMgiLfb8C2BQo473UufdAV6ywoOic4ofqBDR8weKtNe5kGWTsdGC2QtFgo1PkJbBuyHm4Sph14qKlGW0LIMtLNb5K1F4SBTXhVC7RO3xBO/29sil2rPWEKZpLYNPpR6/Lq/RtvMocS9ApaX4Jv510cHKvt2EQUzkNj6VPqsPmMEZWj1hAZa3h73lvhSv62eyzOaKvAoyfjoBqByIfG+ZxfM351MOmaS/UAzN5Rt1jY8RdfOKIxaCH4o54oJYFp9j7mCsNGueDEpudtPLUluZE1m6akw==",
            queryParameterSlot.captured["encrypted"]
        )
        Assertions.assertEquals(
            "ef8736dbe88889fc3ca8cfed29f313d35802b5117777554236983e03e539c4dd",
            queryParameterSlot.captured["integrityCheck"]
        )

        Assertions.assertEquals(Endpoints.H2H_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.h2HRecurringSubsequentResponse

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
            NotificationResponses.h2HRecurringSubsequentResponse,
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
    fun missingFieldRecurringSubsequentPayment() {
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

        val h2HPaymentRecurrentSuccessive = H2HPaymentRecurrentSuccessive()

        h2HPaymentRecurrentSuccessive.amount = "50"
        h2HPaymentRecurrentSuccessive.currency = Currency.EUR
        h2HPaymentRecurrentSuccessive.country = CountryCode.ES
        h2HPaymentRecurrentSuccessive.customerId = "903"
        h2HPaymentRecurrentSuccessive.chName = "First name Last name"
        h2HPaymentRecurrentSuccessive.paymentSolution = PaymentSolutions.creditcards
        h2HPaymentRecurrentSuccessive.subscriptionPlan = "511845609608301"
        h2HPaymentRecurrentSuccessive.statusURL = "https://test.com/status"
        h2HPaymentRecurrentSuccessive.successURL = "https://test.com/success"
        h2HPaymentRecurrentSuccessive.errorURL = "https://test.com/error"
        h2HPaymentRecurrentSuccessive.awaitingURL = "https://test.com/waiting"
        h2HPaymentRecurrentSuccessive.cancelURL = "https://test.com/cancel"
        h2HPaymentRecurrentSuccessive.merchantTransactionId = "12345678"
        h2HPaymentRecurrentSuccessive.merchantExemptionsSca = MerchantExemptionsSca.MIT

        val h2HPaymentAdapter = H2HPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            h2HPaymentAdapter.sendH2hPaymentRecurrentSuccessive(h2HPaymentRecurrentSuccessive, mockedResponseListener)
        }

        Assertions.assertEquals("Missing cardNumberToken", exception.message)
    }

    @Test
    fun failInvalidAmountRecurringSubsequentPayment() {
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

        val h2HPaymentRecurrentSuccessive = H2HPaymentRecurrentSuccessive()

        val exception = assertThrows<InvalidFieldException> {
            h2HPaymentRecurrentSuccessive.amount = "50-12"
        }

        Assertions.assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }

}