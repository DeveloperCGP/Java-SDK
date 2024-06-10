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
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixArticleService
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixCartService
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixItemCartItemService
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_service.QuixServicePaySolExtendedData
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixService
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HostedPaymentChargeServiceTest {

    @Test
    fun successHostedServiceNotification() {

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

        val hostedQuixService = HostedQuixService()
        hostedQuixService.amount = "99.0"
        hostedQuixService.customerId = "903"
        hostedQuixService.statusURL = "https://test.com/paymentNotification"
        hostedQuixService.cancelURL = "https://test.com/cancel"
        hostedQuixService.errorURL = "https://test.com/error"
        hostedQuixService.successURL = "https://test.com/success"
        hostedQuixService.awaitingURL = "https://test.com/awaiting"
        hostedQuixService.customerEmail = "test@mail.com"
        hostedQuixService.customerNationalId = "99999999RR"
        hostedQuixService.dob = "01-12-1999"
        hostedQuixService.firstName = "Name"
        hostedQuixService.lastName = "Last Name"
        hostedQuixService.merchantTransactionId = "12345678"
        hostedQuixService.ipAddress = "0.0.0.0"

        val quixArticleService = QuixArticleService()
        quixArticleService.name = "Nombre del servicio 2"
        quixArticleService.reference = "4912345678903"
        quixArticleService.endDate = "2024-12-31T23:59:59+01:00"
        quixArticleService.unitPriceWithTax = 99.0
        quixArticleService.category = Category.digital

        val quixItemCartItemService = QuixItemCartItemService()
        quixItemCartItemService.article = quixArticleService
        quixItemCartItemService.units = 1
        quixItemCartItemService.isAutoShipping = true
        quixItemCartItemService.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemService> = java.util.ArrayList()
        items.add(quixItemCartItemService)

        val quixCartService = QuixCartService()
        quixCartService.currency = Currency.EUR
        quixCartService.items = items
        quixCartService.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixServicePaySolExtendedData = QuixServicePaySolExtendedData()
        quixServicePaySolExtendedData.cart = quixCartService
        quixServicePaySolExtendedData.billing = quixBilling
        quixServicePaySolExtendedData.product = "instalments"

        hostedQuixService.paySolExtendedData = quixServicePaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)
        hostedQuixPaymentAdapter.sendHostedQuixServiceRequest(hostedQuixService, mockedResponseListener)

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
            "pDH/U+/gbuzXdYp84aiQKsVwdo0OluLSE7iid4fDTDsOp3Iz5PMaVkId+H/okm/59Slik6eoVuhf9S0X7utcyiYp1zqBuvvjPWiO0Nmne1/ZLwf2liuTEo6jRVTCGjokuW3KnOMHbgeoHjg5TaK6fzocze2OWBs55Luc+A4onL6/qm7Lt8dAhWkUjIcWzIE5KXyKPm4Icm16zGh5wmDou/WEtJVEedu7LsO1HfOvJro6s39Ya+e8RAaNEoQZ64f4J8kDU9KYEm6aQZrOEp/+n1wI2Vc7u/6Y/VGO2ye7649smVWFsPhgGe9L8i5wzQRI4xpVpKLKQKe2Opx7fG7FZVgy1RZ8Ye4t3KZ8qEKlpHMTriACrdB4QxcwctvbRQCWgjCqCEDwD+98eTefF1LRqh2++/xptrxrXxBl+oOiyNbIdG8ZllTmYZIDF/dIESFWQUXqxL3vAYEj4CMctMZSmb2721NohunvlobjzKnbl/LB8o2dxsCrONuhDn8AX4I+5+IErn+6ifP0cgce4p1LwQL3twThfnqWZRIz0D9O54QEVSj/MTHwTFumZHiNdHlw3jesezHFj9nZDOxgEB42fIT+gOMk5dK5Mq5Y9Bq/ngjN22s5zzfMIgjScm4jwRFa6IszqGWhbrDZex6rJ98Lmrw/lQF4H6d4Gmpjmi8NR8ws30JMlmjNZaxeWJmdR1oCfrL3toKefI/Vf1Ir+8Se7FhBMM72Aybz0ySfsAYFTksou/vSI3Sv2DxOdvo/NtigZw3VWcTHt/Cb0vrhTAjv8b7JOLXrJ5+LCEkTkUO9QufvsAci+XqkWrxU3QnXUYpakpwi3pl7n9N+I3xjPtsrPbZQtVi+b+LBaza7yxgBKaSenyvP7aF7DBm9GPxfvVcn+jl7NbEbeEUGDd0zdmFGTbbyokYcoXD6bMLXq6mR9c4Bzh5uwrOAVXpXSRtCioeaZ6LsI9oHnMh1C43fOrkyU9p2Tf0Z4fV2WbbXTITUhKO/CSH0IOFT4t7uGnMehdo9OlAzK+wWGrIv1EpiATkkWzqiM+8pkf6p644MQKXmmuTRff1qP6WyhRAjmq4JFfCNw6hgotxutIUHvoe22mq55HW/7/Y2obF/1zeKChmyHJNtjYR6xeCVVqcPxM2olHO/rxh7qS9GJUvLUeRKBzstzJOk7EUdj44Qmgr44zYYESwRr8XnOc7SGF165nE0LwuBx8rH5up4jaKtUUT68cVwyNnrhhh2sJlCi8TOvL2b9MTl2K0zi6MVethB1/3aBu9dr+WTAo7GsBCyWgLEV3KCXtOFrBQRj3XKPTkxntvcsBkHRMWP/+2ZECKbSjHvz0V95Q0Izub5BrtAw295Ilm5L5GyVCQnQYlxNQ4CF4iB9uHkTqfRQqQ2egobLTVlFHh1P/NrPl7bz1wIPLlWD9rqYOUyhZyKUwwfolTc9BTEvu+d++NQc5QTsI/dp7uMbMRjGmSfdt5PcOhiNIgWyIrePClQLUuNt56I0Sa+y5wGDnSRNKGxGeWoerZii3+PmOQOKFW8EhqPQGj8+1e4As7rHDAoMrUemjATW5RFdxhZhafIumzUT1FizdvfLqtlFWg/WqPlBrtuFo/Y6LxoSVU0zOJKnF/EbHB7qAqZ9nLZWUoh/JgkywTac2sqUB2gm1Gg19DGjPmy0NSBqI+LPugjq4/WgirBsk6S5rLyMzpHnms=",
            queryParameterSlot.captured["encrypted"]
        )
        assertEquals(
            "43eec1a8967c92b331d001d4e8ce0c232e9fedf92c4dd41f68896c2a6e238a91",
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

        val hostedQuixService = HostedQuixService()
        hostedQuixService.amount = "99.0"
        hostedQuixService.customerId = "903"
        hostedQuixService.cancelURL = "https://test.com/cancel"
        hostedQuixService.errorURL = "https://test.com/error"
        hostedQuixService.successURL = "https://test.com/success"
        hostedQuixService.awaitingURL = "https://test.com/awaiting"
        hostedQuixService.customerEmail = "test@mail.com"
        hostedQuixService.customerNationalId = "99999999RR"
        hostedQuixService.dob = "01-12-1999"
        hostedQuixService.firstName = "Name"
        hostedQuixService.lastName = "Last Name"
        hostedQuixService.merchantTransactionId = "12345678"

        val quixArticleService = QuixArticleService()
        quixArticleService.name = "Nombre del servicio 2"
        quixArticleService.reference = "4912345678903"
        quixArticleService.endDate = "2024-12-31T23:59:59+01:00"
        quixArticleService.unitPriceWithTax = 99.0
        quixArticleService.category = Category.digital

        val quixItemCartItemService = QuixItemCartItemService()
        quixItemCartItemService.article = quixArticleService
        quixItemCartItemService.units = 1
        quixItemCartItemService.isAutoShipping = true
        quixItemCartItemService.totalPriceWithTax = 99.0

        val items: MutableList<QuixItemCartItemService> = java.util.ArrayList()
        items.add(quixItemCartItemService)

        val quixCartService = QuixCartService()
        quixCartService.currency = Currency.EUR
        quixCartService.items = items
        quixCartService.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "28003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixServicePaySolExtendedData = QuixServicePaySolExtendedData()
        quixServicePaySolExtendedData.cart = quixCartService
        quixServicePaySolExtendedData.billing = quixBilling
        quixServicePaySolExtendedData.product = "instalments"

        hostedQuixService.paySolExtendedData = quixServicePaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            hostedQuixPaymentAdapter.sendHostedQuixServiceRequest(hostedQuixService, mockedResponseListener)
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

        val hostedQuixService = HostedQuixService()

        val exception = assertThrows<InvalidFieldException> {
            hostedQuixService.amount = "99,1123"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}