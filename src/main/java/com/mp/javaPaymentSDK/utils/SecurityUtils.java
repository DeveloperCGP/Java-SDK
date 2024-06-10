package com.mp.javaPaymentSDK.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * Security Util Class
 */

public class SecurityUtils {

    private static int AES_256_BLOCK_SIZE = 32;

    /**
     * Generates a random byte array (IV) with size 16 bytes
     * @return Generated IV
     */
    public static byte[] generateIV() {
        Random random = new Random();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return iv;
    }

    /**
     * Encode byte array to string
     * @param byteArray Byte array to be encoded
     * @return The encoded string
     */
    public static String base64Encode(byte[] byteArray) {
        byte[] encodedBytes = Base64.getEncoder().encode(byteArray);
        return new String(encodedBytes);
    }

    /**
     *
     * @param data Data to be encrypted
     * @param key Key used in the encryption
     * @param iv IV used in the encryption
     * @param encrypt Boolean in which the function do encryption or decryption
     * @return The byte array result
     */
    public static byte[] cbcEncryption(byte[] data, byte[] key, byte[] iv, Boolean encrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            if (encrypt) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            }

            return cipher.doFinal(data);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Hashing (Signature) some data with SHA256
     * @param data Data to be hashed
     * @return Hashed string
     */
    public static byte[] hash256(byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Apply AES zero padding to a byte array
     * @param data Data to be padded
     * @return Padded byte array
     */
    public static byte[] applyAESPadding(byte[] data) {
        if (data.length == 0) {
            return null;
        }

        if (data.length % AES_256_BLOCK_SIZE == 0) {
            return data;
        }

        byte[] paddedData = new byte[data.length + (32 - (data.length % AES_256_BLOCK_SIZE))];
        Utils.arrayCopy(data, 0, data.length, paddedData, 0);
        return paddedData;
    }

}
