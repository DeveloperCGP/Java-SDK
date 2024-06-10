package quix.hosted

import com.comerciaglobalpayments.javaPaymentSDK.adapters.HostedQuixPaymentAdapter
import com.comerciaglobalpayments.javaPaymentSDK.adapters.NetworkAdapter
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.RequestListener
import com.comerciaglobalpayments.javaPaymentSDK.callbacks.ResponseListener
import com.comerciaglobalpayments.javaPaymentSDK.enums.*
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.InvalidFieldException
import com.comerciaglobalpayments.javaPaymentSDK.exceptions.MissingFieldException
import com.comerciaglobalpayments.javaPaymentSDK.models.Credentials
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixAddress
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.QuixBilling
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixAccommodationPaySolExtendedData
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixArticleAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixCartAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_accommodation.QuixItemCartItemAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixAccommodation
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HostedPaymentChargeAccommodationTest {

    @Test
    fun successHostedResponse() {

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
        credentials.productId = "1112220003"
        credentials.apiVersion = 5

        val hostedQuixAccommodation = HostedQuixAccommodation()
        hostedQuixAccommodation.amount = "99.0"
        hostedQuixAccommodation.customerId = "903"
        hostedQuixAccommodation.statusURL = "https://test.com/paymentNotification"
        hostedQuixAccommodation.cancelURL = "https://test.com/cancel"
        hostedQuixAccommodation.errorURL = "https://test.com/error"
        hostedQuixAccommodation.successURL = "https://test.com/success"
        hostedQuixAccommodation.awaitingURL = "https://test.com/awaiting"
        hostedQuixAccommodation.customerEmail = "test@mail.com"
        hostedQuixAccommodation.customerNationalId = "99999999RR"
        hostedQuixAccommodation.dob = "01-12-1999"
        hostedQuixAccommodation.firstName = "Name"
        hostedQuixAccommodation.lastName = "Last Name"
        hostedQuixAccommodation.merchantTransactionId = "12345678"
        hostedQuixAccommodation.ipAddress = "0.0.0.0"

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixArticleAccommodation = QuixArticleAccommodation()
        quixArticleAccommodation.name = "Nombre del servicio 2"
        quixArticleAccommodation.reference = "4912345678903"
        quixArticleAccommodation.checkinDate = "2024-10-30T00:00:00+01:00"
        quixArticleAccommodation.checkoutDate = "2024-12-31T23:59:59+01:00"
        quixArticleAccommodation.guests = 1
        quixArticleAccommodation.establishmentName = "Hotel"
        quixArticleAccommodation.address = quixAddress
        quixArticleAccommodation.unitPriceWithTax = 99.0
        quixArticleAccommodation.category = Category.digital

        val quixItemCartItemAccommodation = QuixItemCartItemAccommodation()
        quixItemCartItemAccommodation.article = quixArticleAccommodation
        quixItemCartItemAccommodation.units = 1
        quixItemCartItemAccommodation.isAuto_shipping = true
        quixItemCartItemAccommodation.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemAccommodation> = java.util.ArrayList()
        items.add(quixItemCartItemAccommodation)

        val quixCartAccommodation = QuixCartAccommodation()
        quixCartAccommodation.currency = Currency.EUR
        quixCartAccommodation.items = items
        quixCartAccommodation.total_price_with_tax = 99.0

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixAccommodationPaySolExtendedData = QuixAccommodationPaySolExtendedData()
        quixAccommodationPaySolExtendedData.cart = quixCartAccommodation
        quixAccommodationPaySolExtendedData.billing = quixBilling
        quixAccommodationPaySolExtendedData.product = "instalments"

        hostedQuixAccommodation.paySolExtendedData = quixAccommodationPaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)
        hostedQuixPaymentAdapter.sendHostedQuixAccommodationRequest(hostedQuixAccommodation, mockedResponseListener)

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
            "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",
            queryParameterSlot.captured["encrypted"]
        )
        assertEquals(
            "d8d54a3ebd4477fc2f2b259f0937149b6195bb6c74bfe4720450097794a491b5",
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

        val hostedQuixAccommodation = HostedQuixAccommodation()
        hostedQuixAccommodation.amount = "99.0"
        hostedQuixAccommodation.customerId = "903"
        hostedQuixAccommodation.cancelURL = "https://test.com/cancel"
        hostedQuixAccommodation.errorURL = "https://test.com/error"
        hostedQuixAccommodation.awaitingURL = "https://test.com/awaiting"
        hostedQuixAccommodation.successURL = "https://test.com/success"
        hostedQuixAccommodation.customerEmail = "test@mail.com"
        hostedQuixAccommodation.dob = "01-12-1999"
        hostedQuixAccommodation.firstName = "Name"
        hostedQuixAccommodation.lastName = "Last Name"
        hostedQuixAccommodation.merchantTransactionId = "12345678"
        hostedQuixAccommodation.customerNationalId = "99999999RR"

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixArticleAccommodation = QuixArticleAccommodation()
        quixArticleAccommodation.name = "Nombre del servicio 2"
        quixArticleAccommodation.reference = "4912345678903"
        quixArticleAccommodation.checkinDate = "2024-10-30T00:00:00+01:00"
        quixArticleAccommodation.checkoutDate = "2024-12-31T23:59:59+01:00"
        quixArticleAccommodation.guests = 1
        quixArticleAccommodation.establishmentName = "Hotel"
        quixArticleAccommodation.address = quixAddress
        quixArticleAccommodation.unitPriceWithTax = 99.0

        val quixItemCartItemAccommodation = QuixItemCartItemAccommodation()
        quixItemCartItemAccommodation.article = quixArticleAccommodation
        quixItemCartItemAccommodation.units = 1
        quixItemCartItemAccommodation.isAuto_shipping = true
        quixItemCartItemAccommodation.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemAccommodation> = java.util.ArrayList()
        items.add(quixItemCartItemAccommodation)

        val quixCartAccommodation = QuixCartAccommodation()
        quixCartAccommodation.currency = Currency.EUR
        quixCartAccommodation.items = items
        quixCartAccommodation.total_price_with_tax = 99.0

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixAccommodationPaySolExtendedData = QuixAccommodationPaySolExtendedData()
        quixAccommodationPaySolExtendedData.cart = quixCartAccommodation
        quixAccommodationPaySolExtendedData.billing = quixBilling
        quixAccommodationPaySolExtendedData.product = "instalments"

        hostedQuixAccommodation.paySolExtendedData = quixAccommodationPaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            hostedQuixPaymentAdapter.sendHostedQuixAccommodationRequest(hostedQuixAccommodation, mockedResponseListener)
        }

        assertEquals("Missing category", exception.message)
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
        credentials.productId = "1112220003"
        credentials.apiVersion = 5

        val hostedQuixAccommodation = HostedQuixAccommodation()
        val exception = assertThrows<InvalidFieldException> {
            hostedQuixAccommodation.amount = "99,00"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}