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
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_flight.*
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixFlight
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HostedPaymentChargeFlightsTest {

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
        credentials.productId = "1112220001"
        credentials.apiVersion = 5

        val hostedQuixFlight = HostedQuixFlight()
        hostedQuixFlight.amount = "99.0"
        hostedQuixFlight.customerId = "903"
        hostedQuixFlight.statusURL = "https://test.com/paymentNotification"
        hostedQuixFlight.cancelURL = "https://test.com/cancel"
        hostedQuixFlight.errorURL = "https://test.com/error"
        hostedQuixFlight.successURL = "https://test.com/success"
        hostedQuixFlight.awaitingURL = "https://test.com/awaiting"
        hostedQuixFlight.customerEmail = "test@mail.com"
        hostedQuixFlight.customerNationalId = "99999999RR"
        hostedQuixFlight.dob = "01-12-1999"
        hostedQuixFlight.firstName = "Name"
        hostedQuixFlight.lastName = "Last Name"
        hostedQuixFlight.merchantTransactionId = "12345678"
        hostedQuixFlight.ipAddress = "0.0.0.0"

        val quixPassengerFlight = QuixPassengerFlight()
        quixPassengerFlight.firstName = "Pablo"
        quixPassengerFlight.lastName = "Navvaro"

        val passangers: MutableList<QuixPassengerFlight> = ArrayList()
        passangers.add(quixPassengerFlight)

        val quixSegmentFlight = QuixSegmentFlight()
        quixSegmentFlight.iataDepartureCode = "MAD"
        quixSegmentFlight.iataDestinationCode = "BCN"

        val segments: MutableList<QuixSegmentFlight> = ArrayList()
        segments.add(quixSegmentFlight)

        val quixArticleFlight = QuixArticleFlight()
        quixArticleFlight.name = "Nombre del servicio 2"
        quixArticleFlight.reference = "4912345678903"
        quixArticleFlight.departureDate = "2024-12-31T23:59:59+01:00"
        quixArticleFlight.passengers = passangers
        quixArticleFlight.segments = segments
        quixArticleFlight.unitPriceWithTax = 99.0
        quixArticleFlight.category = Category.digital

        val quixItemCartItemFlight = QuixItemCartItemFlight()
        quixItemCartItemFlight.article = quixArticleFlight
        quixItemCartItemFlight.units = 1
        quixItemCartItemFlight.isAutoShipping = true
        quixItemCartItemFlight.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemFlight> = ArrayList()
        items.add(quixItemCartItemFlight)

        val quixCartFlight = QuixCartFlight()
        quixCartFlight.currency = Currency.EUR
        quixCartFlight.items = items
        quixCartFlight.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixFlightPaySolExtendedData = QuixFlightPaySolExtendedData()
        quixFlightPaySolExtendedData.cart = quixCartFlight
        quixFlightPaySolExtendedData.billing = quixBilling
        quixFlightPaySolExtendedData.product = "instalments"

        hostedQuixFlight.paySolExtendedData = quixFlightPaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)
        hostedQuixPaymentAdapter.sendHostedQuixFlightRequest(hostedQuixFlight, mockedResponseListener)

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
            "pDH/U+/gbuzXdYp84aiQKsVwdo0OluLSE7iid4fDTDsOp3Iz5PMaVkId+H/okm/59Slik6eoVuhf9S0X7utcyiYp1zqBuvvjPWiO0Nmne1/ZLwf2liuTEo6jRVTCGjokuW3KnOMHbgeoHjg5TaK6fzocze2OWBs55Luc+A4onL6/qm7Lt8dAhWkUjIcWzIE5KXyKPm4Icm16zGh5wmDou/WEtJVEedu7LsO1HfOvJro6s39Ya+e8RAaNEoQZ64f4J8kDU9KYEm6aQZrOEp/+n1wI2Vc7u/6Y/VGO2ye7649smVWFsPhgGe9L8i5wzQRI4xpVpKLKQKe2Opx7fG7FZVgy1RZ8Ye4t3KZ8qEKlpHMTriACrdB4QxcwctvbRQCWgjCqCEDwD+98eTefF1LRqh2++/xptrxrXxBl+oOiyNbIdG8ZllTmYZIDF/dIESFWQUXqxL3vAYEj4CMctMZSmb2721NohunvlobjzKnbl/LB8o2dxsCrONuhDn8AX4I+5+IErn+6ifP0cgce4p1LwQL3twThfnqWZRIz0D9O54QEVSj/MTHwTFumZHiNdHlw3jesezHFj9nZDOxgEB42fIT+gOMk5dK5Mq5Y9Bq/ngjN22s5zzfMIgjScm4jwRFa6IszqGWhbrDZex6rJ98Lmrw/lQF4H6d4Gmpjmi8NR8ws30JMlmjNZaxeWJmdR1oCfrL3toKefI/Vf1Ir+8Se7FhBMM72Aybz0ySfsAYFTksou/vSI3Sv2DxOdvo/NtigZw3VWcTHt/Cb0vrhTAjv8b7JOLXrJ5+LCEkTkUO9QufvsAci+XqkWrxU3QnXUYpakpwi3pl7n9N+I3xjPtsrPbZQtVi+b+LBaza7yxgBKaSenyvP7aF7DBm9GPxfvVcn+jl7NbEbeEUGDd0zdmFGTbbyokYcoXD6bMLXq6mR9c4Bzh5uwrOAVXpXSRtCioeaZ6LsI9oHnMh1C43fOrkyU9p2Tf0Z4fV2WbbXTITUhKO/CSH0IOFT4t7uGnMehdo9OlAzK+wWGrIv1EpiATkkWzqiM+8pkf6p644MQKXmmuTRff1qP6WyhRAjmq4JFfCNw6hgotxutIUHvoe22mq55HW/7/Y2obF/1zeKChmyHJNtjYR6xeCVVqcPxM2olHO/NJafafXXnrywWeRYqJgS1EueyOOT+RfJH6PlZBdSSN2rZf+HScD5jtP45WVPOSDPvHiyFz+jQP0zOrlj1lEzL8kZJa0aLGJ4Ai1mHvKQNMJ8zARlDPfq3k5V6q4VgjxqTYzyk97F27WvP+YrG2DimpcEwQ0o/2stJILdykFWad5e91VvfFWg3LXXnuJ9gVWmAlUL2tBpwukomT/Q/dDPBOkQWaaCdj42HtXSSAqXfIy6t09GLulYMHljUsesaDvHyuiPWdTNnSLskfH4ItjO30zloQgasBX3XDtcOs/jLztpOIiIElUEeU3sNal7LVkrzo25YUhquzEtSWqz51o+MRz5/yqNTssimunDMtKFYPYYX4N8idXjPSWjGKh72+WkRy6iAhOAmjDINcLTO3RtX7JJiQqtdSTBzYu3IDkK2jVxK07pxqO4NM0AIKW0e+/bMYuDKzK8SQl0oxceO2fG7MxswDyeSjjkSi3LskvuUGP7CecNvHMUjtHIgkzfk0MjWdNfUFjfz0eHK+aGn9gsYPFFP0xr2+bAaJ5P3sISFzSF5TetFN9cmIshsPC/46YVmz/6vAQMkjqHVz6/AWdg6ty7qdZ9qIEg7xaru9EMN8qJ/pEIj8QUNVtauzkE/ZEmgdDveRWA95BwT87QdAuUg/meoKekap+pXF3lxaVIuOMFeu9kij+UIpGXmVKjwgZGPLDsfxYjVGoXBj0/O6KZglGkH7Xn6YXl/wYBuyTFgt4pfdEoCKfvsSO58SFvLrmM2sAJ3eZF4IpEyfchgGSnLg==",
            queryParameterSlot.captured["encrypted"]
        )
        assertEquals(
            "0e0efdf805b2bee1944260c461e021ea4500a47ae463223d954a80b9626d856a",
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

        val hostedQuixFlight = HostedQuixFlight()
        hostedQuixFlight.amount = "99.0"
        hostedQuixFlight.customerId = "903"
        hostedQuixFlight.cancelURL = "https://test.com/cancel"
        hostedQuixFlight.errorURL = "https://test.com/error"
        hostedQuixFlight.successURL = "https://test.com/success"
        hostedQuixFlight.customerEmail = "test@mail.com"
        hostedQuixFlight.customerNationalId = "99999999RR"
        hostedQuixFlight.dob = "01-12-1999"
        hostedQuixFlight.firstName = "Name"
        hostedQuixFlight.lastName = "Last Name"
        hostedQuixFlight.merchantTransactionId = "12345678"

        val quixPassengerFlight = QuixPassengerFlight()
        quixPassengerFlight.firstName = "Pablo"
        quixPassengerFlight.lastName = "Navvaro"

        val passangers: MutableList<QuixPassengerFlight> = ArrayList()
        passangers.add(quixPassengerFlight)

        val quixSegmentFlight = QuixSegmentFlight()
        quixSegmentFlight.iataDepartureCode = "MAD"
        quixSegmentFlight.iataDestinationCode = "BCN"

        val segments: MutableList<QuixSegmentFlight> = ArrayList()
        segments.add(quixSegmentFlight)

        val quixArticleFlight = QuixArticleFlight()
        quixArticleFlight.name = "Nombre del servicio 2"
        quixArticleFlight.reference = "4912345678903"
        quixArticleFlight.departureDate = "2024-12-31T23:59:59+01:00"
        quixArticleFlight.passengers = passangers
        quixArticleFlight.segments = segments
        quixArticleFlight.unitPriceWithTax = 99.0
        quixArticleFlight.category = Category.digital

        val quixItemCartItemFlight = QuixItemCartItemFlight()
        quixItemCartItemFlight.article = quixArticleFlight
        quixItemCartItemFlight.units = 1
        quixItemCartItemFlight.isAutoShipping = true
        quixItemCartItemFlight.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemFlight> = ArrayList()
        items.add(quixItemCartItemFlight)

        val quixCartFlight = QuixCartFlight()
        quixCartFlight.currency = Currency.EUR
        quixCartFlight.items = items
        quixCartFlight.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixFlightPaySolExtendedData = QuixFlightPaySolExtendedData()
        quixFlightPaySolExtendedData.cart = quixCartFlight
        quixFlightPaySolExtendedData.billing = quixBilling
        quixFlightPaySolExtendedData.product = "instalments"

        hostedQuixFlight.paySolExtendedData = quixFlightPaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            hostedQuixPaymentAdapter.sendHostedQuixFlightRequest(hostedQuixFlight, mockedResponseListener)
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

        val hostedQuixFlight = HostedQuixFlight()

        val exception = assertThrows<InvalidFieldException> {
            hostedQuixFlight.amount = "99,0"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}