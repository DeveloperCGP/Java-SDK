package com.mp.javaPaymentSDK.enums;

public enum TransactionResult {

    SUCCESS,
    PENDING,
    REDIRECTED,
    ERROR,
    FAIL;

    public static TransactionResult getTransactionResult(String transactionResultString) {
        for (TransactionResult transactionResult : TransactionResult.values()) {
            if (transactionResult.name().equalsIgnoreCase(transactionResultString)) {
                return transactionResult;
            }
        }

        return null;
    }
}
