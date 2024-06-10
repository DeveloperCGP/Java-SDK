package com.mp.javaPaymentSDK.models.requests.h2h;

import com.mp.javaPaymentSDK.enums.PaymentSolutions;
import com.mp.javaPaymentSDK.exceptions.InvalidFieldException;
import com.mp.javaPaymentSDK.models.Credentials;
import com.mp.javaPaymentSDK.utils.Utils;
import kotlin.Pair;

import java.util.Arrays;
import java.util.List;

public class H2HRefund {
    private String amount = null;
    private String merchantId = null;
    private PaymentSolutions paymentSolution = null;
    private String transactionId = null;
    private String merchantTransactionId = null;
    private String description = null;

    public H2HRefund() throws InvalidFieldException {
        setMerchantTransactionId(Utils.generateRandomNumber());
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws InvalidFieldException {
        String parsedAmount = Utils.parseAmount(amount);
        if (parsedAmount == null) {
            throw new InvalidFieldException("amount: Should Follow Format #.#### And Be Between 0 And 1000000");
        }
        this.amount = parsedAmount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId(String merchantTransactionId) throws InvalidFieldException {
        if (merchantTransactionId.isBlank() || merchantTransactionId.length() > 45) {
            throw new InvalidFieldException("merchantTransactionId: Invalid Size, size must be (0 < merchantTransactionId <= 45)");
        }
        this.merchantTransactionId = merchantTransactionId;
    }

    public PaymentSolutions getPaymentSolution() {
        return paymentSolution;
    }

    public void setPaymentSolution(PaymentSolutions paymentSolution) {
        this.paymentSolution = paymentSolution;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) throws InvalidFieldException {
        if (!Utils.isNumbersOnly(transactionId) || transactionId.length() > 100) {
            throw new InvalidFieldException("transactionId: Must be numbers only with size (transactionId <= 100)");
        }
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidFieldException {
        if (description.length() > 1000) {
            throw new InvalidFieldException("description: Invalid Size, size must be (description <= 1000)");
        }
        this.description = description;
    }

    public void setCredentials(Credentials credentials) {
        this.merchantId = credentials.getMerchantId();
    }

    public Pair<Boolean, String> isMissingField() {
        List<String> mandatoryFields = Arrays.asList(
                "amount", "merchantId", "merchantTransactionId", "paymentSolution", "transactionId"
        );

        return Utils.containsNull(
                H2HRefund.class, this, mandatoryFields
        );
    }

    public Pair<Boolean, String> checkCredentials(Credentials credentials) {
        if (credentials.getApiVersion() < 0)
        {
            return new Pair<>(true, "apiVersion");
        }
        List<String> mandatoryFields = Arrays.asList(
                "merchantId", "merchantPass", "environment"
        );

        return Utils.containsNull(
                Credentials.class, credentials, mandatoryFields
        );
    }
}
