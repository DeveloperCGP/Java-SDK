import com.comerciaglobalpayments.javaPaymentSDK.adapters.NotificationAdapter
import com.comerciaglobalpayments.javaPaymentSDK.enums.Currency
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.NotificationResponses

class NotificationParserTest {

    @Test
    fun parseNotificationFullXMLTest() {
        val text = "<response>" +
                "<message>" +
                "test1" +
                "</message>" +
                "<status>" +
                "test2" +
                "</status>" +
                "</response>"

        val notification = NotificationAdapter.parseNotification(text)
        Assertions.assertEquals("test1", notification.message)
        Assertions.assertEquals("test2", notification.status)
    }

    @Test
    fun parseNotificationJSONInsideXMLTest() {
        val text = "<response>" +
                "{\"message\": \"test1\", \"status\": \"test2\"}" +
                "</response>"

        val notification = NotificationAdapter.parseNotification(text)
        Assertions.assertEquals("test1", notification.message)
        Assertions.assertEquals("test2", notification.status)
    }

    @Test
    fun parseNotificationFullJSONWithoutResponseTest() {
        val text = "{\"message\": \"test1\", \"status\": \"test2\"}"

        val notification = NotificationAdapter.parseNotification(text)
        Assertions.assertEquals("test1", notification.message)
        Assertions.assertEquals("test2", notification.status)
    }

    @Test
    fun parseNotificationFullJSONWithResponseTest() {
        val text = "{" +
                "   \"response\":{" +
                "      \"message\":\"test1\"," +
                "      \"status\":\"test2\"" +
                "   }" +
                "}"

        val notification = NotificationAdapter.parseNotification(text)
        Assertions.assertEquals("test1", notification.message)
        Assertions.assertEquals("test2", notification.status)
    }

    @Test
    fun parseNotificationXMLInsideJSONWithResponseTest() {
        val text = "{" +
                "\"response\": \"<response>" +
                "<message>" +
                "test1" +
                "</message>" +
                "<status>" +
                "test2" +
                "</status>" +
                "</response>\"" +
                "}"

        val notification = NotificationAdapter.parseNotification(text)
        Assertions.assertEquals("test1", notification.message)
        Assertions.assertEquals("test2", notification.status)
    }

    @Test
    fun parseNotificationWithOptionalParametersJSONTest() {
        val notification =
            NotificationAdapter.parseNotification(NotificationResponses.jsonNotificationWithOptionalTransactionParams)

        assertEquals(2, notification.operations.size)

        assertEquals("TRA", notification.operations[0].service)
        assertEquals("SUCCESS", notification.operations[0].status)
        assertEquals(30.0, notification.operations[0].amount)
        assertEquals(Currency.EUR, notification.operations[0].currency)
        assertEquals("8203", notification.operations[0].respCode.code)
        assertEquals("Frictionless requires", notification.operations[0].respCode.message)
        assertEquals("23506844", notification.operations[0].merchantTransactionId)

        assertEquals("3DSv2", notification.operations[1].service)
        assertEquals("REDIRECTED", notification.operations[1].status)
        assertEquals(30.0, notification.operations[1].amount)
        assertEquals(Currency.EUR, notification.operations[1].currency)
        assertEquals("threeDSMethodData", notification.operations[1].paymentDetails.extraDetails.entry[0].key)
        assertEquals(
            "eyJ0aHJlZURTU2VydmVyVHJhbnNJRCI6IjRhNzUwYmNlLWEwM2UtNGI1Ni1iMTRmLWE1YTBlNjc5YTRiOSIsICJ0aHJlZURTTWV0aG9kTm90aWZpY2F0aW9uVVJMIjogImh0dHBzOi8vY2hlY2tvdXQuc3RnLWV1LXdlc3QxLmVwZ2ludC5jb20vRVBHQ2hlY2tvdXQvY2FsbGJhY2svZ2F0aGVyRGV2aWNlTm90aWZpY2F0aW9uL3BheXNvbC8zZHN2Mi8xMTA4MTA0In0=",
            notification.operations[1].paymentDetails.extraDetails.entry[0].value
        )
        assertEquals("threeDSv2Token", notification.operations[1].paymentDetails.extraDetails.entry[1].key)
        assertEquals(
            "4a750bce-a03e-4b56-b14f-a5a0e679a4b9",
            notification.operations[1].paymentDetails.extraDetails.entry[1].value
        )
        assertEquals("sdk", notification.operations[1].optionalTransactionParams.entry[0].key)
        assertEquals("php", notification.operations[1].optionalTransactionParams.entry[0].value)
        assertEquals("type", notification.operations[1].optionalTransactionParams.entry[1].key)
        assertEquals("JsCharge", notification.operations[1].optionalTransactionParams.entry[1].value)
        assertEquals("version", notification.operations[1].optionalTransactionParams.entry[2].key)
        assertEquals("1.00", notification.operations[1].optionalTransactionParams.entry[2].value)

        assertEquals("ClaveN", notification.optionalTransactionParams.entry[0].key)
        assertEquals("ValorN", notification.optionalTransactionParams.entry[0].value)
        assertEquals("Clave1", notification.optionalTransactionParams.entry[1].key)
        assertEquals("Valor1", notification.optionalTransactionParams.entry[1].value)
        assertEquals("Clave2", notification.optionalTransactionParams.entry[2].key)
        assertEquals("Valor2", notification.optionalTransactionParams.entry[2].value)
    }

    @Test
    fun parseNotificationWithOptionalParametersXMLTest() {
        val notification =
            NotificationAdapter.parseNotification(NotificationResponses.xmlNotificationWithOptionalTransactionParams)

        assertEquals(3, notification.operations.size)

        assertEquals("TRA", notification.operations[0].service)
        assertEquals("SUCCESS", notification.operations[0].status)
        assertEquals(13.0, notification.operations[0].amount)
        assertEquals(Currency.EUR, notification.operations[0].currency)
        assertEquals("8203", notification.operations[0].respCode.code)
        assertEquals("Frictionless requires", notification.operations[0].respCode.message)
        assertEquals("1496918", notification.operations[0].merchantTransactionId)
        assertEquals("8232609", notification.operations[0].transactionId)

        assertEquals("3DSv2", notification.operations[1].service)
        assertEquals("SUCCESS3DS", notification.operations[1].status)
        assertEquals(13.0, notification.operations[1].amount)
        assertEquals(Currency.EUR, notification.operations[1].currency)
        assertEquals("1496918", notification.operations[1].merchantTransactionId)
        assertEquals("8232609", notification.operations[1].transactionId)
        assertEquals("nsY1", notification.operations[1].paymentCode)
        assertEquals("163c965a-9772-4bb1-a2f4-e96e184a2661", notification.operations[1].mpi.acsTransID)
        assertEquals("AJkBB4OBmVFmgYFYFIGZAAAAAAA=", notification.operations[1].mpi.cavv)

        assertEquals("SUCCESS", notification.operations[2].status)
        assertEquals("Success 'Settle' operation", notification.operations[2].message)
        assertEquals(13.0, notification.operations[2].amount)
        assertEquals(Currency.EUR, notification.operations[2].currency)
        assertEquals("8232609", notification.operations[2].transactionId)
        assertEquals("test", notification.operations[2].paymentDetails.cardHolderName)
        assertEquals("000", notification.operations[2].paymentCode)

        assertEquals("sdk", notification.operations.last().optionalTransactionParams.entry[0].key)
        assertEquals("php", notification.operations.last().optionalTransactionParams.entry[0].value)
        assertEquals("type", notification.operations.last().optionalTransactionParams.entry[1].key)
        assertEquals("JsCharge", notification.operations.last().optionalTransactionParams.entry[1].value)
        assertEquals("version", notification.operations.last().optionalTransactionParams.entry[2].key)
        assertEquals("1.00", notification.operations.last().optionalTransactionParams.entry[2].value)

        assertEquals("sdk", notification.optionalTransactionParams.entry[0].key)
        assertEquals("php", notification.optionalTransactionParams.entry[0].value)
        assertEquals("type", notification.optionalTransactionParams.entry[1].key)
        assertEquals("JsCharge", notification.optionalTransactionParams.entry[1].value)
        assertEquals("version", notification.optionalTransactionParams.entry[2].key)
        assertEquals("1.00", notification.optionalTransactionParams.entry[2].value)


        assertEquals("SUCCESS", notification.status)
    }
}