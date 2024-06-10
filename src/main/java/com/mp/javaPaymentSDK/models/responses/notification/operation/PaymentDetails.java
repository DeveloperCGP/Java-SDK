package com.mp.javaPaymentSDK.models.responses.notification.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.utils.Utils;

@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetails {

    @JacksonXmlProperty(localName = "cardNumberToken")
    @JsonProperty("cardNumberToken")
    private String cardNumberToken;

    @JacksonXmlProperty(localName = "account")
    @JsonProperty("account")
    private String account;

    @JacksonXmlProperty(localName = "cardHolderName")
    @JsonProperty("cardHolderName")
    private String cardHolderName;

    @JacksonXmlProperty(localName = "cardNumber")
    @JsonProperty("cardNumber")
    private String cardNumber;

    @JacksonXmlProperty(localName = "cardType")
    @JsonProperty("cardType")
    private String cardType;

    @JacksonXmlProperty(localName = "expDate")
    @JsonProperty("expDate")
    private String expDate;

    @JacksonXmlProperty(localName = "issuerBank")
    @JsonProperty("issuerBank")
    private String issuerBank;

    @JacksonXmlProperty(localName = "issuerCountry")
    @JsonProperty("issuerCountry")
    private String issuerCountry;

    @JacksonXmlProperty(localName = "extraDetails")
    @JsonProperty("extraDetails")
    private ExtraDetails extraDetails;

    public PaymentDetails() {
    }

    public PaymentDetails(String cardNumberToken, String account, String cardHolderName, String cardNumber, String cardType, String expDate, String issuerBank, String issuerCountry, ExtraDetails extraDetails) {
        this.cardNumberToken = cardNumberToken;
        this.account = account;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expDate = expDate;
        this.issuerBank = issuerBank;
        this.issuerCountry = issuerCountry;
        this.extraDetails = extraDetails;
    }

    public String getCardNumberToken() {
        return cardNumberToken;
    }

    public void setCardNumberToken(String cardNumberToken) {
        this.cardNumberToken = cardNumberToken;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getIssuerBank() {
        return issuerBank;
    }

    public void setIssuerBank(String issuerBank) {
        this.issuerBank = issuerBank;
    }

    public String getIssuerCountry() {
        return issuerCountry;
    }

    public void setIssuerCountry(String issuerCountry) {
        this.issuerCountry = issuerCountry;
    }

    public ExtraDetails getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(ExtraDetails extraDetails) {
        this.extraDetails = extraDetails;
    }
}
