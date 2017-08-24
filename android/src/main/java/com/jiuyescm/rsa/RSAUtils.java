/**
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 */
package com.jiuyescm.rsa;

import com.facebook.common.util.Hex;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;


/**
 * @author litao
 * @date 2017年8月23日 下午3:00:03
 */
public class RSAUtils {

    private static final String ALGORITHM = "RSA";
    private static KeyFactory keyFactory = null;

    private RSAUtils() {
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
        RSAPublicKey publicKey = getRSAPublicKey(modulus, exponent);
        try {
            return new String(encrypt(plainText.getBytes(), publicKey));
        } catch (Exception e) {
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
        Cipher ci = Cipher.getInstance(ALGORITHM);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
        } catch (NullPointerException ex) {
        }
        return null;
    }

    public static RSAPublicKey getRSAPublicKey(String hexModulus, String hexPublicExponent) {
        if (null == hexModulus || hexModulus.length() == 0 || null == hexPublicExponent || hexPublicExponent.length() == 0) {
            return null;
        }
        byte[] modulus = null;
        byte[] publicExponent = null;
        modulus = Hex.decodeHex(hexModulus);
        publicExponent = Hex.decodeHex(hexPublicExponent);

        if (modulus != null && publicExponent != null) {
            return generateRSAPublicKey(modulus, publicExponent);
        }
        return null;
    }

}
