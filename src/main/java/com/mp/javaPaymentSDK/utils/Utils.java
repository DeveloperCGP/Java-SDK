package com.mp.javaPaymentSDK.utils;

import kotlin.Pair;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Generic Utils Class
 */

public class Utils {

    private final static int RANDOM_NUMBER_SIZE = 44;

    private final static String amountFormat = "0.0000";

    /**
     * @param clazz  The class type that will be converted to query
     * @param object The object that will be converted
     * @return Query string of the object
     */
    public static String buildQuery(Class clazz, Object object) {
        String queryString = "";

        try {
            Class<?> currentClass = clazz;
            while (currentClass != null) {
                for (Field f : currentClass.getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.get(object) != null) {
                        String value = f.get(object).toString();
                        if (f.getName().equalsIgnoreCase("merchantParams")) {
                            value = merchantParamsQuery((List<Pair<String, String>>) f.get(object));
                        }
                        queryString = queryString.concat(f.getName() + "=" + value + "&");
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryString.substring(0, queryString.length() - 1);
    }

    public static String encodeUrl(String httpQuery) {
        return URLEncoder.encode(httpQuery, StandardCharsets.UTF_8).replace("%3D", "=").replace("%26", "&").replace("%3B", ";").replace("%3A", ":").replace("%2C", ",");
    }

    public static String merchantParamsQuery(List<Pair<String, String>> merchantParams) {
        StringBuilder merchantParamsQuery = new StringBuilder();
        for (Pair<String, String> parameter : merchantParams) {
            merchantParamsQuery.append(parameter.getFirst());
            merchantParamsQuery.append(":");
            merchantParamsQuery.append(parameter.getSecond());
            merchantParamsQuery.append(";");
        }
        return merchantParamsQuery.substring(0, merchantParamsQuery.length() - 1);
    }

    /**
     * Checks for null variables in a class
     *
     * @param clazz               The class type that will be converted to query
     * @param object              The object that will be checked
     * @param mandatoryFieldsList The fields that will be checked
     * @return Pair of Boolean and String
     * Boolean: if there is missing field
     * String: the name of the missing field
     */
    public static Pair<Boolean, String> containsNull(Class clazz, Object object, List<String> mandatoryFieldsList) {
        ArrayList<String> mandatoryFields = new ArrayList<>(mandatoryFieldsList);
        try {
            Class<?> currentClass = clazz;
            while (currentClass != null) {
                for (Field f : currentClass.getDeclaredFields()) {
                    f.setAccessible(true);
                    if (mandatoryFields.contains(f.getName())) {
                        mandatoryFields.remove(f.getName());
                        if (f.get(object) == null) {
                            return new Pair<>(true, f.getName());
                        }
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
            if (!mandatoryFields.isEmpty()) {
                return new Pair<>(true, mandatoryFields.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Pair<>(true, "An Exception Occurred");
        }

        return new Pair<>(false, null);
    }

    /**
     * Copy an array to another array
     *
     * @param source              Source array that will be copied from
     * @param sourceStartIdx      Start index in the source array
     * @param sourceEndIdx        End index in the source array
     * @param destination         Destination array that will contain the result
     * @param destinationStartIdx Start index in the destination array
     */
    public static void arrayCopy(
            byte[] source,
            int sourceStartIdx,
            int sourceEndIdx,
            byte[] destination,
            int destinationStartIdx
    ) {
        System.arraycopy(source, sourceStartIdx, destination, destinationStartIdx, sourceEndIdx - sourceStartIdx);
    }

    /**
     * Print map in a beuty form
     *
     * @param mp Map to be printed
     */
    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    public static boolean isValidURL(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    public static String generateRandomNumber() {
        Random random = new Random();
        int leftLimit = 48; // numeral '0'
        int rightLimit = 57; // letter '9'

        return random.ints(leftLimit, rightLimit + 1)
                .limit(RANDOM_NUMBER_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String parseAmount(String amount) {
        if (amount.contains(",")) {
            return null;
        }
        try {
            double doubleAmount = Double.parseDouble(amount);
            if (doubleAmount < 0 || doubleAmount > 1000000) {
                return null;
            }
            return roundAmount(doubleAmount);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String roundAmount(double doubleAmount) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
        decimalFormat.applyPattern(amountFormat);
        return decimalFormat.format(doubleAmount);
    }

    public static boolean isNumbersOnly(String stringValue) {
        return stringValue.matches("\\d+");
    }

    public static boolean checkLuhn(String cardNo) {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNo.charAt(i) - '0';

            if (isSecond)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    public static boolean isValidExpDate(String expDate) {
        if (expDate == null || expDate.length() != 4) {
            return false;
        }

        String month = expDate.substring(0, 2);
        String year = expDate.substring(2, 4);

        if (!isNumbersOnly(month) || !isNumbersOnly(year)) {
            return false;
        }

        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        return monthInt >= 1 && monthInt <= 12 && yearInt >= 1;
    }

    public static boolean isValidIP(String ip)
    {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        return validator.isValid(ip);
    }
}
