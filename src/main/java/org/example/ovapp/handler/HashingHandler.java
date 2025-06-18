package org.example.ovapp.handler;

import org.example.ovapp.Instance;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

public class HashingHandler {

    private static final String SHA2_ALGORITHM
            = "SHA-256";

    public static byte[] Creating_SHA2_Hash(String input) {

        try {
            byte[] salt = Instance.salt;
            ByteArrayOutputStream byte_Stream
                    = new ByteArrayOutputStream();

            byte_Stream.write(salt);
            byte_Stream.write(input.getBytes());
            byte[] valueToHash
                    = byte_Stream.toByteArray();
            MessageDigest messageDigest
                    = MessageDigest
                    .getInstance(SHA2_ALGORITHM);
            return messageDigest
                    .digest(valueToHash);
        } catch (Exception e) {
            ScreenHandler.openPopup("Failed to Hash Message");
        }
        return new byte[0];
    }

}