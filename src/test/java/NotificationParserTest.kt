import com.comerciaglobalpayments.javaPaymentSDK.adapters.NotificationAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
}