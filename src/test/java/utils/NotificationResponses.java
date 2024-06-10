package utils;

public class NotificationResponses {

    public static String h2HRedirectionSuccessResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response operation-size=\"2\"><message>WorkFlow has finished successfully, for transaction Id: 7818280</message><operations><operation sorted-order=\"1\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>76772304</merchantTransactionId><operationType>DEBIT</operationType><paySolTransactionId>e9ef153d-d024-40ed-ac5f-283d486d84c1</paySolTransactionId><service>TRA</service><status>SUCCESS</status><transactionId>7818280</transactionId><respCode><code>8203</code><message>Frictionless requires</message><uuid>67cf5c1e_3af8_4683_8575_bd7aae62198c</uuid></respCode></operation><operation sorted-order=\"2\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>76772304</merchantTransactionId><message>Starting 3DSecure 2.0 process.</message><operationType>DEBIT</operationType><optionalTransactionParams/><originalTransactionId>7818280</originalTransactionId><paymentDetails><cardHolderName>First name Last name</cardHolderName><extraDetails><entry><key>threeDSMethodData</key><value>eyJ0aHJlZURTU2VydmVyVHJhbnNJRCI6ImQ1NmEzNTVlLTQ4NjktNDFhNy1iOTRiLTAxNWMwYzVkZTIwNiIsICJ0aHJlZURTTWV0aG9kTm90aWZpY2F0aW9uVVJMIjogImh0dHBzOi8vY2hlY2tvdXQuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vRVBHQ2hlY2tvdXQvY2FsbGJhY2svZ2F0aGVyRGV2aWNlTm90aWZpY2F0aW9uL3BheXNvbC8zZHN2Mi8xMDIxNjM2In0=</value></entry><entry><key>threeDSv2Token</key><value>d56a355e-4869-41a7-b94b-015c0c5de206</value></entry></extraDetails></paymentDetails><redirectionResponse>redirect:https://checkout.stg-eu-west3.epgint.com/EPGCheckout/rest/online/3dsv2/redirect?action=gatherdevice&amp;params=eyJ0aHJlZURTdjJUb2tlbiI6ImQ1NmEzNTVlLTQ4NjktNDFhNy1iOTRiLTAxNWMwYzVkZTIwNiIsInRocmVlRFNNZXRob2RVcmwiOiJodHRwczovL21vY2stZHMuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vcHVibGljL21ldGhvZC1kYXRhLyIsInRocmVlRFNNZXRob2REYXRhIjoiZXlKMGFISmxaVVJUVTJWeWRtVnlWSEpoYm5OSlJDSTZJbVExTm1Fek5UVmxMVFE0TmprdE5ERmhOeTFpT1RSaUxUQXhOV013WXpWa1pUSXdOaUlzSUNKMGFISmxaVVJUVFdWMGFHOWtUbTkwYVdacFkyRjBhVzl1VlZKTUlqb2dJbWgwZEhCek9pOHZZMmhsWTJ0dmRYUXVjM1JuTFdWMUxYZGxjM1F6TG1Wd1oybHVkQzVqYjIwdlJWQkhRMmhsWTJ0dmRYUXZZMkZzYkdKaFkyc3ZaMkYwYUdWeVJHVjJhV05sVG05MGFXWnBZMkYwYVc5dUwzQmhlWE52YkM4elpITjJNaTh4TURJeE5qTTJJbjA9IiwiYnJhbmQiOm51bGwsInJlc3VtZUF1dGhlbnRpY2F0aW9uIjoiaHR0cHM6Ly9jaGVja291dC5zdGctZXUtd2VzdDMuZXBnaW50LmNvbS9FUEdDaGVja291dC9yZXR1cm51cmwvZnJpY3Rpb25sZXNzL3BheXNvbC8zZHN2Mi8xMDIxNjM2P3RocmVlRFN2MlRva2VuPWQ1NmEzNTVlLTQ4NjktNDFhNy1iOTRiLTAxNWMwYzVkZTIwNiIsInJlbmRlckNhc2hpZXJMb2NhdGlvbiI6Imh0dHBzOi8vZXBnanMtcmVuZGVyY2FzaGllci1zdGcuZWFzeXBheW1lbnRnYXRld2F5LmNvbSIsImNoYWxsZW5nZVdpbmRvd3NTaXplIjoiMDUifQ==</redirectionResponse><service>3DSv2</service><status>REDIRECTED</status><transactionId>1021636</transactionId><respCode><code>8100</code><message>Frictionless requires</message><uuid>33d214d9_d917_477d_bd55_ff00de31a4a2</uuid></respCode><mpi><acsEndProtocolVersion>2.2.0</acsEndProtocolVersion><acsStartProtocolVersion>2.1.0</acsStartProtocolVersion><dsEndProtocolVersion>2.2.0</dsEndProtocolVersion><dsStartProtocolVersion>2.1.0</dsStartProtocolVersion><threeDSMethodURL>https://mock-ds.stg-eu-west3.epgint.com/public/method-data/</threeDSMethodURL></mpi></operation></operations><optionalTransactionParams/><status>SUCCESS</status><workFlowResponse><id>48787</id><name>debit creditcards (TRA)</name><version>0</version></workFlowResponse></response>";

    public static String finalH2HRedirectionSuccessNotification = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response operation-size=\"3\"><message>WorkFlow has finished successfully, for transaction Id: 7818280</message><operations><operation sorted-order=\"1\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>76772304</merchantTransactionId><operationType>DEBIT</operationType><paySolTransactionId>e9ef153d-d024-40ed-ac5f-283d486d84c1</paySolTransactionId><service>TRA</service><status>SUCCESS</status><transactionId>7818280</transactionId><respCode><code>8203</code><message>Frictionless requires</message><uuid>67cf5c1e_3af8_4683_8575_bd7aae62198c</uuid></respCode></operation><operation sorted-order=\"2\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>76772304</merchantTransactionId><message>3dsv2 - processed</message><operationType>DEBIT</operationType><paymentDetails><cardNumberToken>6537275043632227</cardNumberToken><extraDetails/></paymentDetails><service>3DSv2</service><status>SUCCESS3DS</status><transactionId>7818280</transactionId><respCode><code>8000</code><message>Successful authentication</message><uuid>fc0ab0a2_908d_4d6f_8baf_3dac1aa610d6</uuid></respCode><mpi><acsTransID>3de5f92c-cb69-4e09-8fa2-12b668519b04</acsTransID><authMethod>01</authMethod><authTimestamp>202401311702</authTimestamp><authenticationStatus>Y</authenticationStatus><cavv>AJkBB4OBmVFmgYFYFIGZAAAAAAA=</cavv><eci>05</eci><messageVersion>2.2.0</messageVersion><threeDSSessionData>ZDU2YTM1NWUtNDg2OS00MWE3LWI5NGItMDE1YzBjNWRlMjA2</threeDSSessionData><threeDSv2Token>d56a355e-4869-41a7-b94b-015c0c5de206</threeDSv2Token></mpi><paymentCode>nsY1</paymentCode><paymentMessage>Authenticated successfully</paymentMessage></operation><operation sorted-order=\"3\"><amount>50.00</amount><currency>EUR</currency><details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 947965 240131180252\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Settle\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"************2227\",\"rfOperationDateTime\":\"31/01/24 18:02:52\",\"rfTerminalOperationNumber\":\"0926\",\"rfAuthNumber\":\"246953\",\"rfTransactionAmountCurrency\":\"50,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"rfReferenceId\":\"0926\",\"posTransactionToken\":\"{\\\"pucIdMsg\\\":\\\"1200\\\",\\\"pucP3ProcessCode\\\":\\\"000000\\\",\\\"pucP4OriginalAmount\\\":\\\"000000005000\\\",\\\"pucP11TransactionNumber\\\":\\\"947965\\\",\\\"pucP12LocalDateTime\\\":\\\"240131180252\\\",\\\"pucP22ServicePointData\\\":\\\"1U00506K3000\\\",\\\"pucP38AuthNumber\\\":\\\"246953\\\",\\\"pucP39ActionCode\\\":\\\"000\\\",\\\"pucP53SecurityControlInfo\\\":\\\"0102000001000000\\\",\\\"pinpadId\\\":\\\"1639\\\",\\\"pinpadAcquirerId\\\":\\\"00000500\\\",\\\"pinpadManufacturer\\\":null,\\\"pinpadModel\\\":null,\\\"pinpadSerialNumber\\\":null,\\\"pinpadSoftwareName\\\":null,\\\"pinpadSoftwareVersion\\\":null,\\\"pinpadKernelEmv\\\":null,\\\"pinpadVccStrip\\\":null,\\\"pinpadVerPup\\\":null,\\\"pinpadPciStage\\\":null,\\\"pinpadVerEmvParams\\\":null,\\\"pinpadEmvType\\\":null,\\\"pinpadCapabilities\\\":null,\\\"pinpadLanguage\\\":null,\\\"transactionType\\\":\\\"O\\\",\\\"transactionContactless\\\":\\\"0\\\",\\\"transactionDcc\\\":\\\"0\\\",\\\"transactionDccComission\\\":null,\\\"transactionDccExchangeRate\\\":null,\\\"transactionDccMarkUp\\\":null,\\\"transactionDccEntity\\\":null,\\\"transactionDccBceExchangeRate\\\":null,\\\"transactionDccBceMarkUp\\\":null,\\\"transactionPanSequenceNumber\\\":null,\\\"transactionTerminalOperationNumber\\\":\\\"0926\\\",\\\"transactionResponseCode\\\":null,\\\"transactionCurrency\\\":\\\"978\\\",\\\"transactionFuc\\\":\\\"355534686\\\",\\\"cardMaskedPan\\\":\\\"************2227\\\",\\\"cardAid\\\":null,\\\"cardDdfName\\\":null,\\\"cardApplicationLabel\\\":null,\\\"cardCypherData\\\":null}\",\"OperationResult\":\"000\"},\"threeDsProtocolVersion\":\"2.2.0\"}</details><merchantTransactionId>76772304</merchantTransactionId><message>Success 'Settle' operation</message><operationType>DEBIT</operationType><optionalTransactionParams/><paySolTransactionId>355534686 790190 947965 240131180252</paySolTransactionId><paymentDetails><cardHolderName>First name Last name</cardHolderName><cardNumber>490727****2227</cardNumber><cardNumberToken>6537275043632227</cardNumberToken><cardType>VISA/CREDIT</cardType><expDate>0625</expDate><extraDetails><entry><key>cardCategory</key><value>Not Available</value></entry><entry><key>rememberMe</key><value>true</value></entry></extraDetails><issuerBank>SERVIRED MASTERCARD INTERNACIONAL</issuerBank><issuerCountry>ES</issuerCountry></paymentDetails><paymentMethod>19900</paymentMethod><paymentSolution>caixapucpuce</paymentSolution><status>SUCCESS</status><transactionId>7818280</transactionId><respCode><code>0000</code><message>Successful</message><uuid>1bb66921_b4ed_4e8f_837d_7f20158a66ce</uuid></respCode><authCode>246953</authCode><mpi><eci>05</eci></mpi><paymentCode>000</paymentCode><paymentMessage>Operación finalizada con éxito</paymentMessage></operation></operations><optionalTransactionParams/><status>SUCCESS</status><workFlowResponse><id>48787</id><name>debit creditcards (TRA)</name><version>0</version></workFlowResponse></response>";

    public static String finalHostedRedirectionSuccessNotification = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response operation-size=\"3\"><message>WorkFlow has finished successfully, for transaction Id: 7819696</message><operations><operation sorted-order=\"1\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>30825486</merchantTransactionId><operationType>DEBIT</operationType><paySolTransactionId>abbc823a-e6ad-4419-9b87-f074540393a7</paySolTransactionId><service>TRA</service><status>SUCCESS</status><transactionId>7819696</transactionId><respCode><code>8203</code><message>Frictionless requires</message><uuid>102b6ae2_85f9_476d_9baa_20b1c649ee1a</uuid></respCode></operation><operation sorted-order=\"2\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>30825486</merchantTransactionId><message>3dsv2 - processed</message><operationType>DEBIT</operationType><paymentDetails><cardNumberToken>6537275043632227</cardNumberToken><extraDetails/></paymentDetails><service>3DSv2</service><status>SUCCESS3DS</status><transactionId>7819696</transactionId><respCode><code>8000</code><message>Successful authentication</message><uuid>fc0ab0a2_908d_4d6f_8baf_3dac1aa610d6</uuid></respCode><mpi><acsTransID>76fff2e9-9681-4842-943a-4cd1a19e576a</acsTransID><authMethod>01</authMethod><authTimestamp>202402012005</authTimestamp><authenticationStatus>Y</authenticationStatus><cavv>AJkBB4OBmVFmgYFYFIGZAAAAAAA=</cavv><eci>05</eci><messageVersion>2.2.0</messageVersion><threeDSSessionData>MDA4ZjY5ZmEtNzFlNi00OTRjLTliOTctNjg3M2NiMGIwN2Ji</threeDSSessionData><threeDSv2Token>008f69fa-71e6-494c-9b97-6873cb0b07bb</threeDSv2Token></mpi><paymentCode>nsY1</paymentCode><paymentMessage>Authenticated successfully</paymentMessage></operation><operation sorted-order=\"3\"><amount>50.00</amount><currency>EUR</currency><details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 949433 240201210513\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Settle\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"************2227\",\"rfOperationDateTime\":\"01/02/24 21:05:13\",\"rfTerminalOperationNumber\":\"0951\",\"rfAuthNumber\":\"806056\",\"rfTransactionAmountCurrency\":\"50,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"rfReferenceId\":\"0951\",\"posTransactionToken\":\"{\\\"pucIdMsg\\\":\\\"1200\\\",\\\"pucP3ProcessCode\\\":\\\"000000\\\",\\\"pucP4OriginalAmount\\\":\\\"000000005000\\\",\\\"pucP11TransactionNumber\\\":\\\"949433\\\",\\\"pucP12LocalDateTime\\\":\\\"240201210513\\\",\\\"pucP22ServicePointData\\\":\\\"1U00506K3000\\\",\\\"pucP38AuthNumber\\\":\\\"806056\\\",\\\"pucP39ActionCode\\\":\\\"000\\\",\\\"pucP53SecurityControlInfo\\\":\\\"0102000001000000\\\",\\\"pinpadId\\\":\\\"1639\\\",\\\"pinpadAcquirerId\\\":\\\"00000500\\\",\\\"pinpadManufacturer\\\":null,\\\"pinpadModel\\\":null,\\\"pinpadSerialNumber\\\":null,\\\"pinpadSoftwareName\\\":null,\\\"pinpadSoftwareVersion\\\":null,\\\"pinpadKernelEmv\\\":null,\\\"pinpadVccStrip\\\":null,\\\"pinpadVerPup\\\":null,\\\"pinpadPciStage\\\":null,\\\"pinpadVerEmvParams\\\":null,\\\"pinpadEmvType\\\":null,\\\"pinpadCapabilities\\\":null,\\\"pinpadLanguage\\\":null,\\\"transactionType\\\":\\\"O\\\",\\\"transactionContactless\\\":\\\"0\\\",\\\"transactionDcc\\\":\\\"0\\\",\\\"transactionDccComission\\\":null,\\\"transactionDccExchangeRate\\\":null,\\\"transactionDccMarkUp\\\":null,\\\"transactionDccEntity\\\":null,\\\"transactionDccBceExchangeRate\\\":null,\\\"transactionDccBceMarkUp\\\":null,\\\"transactionPanSequenceNumber\\\":null,\\\"transactionTerminalOperationNumber\\\":\\\"0951\\\",\\\"transactionResponseCode\\\":null,\\\"transactionCurrency\\\":\\\"978\\\",\\\"transactionFuc\\\":\\\"355534686\\\",\\\"cardMaskedPan\\\":\\\"************2227\\\",\\\"cardAid\\\":null,\\\"cardDdfName\\\":null,\\\"cardApplicationLabel\\\":null,\\\"cardCypherData\\\":null}\",\"OperationResult\":\"000\"},\"threeDsProtocolVersion\":\"2.2.0\"}</details><merchantTransactionId>30825486</merchantTransactionId><message>Success 'Settle' operation</message><operationType>DEBIT</operationType><optionalTransactionParams/><paySolTransactionId>355534686 790190 949433 240201210513</paySolTransactionId><paymentDetails><cardHolderName>First name Last name</cardHolderName><cardNumber>490727****2227</cardNumber><cardNumberToken>6537275043632227</cardNumberToken><cardType>VISA/CREDIT</cardType><expDate>0625</expDate><extraDetails><entry><key>cardCategory</key><value>Not Available</value></entry><entry><key>rememberMe</key><value>true</value></entry></extraDetails><issuerBank>SERVIRED MASTERCARD INTERNACIONAL</issuerBank><issuerCountry>ES</issuerCountry></paymentDetails><paymentMethod>19900</paymentMethod><paymentSolution>caixapucpuce</paymentSolution><status>SUCCESS</status><transactionId>7819696</transactionId><respCode><code>0000</code><message>Successful</message><uuid>3b1da2c1_ccfe_466a_a866_f0fecd23d084</uuid></respCode><authCode>806056</authCode><mpi><eci>05</eci></mpi><paymentCode>000</paymentCode><paymentMessage>Operaci�n finalizada con �xito</paymentMessage></operation></operations><optionalTransactionParams/><status>SUCCESS</status><workFlowResponse><id>48787</id><name>debit creditcards (TRA)</name><version>0</version></workFlowResponse></response>";

    public static String finalHostedRecurringSuccessNotification = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response operation-size=\"3\"><message>WorkFlow has finished successfully, for transaction Id: 7819700</message><operations><operation sorted-order=\"1\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>86774421</merchantTransactionId><operationType>DEBIT</operationType><paySolTransactionId>915137ed-bfbb-49fb-a4a0-d8cdc2bcd599</paySolTransactionId><service>TRA</service><status>SUCCESS</status><transactionId>7819700</transactionId><respCode><code>8203</code><message>Frictionless requires</message><uuid>32fd1c3a_4a80_4717_b812_57f01f5be912</uuid></respCode></operation><operation sorted-order=\"2\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>86774421</merchantTransactionId><message>3dsv2 - processed</message><operationType>DEBIT</operationType><paymentDetails><cardNumberToken>6537275043632227</cardNumberToken><extraDetails/></paymentDetails><service>3DSv2</service><status>SUCCESS3DS</status><transactionId>7819700</transactionId><respCode><code>8000</code><message>Successful authentication</message><uuid>3df99914_ba84_40b0_88d6_12df13f39320</uuid></respCode><mpi><acsTransID>ca1f233c-e9dc-4a2f-92c3-454faf5342e0</acsTransID><authMethod>01</authMethod><authTimestamp>202402012045</authTimestamp><authenticationStatus>Y</authenticationStatus><cavv>AJkBB4OBmVFmgYFYFIGZAAAAAAA=</cavv><eci>05</eci><messageVersion>2.2.0</messageVersion><threeDSSessionData>NTc5MjhkNDUtZTlmZS00MGNlLTg5Y2MtY2RmMDgzZjhjZGVh</threeDSSessionData><threeDSv2Token>57928d45-e9fe-40ce-89cc-cdf083f8cdea</threeDSv2Token></mpi><paymentCode>nsY1</paymentCode><paymentMessage>Authenticated successfully</paymentMessage></operation><operation sorted-order=\"3\"><amount>50.00</amount><currency>EUR</currency><details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 949466 240201214525\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Settle\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"************2227\",\"rfOperationDateTime\":\"01/02/24 21:45:25\",\"rfTerminalOperationNumber\":\"0952\",\"rfAuthNumber\":\"536100\",\"rfTransactionAmountCurrency\":\"50,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"rfReferenceId\":\"0952\",\"posTransactionToken\":\"{\\\"pucIdMsg\\\":\\\"1200\\\",\\\"pucP3ProcessCode\\\":\\\"000000\\\",\\\"pucP4OriginalAmount\\\":\\\"000000005000\\\",\\\"pucP11TransactionNumber\\\":\\\"949466\\\",\\\"pucP12LocalDateTime\\\":\\\"240201214525\\\",\\\"pucP22ServicePointData\\\":\\\"1U00506K3000\\\",\\\"pucP38AuthNumber\\\":\\\"536100\\\",\\\"pucP39ActionCode\\\":\\\"000\\\",\\\"pucP53SecurityControlInfo\\\":\\\"0102000001000000\\\",\\\"pinpadId\\\":\\\"1639\\\",\\\"pinpadAcquirerId\\\":\\\"00000500\\\",\\\"pinpadManufacturer\\\":null,\\\"pinpadModel\\\":null,\\\"pinpadSerialNumber\\\":null,\\\"pinpadSoftwareName\\\":null,\\\"pinpadSoftwareVersion\\\":null,\\\"pinpadKernelEmv\\\":null,\\\"pinpadVccStrip\\\":null,\\\"pinpadVerPup\\\":null,\\\"pinpadPciStage\\\":null,\\\"pinpadVerEmvParams\\\":null,\\\"pinpadEmvType\\\":null,\\\"pinpadCapabilities\\\":null,\\\"pinpadLanguage\\\":null,\\\"transactionType\\\":\\\"O\\\",\\\"transactionContactless\\\":\\\"0\\\",\\\"transactionDcc\\\":\\\"0\\\",\\\"transactionDccComission\\\":null,\\\"transactionDccExchangeRate\\\":null,\\\"transactionDccMarkUp\\\":null,\\\"transactionDccEntity\\\":null,\\\"transactionDccBceExchangeRate\\\":null,\\\"transactionDccBceMarkUp\\\":null,\\\"transactionPanSequenceNumber\\\":null,\\\"transactionTerminalOperationNumber\\\":\\\"0952\\\",\\\"transactionResponseCode\\\":null,\\\"transactionCurrency\\\":\\\"978\\\",\\\"transactionFuc\\\":\\\"355534686\\\",\\\"cardMaskedPan\\\":\\\"************2227\\\",\\\"cardAid\\\":null,\\\"cardDdfName\\\":null,\\\"cardApplicationLabel\\\":null,\\\"cardCypherData\\\":null}\",\"OperationResult\":\"000\",\"cofAdditionalInformation\":\"CS285677615514783\"},\"threeDsProtocolVersion\":\"2.2.0\"}</details><merchantTransactionId>86774421</merchantTransactionId><message>Success 'Settle' operation</message><operationType>DEBIT</operationType><optionalTransactionParams/><paySolTransactionId>355534686 790190 949466 240201214525</paySolTransactionId><paymentDetails><cardHolderName>First name Last name</cardHolderName><cardNumber>490727****2227</cardNumber><cardNumberToken>6537275043632227</cardNumberToken><cardType>VISA/CREDIT</cardType><expDate>0625</expDate><extraDetails><entry><key>cardCategory</key><value>Not Available</value></entry><entry><key>rememberMe</key><value>true</value></entry></extraDetails><issuerBank>SERVIRED MASTERCARD INTERNACIONAL</issuerBank><issuerCountry>ES</issuerCountry></paymentDetails><paymentMethod>19900</paymentMethod><paymentSolution>caixapucpuce</paymentSolution><status>SUCCESS</status><transactionId>7819700</transactionId><respCode><code>0000</code><message>Successful</message><uuid>da44f835_d1c8_4d10_aeab_f61ba417439b</uuid></respCode><authCode>536100</authCode><mpi><eci>05</eci></mpi><paymentCode>000</paymentCode><paymentMessage>Operaci�n finalizada con �xito</paymentMessage><subscriptionPlan>285677615514783</subscriptionPlan></operation></operations><optionalTransactionParams/><status>SUCCESS</status><workFlowResponse><id>48787</id><name>debit creditcards (TRA)</name><version>0</version></workFlowResponse></response>";

    public static String h2HRecurringSuccessResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response operation-size=\"2\"><message>WorkFlow has finished successfully, for transaction Id: 7819704</message><operations><operation sorted-order=\"1\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>46604547</merchantTransactionId><operationType>DEBIT</operationType><paySolTransactionId>a448918c-d6f1-412f-b4ea-44e303a961a2</paySolTransactionId><service>TRA</service><status>SUCCESS</status><transactionId>7819704</transactionId><respCode><code>8203</code><message>Frictionless requires</message><uuid>83373ab4_026e_4fed_a0df_9b990ac0492a</uuid></respCode></operation><operation sorted-order=\"2\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>46604547</merchantTransactionId><message>Starting 3DSecure 2.0 process.</message><operationType>DEBIT</operationType><optionalTransactionParams/><originalTransactionId>7819704</originalTransactionId><paymentDetails><cardHolderName>First name Last name</cardHolderName><extraDetails><entry><key>threeDSMethodData</key><value>eyJ0aHJlZURTU2VydmVyVHJhbnNJRCI6IjM2ODU5NGM2LTUzMDItNDlhOC04NThhLTFkNTgwOTM3N2Q0YSIsICJ0aHJlZURTTWV0aG9kTm90aWZpY2F0aW9uVVJMIjogImh0dHBzOi8vY2hlY2tvdXQuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vRVBHQ2hlY2tvdXQvY2FsbGJhY2svZ2F0aGVyRGV2aWNlTm90aWZpY2F0aW9uL3BheXNvbC8zZHN2Mi8xMDIxODg4In0=</value></entry><entry><key>threeDSv2Token</key><value>368594c6-5302-49a8-858a-1d5809377d4a</value></entry></extraDetails></paymentDetails><redirectionResponse>redirect:https://checkout.stg-eu-west3.epgint.com/EPGCheckout/rest/online/3dsv2/redirect?action=gatherdevice&amp;params=eyJ0aHJlZURTdjJUb2tlbiI6IjM2ODU5NGM2LTUzMDItNDlhOC04NThhLTFkNTgwOTM3N2Q0YSIsInRocmVlRFNNZXRob2RVcmwiOiJodHRwczovL21vY2stZHMuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vcHVibGljL21ldGhvZC1kYXRhLyIsInRocmVlRFNNZXRob2REYXRhIjoiZXlKMGFISmxaVVJUVTJWeWRtVnlWSEpoYm5OSlJDSTZJak0yT0RVNU5HTTJMVFV6TURJdE5EbGhPQzA0TlRoaExURmtOVGd3T1RNM04yUTBZU0lzSUNKMGFISmxaVVJUVFdWMGFHOWtUbTkwYVdacFkyRjBhVzl1VlZKTUlqb2dJbWgwZEhCek9pOHZZMmhsWTJ0dmRYUXVjM1JuTFdWMUxYZGxjM1F6TG1Wd1oybHVkQzVqYjIwdlJWQkhRMmhsWTJ0dmRYUXZZMkZzYkdKaFkyc3ZaMkYwYUdWeVJHVjJhV05sVG05MGFXWnBZMkYwYVc5dUwzQmhlWE52YkM4elpITjJNaTh4TURJeE9EZzRJbjA9IiwiYnJhbmQiOm51bGwsInJlc3VtZUF1dGhlbnRpY2F0aW9uIjoiaHR0cHM6Ly9jaGVja291dC5zdGctZXUtd2VzdDMuZXBnaW50LmNvbS9FUEdDaGVja291dC9yZXR1cm51cmwvZnJpY3Rpb25sZXNzL3BheXNvbC8zZHN2Mi8xMDIxODg4P3RocmVlRFN2MlRva2VuPTM2ODU5NGM2LTUzMDItNDlhOC04NThhLTFkNTgwOTM3N2Q0YSIsInJlbmRlckNhc2hpZXJMb2NhdGlvbiI6Imh0dHBzOi8vZXBnanMtcmVuZGVyY2FzaGllci1zdGcuZWFzeXBheW1lbnRnYXRld2F5LmNvbSIsImNoYWxsZW5nZVdpbmRvd3NTaXplIjoiMDUifQ==</redirectionResponse><service>3DSv2</service><status>REDIRECTED</status><transactionId>1021888</transactionId><respCode><code>8100</code><message>Frictionless requires</message><uuid>5fe4e8ff_50aa_4a4b_a3ef_43ac9ce05967</uuid></respCode><mpi><acsEndProtocolVersion>2.2.0</acsEndProtocolVersion><acsStartProtocolVersion>2.1.0</acsStartProtocolVersion><dsEndProtocolVersion>2.2.0</dsEndProtocolVersion><dsStartProtocolVersion>2.1.0</dsStartProtocolVersion><threeDSMethodURL>https://mock-ds.stg-eu-west3.epgint.com/public/method-data/</threeDSMethodURL></mpi></operation></operations><optionalTransactionParams/><status>SUCCESS</status><workFlowResponse><id>48787</id><name>debit creditcards (TRA)</name><version>0</version></workFlowResponse></response>";

    public static String finalH2HRecurringSuccessNotification = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response operation-size=\"3\"><message>WorkFlow has finished successfully, for transaction Id: 7819704</message><operations><operation sorted-order=\"1\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>46604547</merchantTransactionId><operationType>DEBIT</operationType><paySolTransactionId>a448918c-d6f1-412f-b4ea-44e303a961a2</paySolTransactionId><service>TRA</service><status>SUCCESS</status><transactionId>7819704</transactionId><respCode><code>8203</code><message>Frictionless requires</message><uuid>83373ab4_026e_4fed_a0df_9b990ac0492a</uuid></respCode></operation><operation sorted-order=\"2\"><amount>50</amount><currency>EUR</currency><merchantTransactionId>46604547</merchantTransactionId><message>3dsv2 - processed</message><operationType>DEBIT</operationType><paymentDetails><cardNumberToken>6537275043632227</cardNumberToken><extraDetails/></paymentDetails><service>3DSv2</service><status>SUCCESS3DS</status><transactionId>7819704</transactionId><respCode><code>8000</code><message>Successful authentication</message><uuid>fc0ab0a2_908d_4d6f_8baf_3dac1aa610d6</uuid></respCode><mpi><acsTransID>679eda21-0584-4e83-8363-60cc40760ad7</acsTransID><authMethod>01</authMethod><authTimestamp>202402012058</authTimestamp><authenticationStatus>Y</authenticationStatus><cavv>AJkBB4OBmVFmgYFYFIGZAAAAAAA=</cavv><eci>05</eci><messageVersion>2.2.0</messageVersion><threeDSSessionData>MzY4NTk0YzYtNTMwMi00OWE4LTg1OGEtMWQ1ODA5Mzc3ZDRh</threeDSSessionData><threeDSv2Token>368594c6-5302-49a8-858a-1d5809377d4a</threeDSv2Token></mpi><paymentCode>nsY1</paymentCode><paymentMessage>Authenticated successfully</paymentMessage></operation><operation sorted-order=\"3\"><amount>50.00</amount><currency>EUR</currency><details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 949477 240201215800\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Settle\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"************2227\",\"rfOperationDateTime\":\"01/02/24 21:58:00\",\"rfTerminalOperationNumber\":\"0953\",\"rfAuthNumber\":\"127749\",\"rfTransactionAmountCurrency\":\"50,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"rfReferenceId\":\"0953\",\"posTransactionToken\":\"{\\\"pucIdMsg\\\":\\\"1200\\\",\\\"pucP3ProcessCode\\\":\\\"000000\\\",\\\"pucP4OriginalAmount\\\":\\\"000000005000\\\",\\\"pucP11TransactionNumber\\\":\\\"949477\\\",\\\"pucP12LocalDateTime\\\":\\\"240201215800\\\",\\\"pucP22ServicePointData\\\":\\\"1U00506K3000\\\",\\\"pucP38AuthNumber\\\":\\\"127749\\\",\\\"pucP39ActionCode\\\":\\\"000\\\",\\\"pucP53SecurityControlInfo\\\":\\\"0102000001000000\\\",\\\"pinpadId\\\":\\\"1639\\\",\\\"pinpadAcquirerId\\\":\\\"00000500\\\",\\\"pinpadManufacturer\\\":null,\\\"pinpadModel\\\":null,\\\"pinpadSerialNumber\\\":null,\\\"pinpadSoftwareName\\\":null,\\\"pinpadSoftwareVersion\\\":null,\\\"pinpadKernelEmv\\\":null,\\\"pinpadVccStrip\\\":null,\\\"pinpadVerPup\\\":null,\\\"pinpadPciStage\\\":null,\\\"pinpadVerEmvParams\\\":null,\\\"pinpadEmvType\\\":null,\\\"pinpadCapabilities\\\":null,\\\"pinpadLanguage\\\":null,\\\"transactionType\\\":\\\"O\\\",\\\"transactionContactless\\\":\\\"0\\\",\\\"transactionDcc\\\":\\\"0\\\",\\\"transactionDccComission\\\":null,\\\"transactionDccExchangeRate\\\":null,\\\"transactionDccMarkUp\\\":null,\\\"transactionDccEntity\\\":null,\\\"transactionDccBceExchangeRate\\\":null,\\\"transactionDccBceMarkUp\\\":null,\\\"transactionPanSequenceNumber\\\":null,\\\"transactionTerminalOperationNumber\\\":\\\"0953\\\",\\\"transactionResponseCode\\\":null,\\\"transactionCurrency\\\":\\\"978\\\",\\\"transactionFuc\\\":\\\"355534686\\\",\\\"cardMaskedPan\\\":\\\"************2227\\\",\\\"cardAid\\\":null,\\\"cardDdfName\\\":null,\\\"cardApplicationLabel\\\":null,\\\"cardCypherData\\\":null}\",\"OperationResult\":\"000\",\"cofAdditionalInformation\":\"CS386224485282104\"},\"threeDsProtocolVersion\":\"2.2.0\"}</details><merchantTransactionId>46604547</merchantTransactionId><message>Success 'Settle' operation</message><operationType>DEBIT</operationType><optionalTransactionParams/><paySolTransactionId>355534686 790190 949477 240201215800</paySolTransactionId><paymentDetails><cardHolderName>First name Last name</cardHolderName><cardNumber>490727****2227</cardNumber><cardNumberToken>6537275043632227</cardNumberToken><cardType>VISA/CREDIT</cardType><expDate>0625</expDate><extraDetails><entry><key>cardCategory</key><value>Not Available</value></entry><entry><key>rememberMe</key><value>true</value></entry></extraDetails><issuerBank>SERVIRED MASTERCARD INTERNACIONAL</issuerBank><issuerCountry>ES</issuerCountry></paymentDetails><paymentMethod>19900</paymentMethod><paymentSolution>caixapucpuce</paymentSolution><status>SUCCESS</status><transactionId>7819704</transactionId><respCode><code>0000</code><message>Successful</message><uuid>2c7d9ede_c1dc_4392_b266_e408a1be8929</uuid></respCode><authCode>127749</authCode><mpi><eci>05</eci></mpi><paymentCode>000</paymentCode><paymentMessage>Operaci�n finalizada con �xito</paymentMessage><subscriptionPlan>386224485282104</subscriptionPlan></operation></operations><optionalTransactionParams/><status>SUCCESS</status><workFlowResponse><id>48787</id><name>debit creditcards (TRA)</name><version>0</version></workFlowResponse></response>";

    public  static  String h2HRecurringSubsequentResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<response operation-size=\"2\">\n" +
            "    <message>WorkFlow has finished successfully, for transaction Id: 7831690</message>\n" +
            "    <operations>\n" +
            "        <operation sorted-order=\"1\">\n" +
            "            <amount>30</amount>\n" +
            "            <currency>EUR</currency>\n" +
            "            <merchantTransactionId>11906879</merchantTransactionId>\n" +
            "            <operationType>DEBIT</operationType>\n" +
            "            <paySolTransactionId>a506624d-edf8-41f2-aa84-249d3ac870a0</paySolTransactionId>\n" +
            "            <service>TRA</service>\n" +
            "            <status>SUCCESS</status>\n" +
            "            <transactionId>7831690</transactionId>\n" +
            "            <respCode>\n" +
            "                <code>8203</code>\n" +
            "                <message>Frictionless requires</message>\n" +
            "                <uuid>51f2778f_a5cd_497b_9b43_e0542ee3352a</uuid>\n" +
            "            </respCode>\n" +
            "        </operation>\n" +
            "        <operation sorted-order=\"2\">\n" +
            "            <amount>30</amount>\n" +
            "            <currency>EUR</currency>\n" +
            "            <merchantTransactionId>11906879</merchantTransactionId>\n" +
            "            <message>Starting 3DSecure 2.0 process.</message>\n" +
            "            <operationType>DEBIT</operationType>\n" +
            "            <optionalTransactionParams/>\n" +
            "            <originalTransactionId>7831690</originalTransactionId>\n" +
            "            <paymentDetails>\n" +
            "                <cardHolderName>First name Last name</cardHolderName>\n" +
            "                <extraDetails>\n" +
            "                    <entry>\n" +
            "                        <key>threeDSMethodData</key>\n" +
            "                        <value>eyJ0aHJlZURTU2VydmVyVHJhbnNJRCI6ImYxMzliMGZkLTUzMTQtNGU4My04YzdkLWQ4OTg2MTBjYzhkMyIsICJ0aHJlZURTTWV0aG9kTm90aWZpY2F0aW9uVVJMIjogImh0dHBzOi8vY2hlY2tvdXQuc3RnLWV1LXdlc3QxLmVwZ2ludC5jb20vRVBHQ2hlY2tvdXQvY2FsbGJhY2svZ2F0aGVyRGV2aWNlTm90aWZpY2F0aW9uL3BheXNvbC8zZHN2Mi8xMDIyODc0In0=</value>\n" +
            "                    </entry>\n" +
            "                    <entry>\n" +
            "                        <key>threeDSv2Token</key>\n" +
            "                        <value>f139b0fd-5314-4e83-8c7d-d898610cc8d3</value>\n" +
            "                    </entry>\n" +
            "                </extraDetails>\n" +
            "            </paymentDetails>\n" +
            "            <redirectionResponse>redirect:https://checkout.stg-eu-west1.epgint.com/EPGCheckout/rest/online/3dsv2/redirect?action=gatherdevice&amp;params=eyJ0aHJlZURTdjJUb2tlbiI6ImYxMzliMGZkLTUzMTQtNGU4My04YzdkLWQ4OTg2MTBjYzhkMyIsInRocmVlRFNNZXRob2RVcmwiOiJodHRwczovL21vY2stZHMuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vcHVibGljL21ldGhvZC1kYXRhLyIsInRocmVlRFNNZXRob2REYXRhIjoiZXlKMGFISmxaVVJUVTJWeWRtVnlWSEpoYm5OSlJDSTZJbVl4TXpsaU1HWmtMVFV6TVRRdE5HVTRNeTA0WXpka0xXUTRPVGcyTVRCall6aGtNeUlzSUNKMGFISmxaVVJUVFdWMGFHOWtUbTkwYVdacFkyRjBhVzl1VlZKTUlqb2dJbWgwZEhCek9pOHZZMmhsWTJ0dmRYUXVjM1JuTFdWMUxYZGxjM1F4TG1Wd1oybHVkQzVqYjIwdlJWQkhRMmhsWTJ0dmRYUXZZMkZzYkdKaFkyc3ZaMkYwYUdWeVJHVjJhV05sVG05MGFXWnBZMkYwYVc5dUwzQmhlWE52YkM4elpITjJNaTh4TURJeU9EYzBJbjA9IiwiYnJhbmQiOiJWSVNBIiwicmVzdW1lQXV0aGVudGljYXRpb24iOiJodHRwczovL2NoZWNrb3V0LnN0Zy1ldS13ZXN0MS5lcGdpbnQuY29tL0VQR0NoZWNrb3V0L3JldHVybnVybC9mcmljdGlvbmxlc3MvcGF5c29sLzNkc3YyLzEwMjI4NzQ/dGhyZWVEU3YyVG9rZW49ZjEzOWIwZmQtNTMxNC00ZTgzLThjN2QtZDg5ODYxMGNjOGQzIiwicmVuZGVyQ2FzaGllckxvY2F0aW9uIjoiaHR0cHM6Ly9lcGdqcy1yZW5kZXJjYXNoaWVyLXN0Zy5lYXN5cGF5bWVudGdhdGV3YXkuY29tIiwiY2hhbGxlbmdlV2luZG93c1NpemUiOiIwNSJ9</redirectionResponse>\n" +
            "            <service>3DSv2</service>\n" +
            "            <status>REDIRECTED</status>\n" +
            "            <transactionId>1022874</transactionId>\n" +
            "            <respCode>\n" +
            "                <code>8100</code>\n" +
            "                <message>Frictionless requires</message>\n" +
            "                <uuid>68ec8bb4_787b_4e1b_a2c5_1f8a942814d1</uuid>\n" +
            "            </respCode>\n" +
            "            <mpi>\n" +
            "                <acsEndProtocolVersion>2.2.0</acsEndProtocolVersion>\n" +
            "                <acsStartProtocolVersion>2.1.0</acsStartProtocolVersion>\n" +
            "                <dsEndProtocolVersion>2.2.0</dsEndProtocolVersion>\n" +
            "                <dsStartProtocolVersion>2.1.0</dsStartProtocolVersion>\n" +
            "                <threeDSMethodURL>https://mock-ds.stg-eu-west3.epgint.com/public/method-data/</threeDSMethodURL>\n" +
            "            </mpi>\n" +
            "        </operation>\n" +
            "    </operations>\n" +
            "    <optionalTransactionParams/>\n" +
            "    <status>SUCCESS</status>\n" +
            "    <workFlowResponse>\n" +
            "        <id>48787</id>\n" +
            "        <name>debit creditcards (TRA)</name>\n" +
            "        <version>0</version>\n" +
            "    </workFlowResponse>\n" +
            "</response>";

    public static String h2HPreAuthorizationResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<response operation-size=\"2\">\n" +
            "    <message>WorkFlow has finished successfully, for transaction Id: 7831718</message>\n" +
            "    <operations>\n" +
            "        <operation sorted-order=\"1\">\n" +
            "            <amount>30</amount>\n" +
            "            <currency>EUR</currency>\n" +
            "            <merchantTransactionId>07665953</merchantTransactionId>\n" +
            "            <operationType>DEBIT</operationType>\n" +
            "            <paySolTransactionId>dc941b0c-a05c-46cf-b3b1-af71ba4fd439</paySolTransactionId>\n" +
            "            <service>TRA</service>\n" +
            "            <status>SUCCESS</status>\n" +
            "            <transactionId>7831718</transactionId>\n" +
            "            <respCode>\n" +
            "                <code>8203</code>\n" +
            "                <message>Frictionless requires</message>\n" +
            "                <uuid>6bd07c12_84e4_4ed0_a904_daff64105dd0</uuid>\n" +
            "            </respCode>\n" +
            "        </operation>\n" +
            "        <operation sorted-order=\"2\">\n" +
            "            <amount>30</amount>\n" +
            "            <currency>EUR</currency>\n" +
            "            <merchantTransactionId>07665953</merchantTransactionId>\n" +
            "            <message>Starting 3DSecure 2.0 process.</message>\n" +
            "            <operationType>DEBIT</operationType>\n" +
            "            <optionalTransactionParams/>\n" +
            "            <originalTransactionId>7831718</originalTransactionId>\n" +
            "            <paymentDetails>\n" +
            "                <cardHolderName>First name Last name</cardHolderName>\n" +
            "                <extraDetails>\n" +
            "                    <entry>\n" +
            "                        <key>threeDSMethodData</key>\n" +
            "                        <value>eyJ0aHJlZURTU2VydmVyVHJhbnNJRCI6IjQ0ZTQxNTM4LTQ1NTItNGY0Ni1hYzQ0LTYwMTk2ZGUzYTEyOSIsICJ0aHJlZURTTWV0aG9kTm90aWZpY2F0aW9uVVJMIjogImh0dHBzOi8vY2hlY2tvdXQuc3RnLWV1LXdlc3QxLmVwZ2ludC5jb20vRVBHQ2hlY2tvdXQvY2FsbGJhY2svZ2F0aGVyRGV2aWNlTm90aWZpY2F0aW9uL3BheXNvbC8zZHN2Mi8xMDIyODc4In0=</value>\n" +
            "                    </entry>\n" +
            "                    <entry>\n" +
            "                        <key>threeDSv2Token</key>\n" +
            "                        <value>44e41538-4552-4f46-ac44-60196de3a129</value>\n" +
            "                    </entry>\n" +
            "                </extraDetails>\n" +
            "            </paymentDetails>\n" +
            "            <redirectionResponse>redirect:https://checkout.stg-eu-west1.epgint.com/EPGCheckout/rest/online/3dsv2/redirect?action=gatherdevice&amp;params=eyJ0aHJlZURTdjJUb2tlbiI6IjQ0ZTQxNTM4LTQ1NTItNGY0Ni1hYzQ0LTYwMTk2ZGUzYTEyOSIsInRocmVlRFNNZXRob2RVcmwiOiJodHRwczovL21vY2stZHMuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vcHVibGljL21ldGhvZC1kYXRhLyIsInRocmVlRFNNZXRob2REYXRhIjoiZXlKMGFISmxaVVJUVTJWeWRtVnlWSEpoYm5OSlJDSTZJalEwWlRReE5UTTRMVFExTlRJdE5HWTBOaTFoWXpRMExUWXdNVGsyWkdVellURXlPU0lzSUNKMGFISmxaVVJUVFdWMGFHOWtUbTkwYVdacFkyRjBhVzl1VlZKTUlqb2dJbWgwZEhCek9pOHZZMmhsWTJ0dmRYUXVjM1JuTFdWMUxYZGxjM1F4TG1Wd1oybHVkQzVqYjIwdlJWQkhRMmhsWTJ0dmRYUXZZMkZzYkdKaFkyc3ZaMkYwYUdWeVJHVjJhV05sVG05MGFXWnBZMkYwYVc5dUwzQmhlWE52YkM4elpITjJNaTh4TURJeU9EYzRJbjA9IiwiYnJhbmQiOiJWSVNBIiwicmVzdW1lQXV0aGVudGljYXRpb24iOiJodHRwczovL2NoZWNrb3V0LnN0Zy1ldS13ZXN0MS5lcGdpbnQuY29tL0VQR0NoZWNrb3V0L3JldHVybnVybC9mcmljdGlvbmxlc3MvcGF5c29sLzNkc3YyLzEwMjI4Nzg/dGhyZWVEU3YyVG9rZW49NDRlNDE1MzgtNDU1Mi00ZjQ2LWFjNDQtNjAxOTZkZTNhMTI5IiwicmVuZGVyQ2FzaGllckxvY2F0aW9uIjoiaHR0cHM6Ly9lcGdqcy1yZW5kZXJjYXNoaWVyLXN0Zy5lYXN5cGF5bWVudGdhdGV3YXkuY29tIiwiY2hhbGxlbmdlV2luZG93c1NpemUiOiIwNSJ9</redirectionResponse>\n" +
            "            <service>3DSv2</service>\n" +
            "            <status>REDIRECTED</status>\n" +
            "            <transactionId>1022878</transactionId>\n" +
            "            <respCode>\n" +
            "                <code>8100</code>\n" +
            "                <message>Frictionless requires</message>\n" +
            "                <uuid>4e94ac09_877d_41af_ae8d_95c8e26c91bf</uuid>\n" +
            "            </respCode>\n" +
            "            <mpi>\n" +
            "                <acsEndProtocolVersion>2.2.0</acsEndProtocolVersion>\n" +
            "                <acsStartProtocolVersion>2.1.0</acsStartProtocolVersion>\n" +
            "                <dsEndProtocolVersion>2.2.0</dsEndProtocolVersion>\n" +
            "                <dsStartProtocolVersion>2.1.0</dsStartProtocolVersion>\n" +
            "                <threeDSMethodURL>https://mock-ds.stg-eu-west3.epgint.com/public/method-data/</threeDSMethodURL>\n" +
            "            </mpi>\n" +
            "        </operation>\n" +
            "    </operations>\n" +
            "    <optionalTransactionParams/>\n" +
            "    <status>SUCCESS</status>\n" +
            "    <workFlowResponse>\n" +
            "        <id>48787</id>\n" +
            "        <name>debit creditcards (TRA)</name>\n" +
            "        <version>0</version>\n" +
            "    </workFlowResponse>\n" +
            "</response>";
    public static String CaptureResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<response operation-size=\"1\">\n" +
            "    <operations>\n" +
            "        <operation sorted-order=\"1\">\n" +
            "            <amount>30.0</amount>\n" +
            "            <currency>EUR</currency>\n" +
            "            <details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 968336 240212101300\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Capture\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"************2227\",\"rfOperationDateTime\":\"12/02/24 10:13:00\",\"rfTerminalOperationNumber\":\"1094\",\"rfAuthNumber\":\"776097\",\"rfTransactionAmountCurrency\":\"30,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"rfReferenceId\":\"1094\",\"posTransactionToken\":\"{\\\"pucIdMsg\\\":\\\"1220\\\",\\\"pucP3ProcessCode\\\":\\\"000000\\\",\\\"pucP4OriginalAmount\\\":\\\"000000003000\\\",\\\"pucP11TransactionNumber\\\":\\\"968336\\\",\\\"pucP12LocalDateTime\\\":\\\"240212101300\\\",\\\"pucP22ServicePointData\\\":\\\"1U00506K3000\\\",\\\"pucP38AuthNumber\\\":\\\"776097\\\",\\\"pucP39ActionCode\\\":\\\"000\\\",\\\"pucP53SecurityControlInfo\\\":\\\"0102000001000000\\\",\\\"pinpadId\\\":\\\"1639\\\",\\\"pinpadAcquirerId\\\":\\\"00000500\\\",\\\"pinpadManufacturer\\\":null,\\\"pinpadModel\\\":null,\\\"pinpadSerialNumber\\\":null,\\\"pinpadSoftwareName\\\":null,\\\"pinpadSoftwareVersion\\\":null,\\\"pinpadKernelEmv\\\":\\\"1\\\",\\\"pinpadVccStrip\\\":null,\\\"pinpadVerPup\\\":null,\\\"pinpadPciStage\\\":null,\\\"pinpadVerEmvParams\\\":null,\\\"pinpadEmvType\\\":null,\\\"pinpadCapabilities\\\":null,\\\"pinpadLanguage\\\":null,\\\"transactionType\\\":\\\"O\\\",\\\"transactionContactless\\\":\\\"0\\\",\\\"transactionDcc\\\":\\\"0\\\",\\\"transactionDccComission\\\":null,\\\"transactionDccExchangeRate\\\":null,\\\"transactionDccMarkUp\\\":null,\\\"transactionDccEntity\\\":null,\\\"transactionDccBceExchangeRate\\\":null,\\\"transactionDccBceMarkUp\\\":null,\\\"transactionPanSequenceNumber\\\":null,\\\"transactionTerminalOperationNumber\\\":\\\"1094\\\",\\\"transactionResponseCode\\\":null,\\\"transactionCurrency\\\":\\\"978\\\",\\\"transactionFuc\\\":\\\"355534686\\\",\\\"cardMaskedPan\\\":\\\"************2227\\\",\\\"cardAid\\\":null,\\\"cardDdfName\\\":null,\\\"cardApplicationLabel\\\":null,\\\"cardCypherData\\\":null}\",\"OperationResult\":\"000\"},\"threeDsProtocolVersion\":\"2.2.0\"}</details>\n" +
            "            <merchantTransactionId>73741844</merchantTransactionId>\n" +
            "            <message>Success 'Capture' operation</message>\n" +
            "            <operationType>DEBIT</operationType>\n" +
            "            <optionalTransactionParams/>\n" +
            "            <originalTransactionId>7831728</originalTransactionId>\n" +
            "            <paySolTransactionId>355534686 790190 968336 240212101300</paySolTransactionId>\n" +
            "            <paymentDetails>\n" +
            "                <cardHolderName>First name Last name</cardHolderName>\n" +
            "                <cardNumber>490727****2227</cardNumber>\n" +
            "                <cardNumberToken>6537275043632227</cardNumberToken>\n" +
            "                <cardType>VISA/CREDIT</cardType>\n" +
            "                <expDate>0625</expDate>\n" +
            "                <extraDetails>\n" +
            "                    <entry>\n" +
            "                        <key>cardCategory</key>\n" +
            "                        <value>Not Available</value>\n" +
            "                    </entry>\n" +
            "                    <entry>\n" +
            "                        <key>rememberMe</key>\n" +
            "                        <value>true</value>\n" +
            "                    </entry>\n" +
            "                </extraDetails>\n" +
            "                <issuerBank>SERVIRED MASTERCARD INTERNACIONAL</issuerBank>\n" +
            "                <issuerCountry>ES</issuerCountry>\n" +
            "            </paymentDetails>\n" +
            "            <paymentSolution>caixapucpuce</paymentSolution>\n" +
            "            <status>SUCCESS</status>\n" +
            "            <transactionId>7831728</transactionId>\n" +
            "            <respCode>\n" +
            "                <code>0000</code>\n" +
            "                <message>Successful</message>\n" +
            "                <uuid>287a9826_8fdf_4668_a9a4_5470d394d9fd</uuid>\n" +
            "            </respCode>\n" +
            "            <authCode>776097</authCode>\n" +
            "            <mpi>\n" +
            "                <eci>05</eci>\n" +
            "            </mpi>\n" +
            "            <paymentCode>000</paymentCode>\n" +
            "            <paymentMessage>Operación finalizada con éxito</paymentMessage>\n" +
            "        </operation>\n" +
            "    </operations>\n" +
            "    <optionalTransactionParams/>\n" +
            "</response>";

    public static String VoidResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<response operation-size=\"1\">\n" +
            "    <operations>\n" +
            "        <operation sorted-order=\"1\">\n" +
            "            <amount>30.0</amount>\n" +
            "            <currency>EUR</currency>\n" +
            "            <details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 968411 240212110838\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Void\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"\",\"rfOperationDateTime\":\"12/02/24 11:08:38\",\"rfAuthNumber\":\"419610\",\"rfTransactionAmountCurrency\":\"30,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"OperationResult\":\"000\"}}</details>\n" +
            "            <merchantTransactionId>38559715</merchantTransactionId>\n" +
            "            <message>Success 'Void' operation</message>\n" +
            "            <operationType>VOID</operationType>\n" +
            "            <optionalTransactionParams/>\n" +
            "            <originalTransactionId>7831800</originalTransactionId>\n" +
            "            <paySolTransactionId>355534686 790190 968411 240212110838</paySolTransactionId>\n" +
            "            <paymentDetails>\n" +
            "                <cardHolderName>First name Last name</cardHolderName>\n" +
            "                <cardNumber>490727****2227</cardNumber>\n" +
            "                <cardNumberToken>6537275043632227</cardNumberToken>\n" +
            "                <cardType>VISA/CREDIT</cardType>\n" +
            "                <expDate>0625</expDate>\n" +
            "                <extraDetails>\n" +
            "                    <entry>\n" +
            "                        <key>cardCategory</key>\n" +
            "                        <value>Not Available</value>\n" +
            "                    </entry>\n" +
            "                    <entry>\n" +
            "                        <key>rememberMe</key>\n" +
            "                        <value>true</value>\n" +
            "                    </entry>\n" +
            "                </extraDetails>\n" +
            "                <issuerBank>SERVIRED MASTERCARD INTERNACIONAL</issuerBank>\n" +
            "                <issuerCountry>ES</issuerCountry>\n" +
            "            </paymentDetails>\n" +
            "            <paymentSolution>caixapucpuce</paymentSolution>\n" +
            "            <status>SUCCESS</status>\n" +
            "            <transactionId>7831806</transactionId>\n" +
            "            <respCode>\n" +
            "                <code>0000</code>\n" +
            "                <message>Successful</message>\n" +
            "                <uuid>c9d258dc_2902_4dff_9b1d_6ada75d3748d</uuid>\n" +
            "            </respCode>\n" +
            "            <authCode>419610</authCode>\n" +
            "            <paymentCode>000</paymentCode>\n" +
            "            <paymentMessage>Operación finalizada con éxito</paymentMessage>\n" +
            "        </operation>\n" +
            "    </operations>\n" +
            "</response>";

    public static String RefundResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<response operation-size=\"1\">\n" +
            "    <operations>\n" +
            "        <operation sorted-order=\"1\">\n" +
            "            <amount>30.0</amount>\n" +
            "            <currency>EUR</currency>\n" +
            "            <details>{\"resultCode\":\"00000\",\"resultDescription\":\"OK\",\"values\":{\"rfTransactionCurrency\":\"EUR\",\"rfRTS\":\"355534686 790190 968427 240212111936\",\"rfContactlessLogo\":\"false\",\"rfOperationType\":\"Refund\",\"rfAuthMode\":\"On\",\"rfDataEntryMode\":\"Manual\",\"rfCardHolderVerificationMode\":\"No\",\"rfFuc\":\"355534686\",\"rfTerminalID\":\"00000500\",\"rfProcessor\":\"Redsys\",\"rfMerchantCity\":\"BARCELONA\",\"rfMerchantPostalCode\":\"08014\",\"rfMerchantAddress\":\"GRAN VIA DE LES CORTS CATALANE, 159 PLANTA 7\",\"rfMaskedPan\":\"************2227\",\"rfOperationDateTime\":\"12/02/24 11:19:36\",\"rfTerminalOperationNumber\":\"1101\",\"rfAuthNumber\":\"349825\",\"rfTransactionAmountCurrency\":\"30,00 EUR\",\"rfProcessorMessage\":\"\",\"rfPrintSignatureBox\":\"false\",\"rfCardPresent\":\"true\",\"rfReferenceId\":\"1101\",\"posTransactionToken\":\"{\\\"pucIdMsg\\\":\\\"1200\\\",\\\"pucP3ProcessCode\\\":\\\"200000\\\",\\\"pucP4OriginalAmount\\\":\\\"000000003000\\\",\\\"pucP11TransactionNumber\\\":\\\"968427\\\",\\\"pucP12LocalDateTime\\\":\\\"240212111936\\\",\\\"pucP22ServicePointData\\\":\\\"100050600000\\\",\\\"pucP38AuthNumber\\\":\\\"349825\\\",\\\"pucP39ActionCode\\\":\\\"000\\\",\\\"pucP53SecurityControlInfo\\\":\\\"0102000001000000\\\",\\\"pinpadId\\\":\\\"1639\\\",\\\"pinpadAcquirerId\\\":\\\"00000500\\\",\\\"pinpadManufacturer\\\":null,\\\"pinpadModel\\\":null,\\\"pinpadSerialNumber\\\":null,\\\"pinpadSoftwareName\\\":null,\\\"pinpadSoftwareVersion\\\":null,\\\"pinpadKernelEmv\\\":null,\\\"pinpadVccStrip\\\":null,\\\"pinpadVerPup\\\":null,\\\"pinpadPciStage\\\":null,\\\"pinpadVerEmvParams\\\":null,\\\"pinpadEmvType\\\":null,\\\"pinpadCapabilities\\\":null,\\\"pinpadLanguage\\\":null,\\\"transactionType\\\":\\\"O\\\",\\\"transactionContactless\\\":\\\"0\\\",\\\"transactionDcc\\\":\\\"0\\\",\\\"transactionDccComission\\\":null,\\\"transactionDccExchangeRate\\\":null,\\\"transactionDccMarkUp\\\":null,\\\"transactionDccEntity\\\":null,\\\"transactionDccBceExchangeRate\\\":null,\\\"transactionDccBceMarkUp\\\":null,\\\"transactionPanSequenceNumber\\\":null,\\\"transactionTerminalOperationNumber\\\":\\\"1101\\\",\\\"transactionResponseCode\\\":null,\\\"transactionCurrency\\\":\\\"978\\\",\\\"transactionFuc\\\":\\\"355534686\\\",\\\"cardMaskedPan\\\":\\\"************2227\\\",\\\"cardAid\\\":null,\\\"cardDdfName\\\":null,\\\"cardApplicationLabel\\\":null,\\\"cardCypherData\\\":null}\",\"OperationResult\":\"000\"}}</details>\n" +
            "            <merchantTransactionId>74839704</merchantTransactionId>\n" +
            "            <message>Success 'Refund' operation</message>\n" +
            "            <operationType>REFUND</operationType>\n" +
            "            <optionalTransactionParams/>\n" +
            "            <originalTransactionId>7831830</originalTransactionId>\n" +
            "            <paySolTransactionId>355534686 790190 968427 240212111936</paySolTransactionId>\n" +
            "            <paymentDetails>\n" +
            "                <cardHolderName>First name Last name</cardHolderName>\n" +
            "                <cardNumber>490727****2227</cardNumber>\n" +
            "                <cardNumberToken>6537275043632227</cardNumberToken>\n" +
            "                <cardType>VISA/CREDIT</cardType>\n" +
            "                <expDate>0625</expDate>\n" +
            "                <extraDetails>\n" +
            "                    <entry>\n" +
            "                        <key>cardCategory</key>\n" +
            "                        <value>Not Available</value>\n" +
            "                    </entry>\n" +
            "                    <entry>\n" +
            "                        <key>rememberMe</key>\n" +
            "                        <value>true</value>\n" +
            "                    </entry>\n" +
            "                </extraDetails>\n" +
            "                <issuerBank>SERVIRED MASTERCARD INTERNACIONAL</issuerBank>\n" +
            "                <issuerCountry>ES</issuerCountry>\n" +
            "            </paymentDetails>\n" +
            "            <paymentSolution>caixapucpuce</paymentSolution>\n" +
            "            <remainingAmount>0.0</remainingAmount>\n" +
            "            <status>SUCCESS</status>\n" +
            "            <transactionId>7831836</transactionId>\n" +
            "            <respCode>\n" +
            "                <code>0000</code>\n" +
            "                <message>Successful</message>\n" +
            "                <uuid>f5ca8414_7495_4532_ad3b_46fb2616fd65</uuid>\n" +
            "            </respCode>\n" +
            "            <authCode>349825</authCode>\n" +
            "            <paymentCode>000</paymentCode>\n" +
            "            <paymentMessage>Operación finalizada con éxito</paymentMessage>\n" +
            "        </operation>\n" +
            "    </operations>\n" +
            "    <status>SUCCESS</status>\n" +
            "</response>";

    public static String JsQuixItemsResponse = "{\n" +
            "    \"response\": {\n" +
            "        \"workFlowResponse\": {\n" +
            "            \"id\": 51157,\n" +
            "            \"name\": \"BNPL\",\n" +
            "            \"version\": 0\n" +
            "        },\n" +
            "        \"status\": \"SUCCESS\",\n" +
            "        \"message\": \"WorkFlow has finished successfully, for transaction Id: 7833008\",\n" +
            "        \"optionalTransactionParams\": {},\n" +
            "        \"operationSize\": 1,\n" +
            "        \"operationsArray\": [\n" +
            "            {\n" +
            "                \"type\": \"operation\",\n" +
            "                \"sortedOrder\": 1,\n" +
            "                \"status\": \"REDIRECTED\",\n" +
            "                \"message\": \"Transaction was redirected.\",\n" +
            "                \"payFrexTransactionId\": 7833008,\n" +
            "                \"merchantTransactionId\": \"789789798\",\n" +
            "                \"paySolTransactionId\": null,\n" +
            "                \"operationType\": \"DEBIT\",\n" +
            "                \"currency\": \"EUR\",\n" +
            "                \"amount\": \"200\",\n" +
            "                \"paymentSolution\": \"quix\",\n" +
            "                \"paymentMethod\": null,\n" +
            "                \"service\": null,\n" +
            "                \"redirectionResponse\": null,\n" +
            "                \"redirectionParameters\": null,\n" +
            "                \"redirectionParam\": null,\n" +
            "                \"details\": \"{\\\"orderId\\\":\\\"af24252b-e8c9-4fb2-9da2-7a476b2d8cd4\\\",\\\"authToken\\\":\\\"62WBmZM44eDS2gZfVbgvEg5Cydea7IcY\\\"}\",\n" +
            "                \"originalPayFrexTransactionId\": null,\n" +
            "                \"originalCurrency\": null,\n" +
            "                \"originalAmount\": null,\n" +
            "                \"remainingAmount\": null,\n" +
            "                \"optionalTransactionParams\": {},\n" +
            "                \"checkoutURL\": \"NO-APPLY\",\n" +
            "                \"fee\": null,\n" +
            "                \"checkFields\": null,\n" +
            "                \"statusType3DS\": null,\n" +
            "                \"paymentDetails\": {\n" +
            "                    \"issuerCountry\": null,\n" +
            "                    \"issuerBank\": null,\n" +
            "                    \"cardHolderName\": null,\n" +
            "                    \"cardType\": null,\n" +
            "                    \"cardNumber\": null,\n" +
            "                    \"account\": null,\n" +
            "                    \"cardNumberToken\": null,\n" +
            "                    \"expDate\": null,\n" +
            "                    \"extraDetails\": {\n" +
            "                        \"nemuruTxnId\": \"7833008\",\n" +
            "                        \"nemuruCartHash\": \"af24252b-e8c9-4fb2-9da2-7a476b2d8cd4\",\n" +
            "                        \"nemuruAuthToken\": \"62WBmZM44eDS2gZfVbgvEg5Cydea7IcY\",\n" +
            "                        \"nemuruDisableFormEdition\": \"false\",\n" +
            "                        \"status\": \"REDIRECTED\"\n" +
            "                    }\n" +
            "                },\n" +
            "                \"extraInfo\": {},\n" +
            "                \"paymentResponse\": {\n" +
            "                    \"status\": \"REDIRECTED\",\n" +
            "                    \"message\": \"Transaction was redirected.\",\n" +
            "                    \"paySolTransactionId\": null,\n" +
            "                    \"details\": \"{\\\"orderId\\\":\\\"af24252b-e8c9-4fb2-9da2-7a476b2d8cd4\\\",\\\"authToken\\\":\\\"62WBmZM44eDS2gZfVbgvEg5Cydea7IcY\\\"}\"\n" +
            "                },\n" +
            "                \"authCode\": null,\n" +
            "                \"paymentMessage\": null,\n" +
            "                \"paymentCode\": null,\n" +
            "                \"mpi\": null,\n" +
            "                \"subscriptionPlan\": null,\n" +
            "                \"rad\": null,\n" +
            "                \"respCode\": {\n" +
            "                    \"type\": \"respCode\",\n" +
            "                    \"code\": \"0000\",\n" +
            "                    \"message\": \"Successful\",\n" +
            "                    \"uuid\": \"2e8fd32b_bf6f_4918_875e_01e52d2174b5\"\n" +
            "                }\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    public static String AuthResponse = "{\"authToken\":\"c5f2746c-d4ec-44fa-b7a7-61df2378296d\"}";

    public static String ChargeResponse = "{\n" +
            "    \"response\": {\n" +
            "        \"workFlowResponse\": {\n" +
            "            \"id\": 48787,\n" +
            "            \"name\": \"debit creditcards (TRA)\",\n" +
            "            \"version\": 0\n" +
            "        },\n" +
            "        \"status\": \"SUCCESS\",\n" +
            "        \"message\": \"WorkFlow has finished successfully, for transaction Id: 7833442\",\n" +
            "        \"optionalTransactionParams\": {},\n" +
            "        \"operationSize\": 2,\n" +
            "        \"operationsArray\": [\n" +
            "            {\n" +
            "                \"type\": \"operation\",\n" +
            "                \"sortedOrder\": 1,\n" +
            "                \"status\": \"SUCCESS\",\n" +
            "                \"message\": null,\n" +
            "                \"payFrexTransactionId\": 7833442,\n" +
            "                \"merchantTransactionId\": \"40527253\",\n" +
            "                \"paySolTransactionId\": \"169673b9-9ab0-43df-bc5a-7cb02df7b9cb\",\n" +
            "                \"operationType\": \"DEBIT\",\n" +
            "                \"currency\": \"EUR\",\n" +
            "                \"amount\": \"30\",\n" +
            "                \"paymentSolution\": null,\n" +
            "                \"paymentMethod\": null,\n" +
            "                \"service\": \"TRA\",\n" +
            "                \"redirectionResponse\": null,\n" +
            "                \"redirectionParameters\": null,\n" +
            "                \"redirectionParam\": null,\n" +
            "                \"details\": null,\n" +
            "                \"originalPayFrexTransactionId\": null,\n" +
            "                \"originalCurrency\": null,\n" +
            "                \"originalAmount\": null,\n" +
            "                \"remainingAmount\": null,\n" +
            "                \"optionalTransactionParams\": null,\n" +
            "                \"checkoutURL\": null,\n" +
            "                \"fee\": null,\n" +
            "                \"checkFields\": null,\n" +
            "                \"statusType3DS\": null,\n" +
            "                \"paymentDetails\": null,\n" +
            "                \"extraInfo\": {},\n" +
            "                \"paymentResponse\": null,\n" +
            "                \"authCode\": null,\n" +
            "                \"paymentMessage\": null,\n" +
            "                \"paymentCode\": null,\n" +
            "                \"mpi\": null,\n" +
            "                \"subscriptionPlan\": null,\n" +
            "                \"rad\": null,\n" +
            "                \"respCode\": {\n" +
            "                    \"type\": \"respCode\",\n" +
            "                    \"code\": \"0000\",\n" +
            "                    \"message\": \"Successful\",\n" +
            "                    \"uuid\": \"2e8fd32b_bf6f_4918_875e_01e52d2174b5\"\n" +
            "                }\n" +
            "            },\n" +
            "            {\n" +
            "                \"type\": \"operation\",\n" +
            "                \"sortedOrder\": 2,\n" +
            "                \"status\": \"REDIRECTED\",\n" +
            "                \"message\": \"Starting 3DSecure 2.0 process.\",\n" +
            "                \"payFrexTransactionId\": 1023026,\n" +
            "                \"merchantTransactionId\": \"40527253\",\n" +
            "                \"paySolTransactionId\": null,\n" +
            "                \"operationType\": \"DEBIT\",\n" +
            "                \"currency\": \"EUR\",\n" +
            "                \"amount\": \"30\",\n" +
            "                \"paymentSolution\": null,\n" +
            "                \"paymentMethod\": null,\n" +
            "                \"service\": \"3DSv2\",\n" +
            "                \"redirectionResponse\": \"redirect:https://checkout.stg-eu-west3.epgint.com/EPGCheckout/rest/online/3dsv2/redirect?action=gatherdevice&params=eyJ0aHJlZURTdjJUb2tlbiI6ImNjNWM1ODJjLWJiOTgtNGIyNS04NjA5LTZkZDI2YzM3MDgwNSIsInRocmVlRFNNZXRob2RVcmwiOiJodHRwczovL21vY2stZHMuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vcHVibGljL21ldGhvZC1kYXRhLyIsInRocmVlRFNNZXRob2REYXRhIjoiZXlKMGFISmxaVVJUVTJWeWRtVnlWSEpoYm5OSlJDSTZJbU5qTldNMU9ESmpMV0ppT1RndE5HSXlOUzA0TmpBNUxUWmtaREkyWXpNM01EZ3dOU0lzSUNKMGFISmxaVVJUVFdWMGFHOWtUbTkwYVdacFkyRjBhVzl1VlZKTUlqb2dJbWgwZEhCek9pOHZZMmhsWTJ0dmRYUXVjM1JuTFdWMUxYZGxjM1F6TG1Wd1oybHVkQzVqYjIwdlJWQkhRMmhsWTJ0dmRYUXZZMkZzYkdKaFkyc3ZaMkYwYUdWeVJHVjJhV05sVG05MGFXWnBZMkYwYVc5dUwzQmhlWE52YkM4elpITjJNaTh4TURJek1ESTJJbjA9IiwiYnJhbmQiOiJWSVNBIiwicmVzdW1lQXV0aGVudGljYXRpb24iOiJodHRwczovL2NoZWNrb3V0LnN0Zy1ldS13ZXN0My5lcGdpbnQuY29tL0VQR0NoZWNrb3V0L3JldHVybnVybC9mcmljdGlvbmxlc3MvcGF5c29sLzNkc3YyLzEwMjMwMjY/dGhyZWVEU3YyVG9rZW49Y2M1YzU4MmMtYmI5OC00YjI1LTg2MDktNmRkMjZjMzcwODA1IiwicmVuZGVyQ2FzaGllckxvY2F0aW9uIjoiaHR0cHM6Ly9lcGdqcy1yZW5kZXJjYXNoaWVyLXN0Zy5lYXN5cGF5bWVudGdhdGV3YXkuY29tIiwiY2hhbGxlbmdlV2luZG93c1NpemUiOiIwNSJ9\",\n" +
            "                \"redirectionParameters\": null,\n" +
            "                \"redirectionParam\": null,\n" +
            "                \"details\": null,\n" +
            "                \"originalPayFrexTransactionId\": 7833442,\n" +
            "                \"originalCurrency\": null,\n" +
            "                \"originalAmount\": null,\n" +
            "                \"remainingAmount\": null,\n" +
            "                \"optionalTransactionParams\": {},\n" +
            "                \"checkoutURL\": null,\n" +
            "                \"fee\": null,\n" +
            "                \"checkFields\": null,\n" +
            "                \"statusType3DS\": null,\n" +
            "                \"paymentDetails\": {\n" +
            "                    \"issuerCountry\": null,\n" +
            "                    \"issuerBank\": null,\n" +
            "                    \"cardHolderName\": \"Pablo Navarro\",\n" +
            "                    \"cardType\": null,\n" +
            "                    \"cardNumber\": null,\n" +
            "                    \"account\": null,\n" +
            "                    \"cardNumberToken\": null,\n" +
            "                    \"expDate\": null,\n" +
            "                    \"extraDetails\": {\n" +
            "                        \"threeDSMethodData\": \"eyJ0aHJlZURTU2VydmVyVHJhbnNJRCI6ImNjNWM1ODJjLWJiOTgtNGIyNS04NjA5LTZkZDI2YzM3MDgwNSIsICJ0aHJlZURTTWV0aG9kTm90aWZpY2F0aW9uVVJMIjogImh0dHBzOi8vY2hlY2tvdXQuc3RnLWV1LXdlc3QzLmVwZ2ludC5jb20vRVBHQ2hlY2tvdXQvY2FsbGJhY2svZ2F0aGVyRGV2aWNlTm90aWZpY2F0aW9uL3BheXNvbC8zZHN2Mi8xMDIzMDI2In0=\",\n" +
            "                        \"threeDSv2Token\": \"cc5c582c-bb98-4b25-8609-6dd26c370805\"\n" +
            "                    }\n" +
            "                },\n" +
            "                \"extraInfo\": {},\n" +
            "                \"paymentResponse\": {\n" +
            "                    \"status\": \"REDIRECTED\",\n" +
            "                    \"message\": \"Starting 3DSecure 2.0 process.\",\n" +
            "                    \"paySolTransactionId\": null,\n" +
            "                    \"details\": null\n" +
            "                },\n" +
            "                \"authCode\": null,\n" +
            "                \"paymentMessage\": null,\n" +
            "                \"paymentCode\": null,\n" +
            "                \"mpi\": null,\n" +
            "                \"subscriptionPlan\": null,\n" +
            "                \"rad\": null,\n" +
            "                \"respCode\": {\n" +
            "                    \"type\": \"respCode\",\n" +
            "                    \"code\": \"0000\",\n" +
            "                    \"message\": \"Successful\",\n" +
            "                    \"uuid\": \"2e8fd32b_bf6f_4918_875e_01e52d2174b5\"\n" +
            "                }\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";
}
