package creditcards.js

import com.comerciaglobalpayments.javaPaymentSDK.adapters.JSPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.JSPaymentListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.js.JSAuthorizationRequest
import com.comerciaglobalpayments.javaPaymentSDK.models.responses.JSAuthorizationResponse
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.NotificationResponses

class JsAuthChargeItemsPaymentTest {

    @Test
    fun successHostedNotification() {
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

        val mockedResponseListener = mockk<JSPaymentListener>()
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onAuthorizationResponseReceived(any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val jsAuthorizationRequest = JSAuthorizationRequest()

        jsAuthorizationRequest.country = CountryCode.ES
        jsAuthorizationRequest.customerId = "55"
        jsAuthorizationRequest.currency = Currency.EUR
        jsAuthorizationRequest.operationType = OperationTypes.DEBIT

        val jsPaymentAdapter = JSPaymentAdapter(credentials)
        jsPaymentAdapter.sendJSAuthorizationRequest(jsAuthorizationRequest, mockedResponseListener)

        val requestBodySlot = slot<RequestBody>()
        val urlSlot = slot<String>()
        val requestListenerSlot = slot<RequestListener>()

        verify {
            anyConstructed<NetworkAdapter>()["sendRequest"](
                    any <HashMap<String, String>>(),
                    any <HashMap<String, String>>(),
                    capture(requestBodySlot),
                    capture(urlSlot),
                    capture(requestListenerSlot)
            )
        }

        assertEquals(Endpoints.AUTH_ENDPOINT.getEndpoint(Environment.STAGING), urlSlot.captured)

        val mockedResponseBody = mockk<ResponseBody>()
        every { mockedResponseBody.string() } returns NotificationResponses.AuthResponse

        requestListenerSlot.captured.onResponse(200, mockedResponseBody)

        val rawResponseSlot = slot<String>()
        val jsAuthorizationResponseCapturingSlot = slot<JSAuthorizationResponse>()

        verify {
            mockedResponseListener.onAuthorizationResponseReceived(
                    capture(rawResponseSlot),
                    capture(jsAuthorizationResponseCapturingSlot),

            )
        }

        assertEquals(
                NotificationResponses.AuthResponse,
                rawResponseSlot.captured
        )

        assertEquals("c5f2746c-d4ec-44fa-b7a7-61df2378296d", jsAuthorizationResponseCapturingSlot.captured.authToken)
    }

    @Test
    fun failMissingParameterJS() {
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

        val mockedResponseListener = mockk<JSPaymentListener>()
        every { mockedResponseListener.onError(any(), any()) } just Runs
        every { mockedResponseListener.onAuthorizationResponseReceived(any(), any()) } just Runs

        val credentials = Credentials()
        credentials.merchantPass = "11111111112222222222333333333344"
        credentials.merchantKey = "11111111-1111-1111-1111-111111111111"
        credentials.merchantId = "111222"
        credentials.environment = Environment.STAGING
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val jsAuthorizationRequest = JSAuthorizationRequest()
        jsAuthorizationRequest.country = CountryCode.ES
        jsAuthorizationRequest.currency = Currency.EUR
        jsAuthorizationRequest.operationType = OperationTypes.DEBIT

        val jsPaymentAdapter = JSPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            jsPaymentAdapter.sendJSAuthorizationRequest(jsAuthorizationRequest, mockedResponseListener)
        }

        assertEquals("Missing customerId", exception.message)
    }
}