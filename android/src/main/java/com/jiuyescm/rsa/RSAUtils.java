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
        String modulus = "0086f6e2973a15572807496cd6054741089b16848fa5baa9f987e5349311cfb561893020b7a8f9ce9fa59c8c9074e78bfdd49e53781799b706086d3f57c1079ab5261bb74024ee967289488c8c07a8489c5f79fb85607942926e4584a11a808693897ed4c28c7adb5c33ee6434552f7bcb7974c2120c0993fa290d73c64fe3c5b5";
        String exponent = "010001";
        System.out.println(RSAUtils.encrypt("12345678", modulus, exponent));
    }

}
