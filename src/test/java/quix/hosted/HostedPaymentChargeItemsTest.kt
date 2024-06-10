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
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixArticleProduct
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixCartProduct
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixItemCartItemProduct
import com.comerciaglobalpayments.javaPaymentSDK.models.quix_models.quix_product.QuixItemPaySolExtendedData
import com.comerciaglobalpayments.javaPaymentSDK.models.requests.quix_hosted.HostedQuixItem
import com.comerciaglobalpayments.javaPaymentSDK.utils.SecurityUtils
import io.mockk.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HostedPaymentChargeItemsTest {

    @Test
    fun successHostedNotification() {

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

        val hostedQuixItem = HostedQuixItem()
        hostedQuixItem.amount = "99.0"
        hostedQuixItem.customerId = "903"
        hostedQuixItem.statusURL = "https://test.com/paymentNotification"
        hostedQuixItem.cancelURL = "https://test.com/cancel"
        hostedQuixItem.errorURL = "https://test.com/error"
        hostedQuixItem.successURL = "https://test.com/success"
        hostedQuixItem.awaitingURL = "https://test.com/awaiting"
        hostedQuixItem.customerEmail = "test@mail.com"
        hostedQuixItem.customerNationalId = "99999999RR"
        hostedQuixItem.dob = "01-12-1999"
        hostedQuixItem.firstName = "Name"
        hostedQuixItem.lastName = "Last Name"
        hostedQuixItem.merchantTransactionId = "12345678"
        hostedQuixItem.ipAddress = "0.0.0.0"

        val quixArticleProduct = QuixArticleProduct()
        quixArticleProduct.name = "Nombre del servicio 2"
        quixArticleProduct.reference = "4912345678903"
        quixArticleProduct.unitPriceWithTax = 99.0
        quixArticleProduct.category = Category.digital

        val quixItemCartItemProduct = QuixItemCartItemProduct()
        quixItemCartItemProduct.article = quixArticleProduct
        quixItemCartItemProduct.units = 1
        quixItemCartItemProduct.isAutoShipping = true
        quixItemCartItemProduct.total_price_with_tax = 99.0

        val items: MutableList<QuixItemCartItemProduct> = ArrayList()
        items.add(quixItemCartItemProduct)

        val quixCartProduct = QuixCartProduct()
        quixCartProduct.currency = Currency.EUR
        quixCartProduct.items = items
        quixCartProduct.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "08003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixItemPaySolExtendedData = QuixItemPaySolExtendedData()
        quixItemPaySolExtendedData.cart = quixCartProduct
        quixItemPaySolExtendedData.billing = quixBilling
        quixItemPaySolExtendedData.product = "instalments"

        hostedQuixItem.paySolExtendedData = quixItemPaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)
        hostedQuixPaymentAdapter.sendHostedQuixItemRequest(hostedQuixItem, mockedResponseListener)

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
            "pDH/U+/gbuzXdYp84aiQKsVwdo0OluLSE7iid4fDTDsOp3Iz5PMaVkId+H/okm/59Slik6eoVuhf9S0X7utcyiYp1zqBuvvjPWiO0Nmne1/ZLwf2liuTEo6jRVTCGjokuW3KnOMHbgeoHjg5TaK6fzocze2OWBs55Luc+A4onL6/qm7Lt8dAhWkUjIcWzIE5KXyKPm4Icm16zGh5wmDou/WEtJVEedu7LsO1HfOvJro6s39Ya+e8RAaNEoQZ64f4J8kDU9KYEm6aQZrOEp/+n1wI2Vc7u/6Y/VGO2ye7649smVWFsPhgGe9L8i5wzQRI4xpVpKLKQKe2Opx7fG7FZVgy1RZ8Ye4t3KZ8qEKlpHMTriACrdB4QxcwctvbRQCWgjCqCEDwD+98eTefF1LRqh2++/xptrxrXxBl+oOiyNbIdG8ZllTmYZIDF/dIESFWQUXqxL3vAYEj4CMctMZSmb2721NohunvlobjzKnbl/LB8o2dxsCrONuhDn8AX4I+5+IErn+6ifP0cgce4p1LwQL3twThfnqWZRIz0D9O54QEVSj/MTHwTFumZHiNdHlw3jesezHFj9nZDOxgEB42fIT+gOMk5dK5Mq5Y9Bq/ngjN22s5zzfMIgjScm4jwRFa6IszqGWhbrDZex6rJ98Lmrw/lQF4H6d4Gmpjmi8NR8ws30JMlmjNZaxeWJmdR1oCfrL3toKefI/Vf1Ir+8Se7FhBMM72Aybz0ySfsAYFTksou/vSI3Sv2DxOdvo/NtigZw3VWcTHt/Cb0vrhTAjv8b7JOLXrJ5+LCEkTkUO9QufvsAci+XqkWrxU3QnXUYpakpwi3pl7n9N+I3xjPtsrPbZQtVi+b+LBaza7yxgBKaSenyvP7aF7DBm9GPxfvVcn+jl7NbEbeEUGDd0zdmFGTbbyokYcoXD6bMLXq6mR9c4Bzh5uwrOAVXpXSRtCioeaZ6LsI9oHnMh1C43fOrkyU9p2Tf0Z4fV2WbbXTITUhKO/CSH0IOFT4t7uGnMehdo9OlAzK+wWGrIv1EpiATkkW2sD8OiGiti4sVoPh8V3pQsW4w1Mal1DSwYFcAXLnzruMrpAyJ4KgmFZ8aIxlwqp2mUrm7TcZvdJ8j6jJ3ZdqZ+Zgd167XrKDqsjPzQv8Pq68tEIZhBICLPZTF4L7gcC7HwMk4M6dMDszTWCXlxFO/RuHu/pF89znC6S7mqfh8hVqfqQsTNWRvX4UXTXfWYThaI3xqK3HhnNz+20Qz+UUETpCccSpH+DPtlh99PyLq3jfMiLLIhFXcv09g4LjtPDjaOhGY7EaowTc7IfgNWtIOPuljtASG0brO+q6p0TuamT34nCBs4jjRBRZ/dohf0nOQYBijJI3pfBUtgiW/JKdd+HudVoGR0RHnWC7JnteZZZf0yrxAXFfs/0HcU749JHTfoAnV5EvfEbqoX1Opi+eaPLPzS8LoCURvNJx0GZM2aZqQoF1ID+az+RXCpGGvacxStRhdjPMEOG+mE1/VlYERX9gZLZ4ihRWbqntN3QoMDqV8IL4Uekq3i+YuOvYJ39imgEe4o/4nkD5XIomtaL+aIhWgk3j+NV3nDQNF952INYE/Jlbx3hzaImp1BAdAxNmNJ8JxaEXtJNsRwfQhdQ1C8=",
            queryParameterSlot.captured["encrypted"]
        )
        assertEquals(
            "2aa4296ee339839bde555cb515fb9e362cd29c83c17fd5c0797b2b86c5b23efc",
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

        val hostedQuixItem = HostedQuixItem()
        hostedQuixItem.amount = "99.0"
        hostedQuixItem.customerId = "903"
        hostedQuixItem.cancelURL = "https://test.com/cancel"
        hostedQuixItem.errorURL = "https://test.com/error"
        hostedQuixItem.successURL = "https://test.com/success"
        hostedQuixItem.customerEmail = "test@mail.com"
        hostedQuixItem.customerNationalId = "99999999RR"
        hostedQuixItem.dob = "01-12-1999"
        hostedQuixItem.firstName = "Name"
        hostedQuixItem.lastName = "Last Name"
        hostedQuixItem.merchantTransactionId = "12345678"

        val quixArticleProduct = QuixArticleProduct()
        quixArticleProduct.name = "Nombre del servicio 2"
        quixArticleProduct.reference = "4912345678903"
        quixArticleProduct.unitPriceWithTax = 99.0
        quixArticleProduct.category = Category.digital

        val quixItemCartItemProduct = QuixItemCartItemProduct()
        quixItemCartItemProduct.article = quixArticleProduct
        quixItemCartItemProduct.units = 1
        quixItemCartItemProduct.isAutoShipping = true
        quixItemCartItemProduct.total_price_with_tax = 99.0

        val items: MutableList<QuixItemCartItemProduct> = ArrayList()
        items.add(quixItemCartItemProduct)

        val quixCartProduct = QuixCartProduct()
        quixCartProduct.currency = Currency.EUR
        quixCartProduct.items = items
        quixCartProduct.totalPriceWithTax = 99.0

        val quixAddress = QuixAddress()
        quixAddress.city = "Barcelona"
        quixAddress.setCountry(CountryCode.ES)
        quixAddress.streetAddress = "Nombre de la vía y nº"
        quixAddress.postalCode = "08003"

        val quixBilling = QuixBilling()
        quixBilling.address = quixAddress
        quixBilling.firstName = "Nombre"
        quixBilling.lastName = "Apellido"

        val quixItemPaySolExtendedData = QuixItemPaySolExtendedData()
        quixItemPaySolExtendedData.cart = quixCartProduct
        quixItemPaySolExtendedData.billing = quixBilling
        quixItemPaySolExtendedData.product = "instalments"

        hostedQuixItem.paySolExtendedData = quixItemPaySolExtendedData

        val hostedQuixPaymentAdapter = HostedQuixPaymentAdapter(credentials)

        val exception = assertThrows<MissingFieldException> {
            hostedQuixPaymentAdapter.sendHostedQuixItemRequest(hostedQuixItem, mockedResponseListener)
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

        val hostedQuixItem = HostedQuixItem()

        val exception = assertThrows<InvalidFieldException> {
            hostedQuixItem.amount = "99,01"
        }

        assertEquals("amount: Should Follow Format #.#### And Be Between 0 And 1000000", exception.message)
    }
}