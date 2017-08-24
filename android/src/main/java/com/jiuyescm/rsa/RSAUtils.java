/**
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 */
package com.jiuyescm.rsa;

import com.facebook.common.util.Hex;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;


/**
 * @author litao
 * @date 2017年8月23日 下午3:00:03
 */
public class RSAUtils {

    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    private static final String ALGORITHM = "RSA";
    private static KeyFactory keyFactory = null;

    private RSAUtils() {
    }

    static {
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            ;
        }
    }

    /**
     * Encrypt plain text by modulus and exponent
     *
     * @param plainText
     * @param modulus
     * @param exponent
     * @return
     */
    public static String encrypt(String plainText, String modulus, String exponent) {
        return encrypt(plainText, Hex.decodeHex(modulus), Hex.decodeHex(exponent));
    }

    /**
     * Encrypt plain text by modulus and exponent
     *
     * @param plainText
     * @param modulus
     * @param publicExponent
     * @return
     */
    public static String encrypt(String plainText, byte[] modulus, byte[] publicExponent) {
        RSAPublicKey publicKey = generateRSAPublicKey(modulus, publicExponent);
        try {
            byte[] encryptedBytes = encrypt(plainText.getBytes(), publicKey);
            return Hex.encodeHex(encryptedBytes, false).toLowerCase();
        } catch (Exception e) {
            ;
            return plainText;
        }
    }

    /**
     * Encrypt the plain text by public key
     *
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        System.out.println(new BigInteger(modulus));
        System.out.println(new BigInteger(publicExponent));
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            ;
        } catch (NullPointerException e) {
            ;
        }
        return null;
    }

    public static RSAPublicKey getRSAPublicKey(String hexModulus, String hexPublicExponent) {
        if (null == hexModulus || hexModulus.length() == 0 || null == hexPublicExponent || hexPublicExponent.length() == 0) {
            return null;
        }
        byte[] modulus = Hex.decodeHex(hexModulus);
        byte[] publicExponent = Hex.decodeHex(hexPublicExponent);

        if (modulus != null && publicExponent != null) {
            RSAPublicKey publicKey = generateRSAPublicKey(modulus, publicExponent);
            return publicKey;
        }
        return null;
    }

    public static void main(String[] args) {
        String modulus = "0092a947f9432fd8aa6c79d794f3bd07cf50c8827c69919d221213814a69d1667ca3bee1bbe27d0d50f97497f2d2f6f18de05881d7e24b08c1c7eddf828a9b5b0ed868fcecc14ce5045372d56bfe13172cbd1a96498e9ac1d20abf4687620215a7b1458d93fca7946cad99ff41f1308816ab3be3d6ffb754d06e1d3b7dfe635bfd";
        String exponent = "010001";
        System.out.println(RSAUtils.encrypt("12345678", modulus, exponent));
    }

}
