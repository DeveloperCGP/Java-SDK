import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TestClass {

    @Test
    void testFun() throws JsonProcessingException {
        NumberFormat numberFormat = new DecimalFormat("0.0000");
        System.out.println(numberFormat.format(511231.5748812312));
        return;
//        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
//                "<response operation-size=\"3\">\n" +
//                "    <message>WorkFlow has finished successfully, for transaction Id: 7542175</message>\n" +
//                "    <operations>\n" +
//                "        <operation sorted-order=\"1\">\n" +
//                "            <amount>36.00</amount>\n" +
//                "            <currency>EUR</currency>\n" +
//                "            <merchantTransactionId>92</merchantTransactionId>\n" +
//                "            <operationType>DEBIT</operationType>\n" +
//                "            <paySolTransactionId>38b8a363-8800-422c-accf-c6030d805ec2</paySolTransactionId>\n" +
//                "            <service>TRA</service>\n" +
//                "            <status>SUCCESS</status>\n" +
//                "            <transactionId>7542175</transactionId>\n" +
//                "            <respCode>\n" +
//                "                8203\n" +
//                "                <message>Frictionless requires</message>\n" +
//                "                <uuid>3f7b1f84_6e61_4a55_8ba9_d9bba597d897</uuid>\n" +
//                "            </respCode>\n" +
//                "        </operation>\n" +
//                "        <operation sorted-order=\"2\">\n" +
//                "            <amount>36.00</amount>\n" +
//                "            <currency>EUR</currency>\n" +
//                "            <merchantTransactionId>92</merchantTransactionId>\n" +
//                "            <message>3dsv2 - processed</message>\n" +
//                "            <operationType>DEBIT</operationType>\n" +
//                "            <paymentDetails>\n" +
//                "                <cardNumberToken>8956861270870014</cardNumberToken>\n" +
//                "                <extraDetails/>\n" +
//                "            </paymentDetails>\n" +
//                "            <service>3DSv2</service>\n" +
//                "            <status>SUCCESS3DS</status>\n" +
//                "            <transactionId>7542175</transactionId>\n" +
//                "            <respCode>\n" +
//                "                8001\n" +
//                "                <message>Success but not finished yet. Check Service Response</message>\n" +
//                "                <uuid>b187830c_33dc_47be_8f45_9c765ed0bcaa</uuid>\n" +
//                "            </respCode>\n" +
//                "            <mpi>\n" +
//                "                <acsTransID>0e4bfd73-0b44-4ed2-99ce-e15315e22783</acsTransID>\n" +
//                "                <authMethod>01</authMethod>\n" +
//                "                <authTimestamp>202306211254</authTimestamp>\n" +
//                "                <authenticationStatus>A</authenticationStatus>\n" +
//                "                <cavv>AJkBB4OBmVFmgYFYFIGZAAAAAAA=</cavv>\n" +
//                "                <eci>06</eci>\n" +
//                "                <messageVersion>2.2.0</messageVersion>\n" +
//                "                <threeDSSessionData>NTc5ZmE3M2MtZTEwZi00ZWY5LWJhOTItMDJhOTlmMDFhMDlj</threeDSSessionData>\n" +
//                "                <threeDSv2Token>579fa73c-e10f-4ef9-ba92-02a99f01a09c</threeDSv2Token>\n" +
//                "            </mpi>\n" +
//                "            <paymentCode>nsA4</paymentCode>\n" +
//                "            <paymentMessage>Not Authenticated, but a proof of authentication attempt was generated in AuthenticationValue</paymentMessage>\n" +
//                "        </operation>\n" +
//                "        <operation sorted-order=\"3\">\n" +
//                "            <amount>36.00</amount>\n" +
//                "            <currency>EUR</currency>\n" +
//                "            <details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 689749 230621145440\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Settle\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"************0014\",\"rfOperationDateTime\":\"21/06/23 14:54:40\",\"rfTerminalOperationNumber\":\"0116\",\"rfAuthNumber\":\"555579\",\"rfTransactionAmountCurrency\":\"36,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"rfReferenceId\":\"0116\",\"posTransactionToken\":\"{\\\"pucIdMsg\\\":\\\"1200\\\",\\\"pucP3ProcessCode\\\":\\\"000000\\\",\\\"pucP4OriginalAmount\\\":\\\"000000003600\\\",\\\"pucP11TransactionNumber\\\":\\\"689749\\\",\\\"pucP12LocalDateTime\\\":\\\"230621145440\\\",\\\"pucP22ServicePointData\\\":\\\"1U00506K3000\\\",\\\"pucP38AuthNumber\\\":\\\"555579\\\",\\\"pucP39ActionCode\\\":\\\"000\\\",\\\"pucP53SecurityControlInfo\\\":\\\"0102000001000000\\\",\\\"pinpadId\\\":\\\"1639\\\",\\\"pinpadAcquirerId\\\":\\\"00000500\\\",\\\"pinpadManufacturer\\\":null,\\\"pinpadModel\\\":null,\\\"pinpadSerialNumber\\\":null,\\\"pinpadSoftwareName\\\":null,\\\"pinpadSoftwareVersion\\\":null,\\\"pinpadKernelEmv\\\":null,\\\"pinpadVccStrip\\\":null,\\\"pinpadVerPup\\\":null,\\\"pinpadPciStage\\\":null,\\\"pinpadVerEmvParams\\\":null,\\\"pinpadEmvType\\\":null,\\\"pinpadCapabilities\\\":null,\\\"pinpadLanguage\\\":null,\\\"transactionType\\\":\\\"O\\\",\\\"transactionContactless\\\":\\\"0\\\",\\\"transactionDcc\\\":\\\"0\\\",\\\"transactionDccComission\\\":null,\\\"transactionDccExchangeRate\\\":null,\\\"transactionDccMarkUp\\\":null,\\\"transactionDccEntity\\\":null,\\\"transactionDccBceExchangeRate\\\":null,\\\"transactionDccBceMarkUp\\\":null,\\\"transactionPanSequenceNumber\\\":null,\\\"transactionTerminalOperationNumber\\\":\\\"0116\\\",\\\"transactionResponseCode\\\":null,\\\"transactionCurrency\\\":\\\"978\\\",\\\"transactionFuc\\\":\\\"355534686\\\",\\\"cardMaskedPan\\\":\\\"************0014\\\",\\\"cardAid\\\":null,\\\"cardDdfName\\\":null,\\\"cardApplicationLabel\\\":null,\\\"cardCypherData\\\":null}\",\"OperationResult\":\"000\"},\"threeDsProtocolVersion\":\"2.2.0\"}</details>\n" +
//                "            <merchantTransactionId>92</merchantTransactionId>\n" +
//                "            <message>Success 'Settle' operation with status 'SUCCESS'</message>\n" +
//                "            <operationType>DEBIT</operationType>\n" +
//                "            <optionalTransactionParams/>\n" +
//                "            <paySolTransactionId>355534686 790190 689749 230621145440</paySolTransactionId>\n" +
//                "            <paymentDetails>\n" +
//                "                <cardHolderName>First name Last name</cardHolderName>\n" +
//                "                <cardNumber>401200****0014</cardNumber>\n" +
//                "                <cardNumberToken>8956861270870014</cardNumberToken>\n" +
//                "                <cardType>visa/credit</cardType>\n" +
//                "                <expDate>0623</expDate>\n" +
//                "                <extraDetails>\n" +
//                "                    <entry>\n" +
//                "                        <key>cardCategory</key>\n" +
//                "                        <value>Not Available</value>\n" +
//                "                    </entry>\n" +
//                "                    <entry>\n" +
//                "                        <key>rememberMe</key>\n" +
//                "                        <value>true</value>\n" +
//                "                    </entry>\n" +
//                "                </extraDetails>\n" +
//                "                <issuerBank>EXTRAS TEST - VISA</issuerBank>\n" +
//                "                <issuerCountry>ES</issuerCountry>\n" +
//                "            </paymentDetails>\n" +
//                "            <paymentMethod>19900</paymentMethod>\n" +
//                "            <paymentSolution>caixapucpuce</paymentSolution>\n" +
//                "            <status>SUCCESS</status>\n" +
//                "            <transactionId>7542175</transactionId>\n" +
//                "            <respCode>\n" +
//                "                0000\n" +
//                "                <message>Successful</message>\n" +
//                "                <uuid>94230671_ecf0_4fbf_aa12_196a4f557621</uuid>\n" +
//                "            </respCode>\n" +
//                "            <authCode>555579</authCode>\n" +
//                "            <mpi>\n" +
//                "                <eci>06</eci>\n" +
//                "            </mpi>\n" +
//                "            <paymentCode>000</paymentCode>\n" +
//                "            <paymentMessage>Operación finalizada con éxito</paymentMessage>\n" +
//                "        </operation>\n" +
//                "    </operations>\n" +
//                "    <optionalTransactionParams/>\n" +
//                "    <status>SUCCESS</status>\n" +
//                "    <workFlowResponse>\n" +
//                "        <id>48787</id>\n" +
//                "        <name>debit creditcards (TRA)</name>\n" +
//                "        <version>0</version>\n" +
//                "    </workFlowResponse>\n" +
//                "</response>";
//
//        XmlMapper xmlMapper = new XmlMapper();
//        Notification notification
//                = xmlMapper.readValue(s, Notification.class);
//
//        System.out.println(notification.getMessage());

//        JSAuthorizationRequest jsAuthorizationRequest = new JSAuthorizationRequest(
//                Currency.EUR,
//                "1166590002",
//                "ES",
//                "903",
//                "debit"
//        );
//
//        Credentials credentials = new Credentials();
//        credentials.setMerchantId("116659");
//        credentials.setMerchantKey("35354a7e-ce21-40e7-863e-e58a8e53499e");
//        credentials.setMerchantPass("a723a2ce8ec3840c848d5914520c8199");
//        credentials.setProductId("1166590002");
//
//        Gson gson = new Gson();
//        String tt = gson.toJson(credentials);
//        String tt2 = gson.toJson(tt);
//
//        Configurations configurations = new Configurations();
//        configurations.setBaseUrl("https://epgjs-mep-stg.easypaymentgateway.com");
//
//        JSPaymentController jsPaymentController = new JSPaymentController(credentials, configurations);
//        jsPaymentController.sendJSAuthorizationRequest(jsAuthorizationRequest, new RequestCallback() {
//            @Override
//            public void onError(Error error) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//
//            }
//        });


    }

}
