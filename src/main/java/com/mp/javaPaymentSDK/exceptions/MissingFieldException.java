package com.mp.javaPaymentSDK.exceptions;

public class MissingFieldException extends FieldException {
    public MissingFieldException(String message) {
        super(message);
    }

    public static String createMessage(String field, boolean isCred)
    {
        if (isCred)
        {
            return "Mandatory credentials are missing. Please ensure you provide: " + field;
        }
        else
        {
            return "Missing " + field;
        }
    }
}
