/**
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 *
 */
package com.jiuyescm.rsa;

/**
 * @author litao
 * @date 2017年8月23日 下午3:00:03
 */
 public class RSAUtils {

     private RSAUtils() {
     }

     public static String encrypt(String plainText, String modulus, String exponent) {
         return "EncryptedString" + System.currentTimeMillis();
     }

 }
