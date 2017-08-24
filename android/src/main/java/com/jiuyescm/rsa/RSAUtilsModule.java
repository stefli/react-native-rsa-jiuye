/**
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 */
package com.jiuyescm.rsa;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

/**
 * @author litao
 * @date 2017年8月23日 下午3:35:54
 */
public class RSAUtilsModule extends ReactContextBaseJavaModule {

    public RSAUtilsModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RSAUtils";
    }


    @ReactMethod
    public String encrypt(String plainText, String modulus, String exponent) {
        String encryptString = RSAUtils.encrypt(plainText, modulus, exponent);
        if (encryptString != null) {
            return encryptString;
        } else {
            return plainText;
        }
    }

}
