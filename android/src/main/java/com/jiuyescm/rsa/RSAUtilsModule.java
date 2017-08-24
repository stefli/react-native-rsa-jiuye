/**
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 */
package com.jiuyescm.rsa;

import com.facebook.react.bridge.Callback;
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
    public void encrypt(String plainText, String modulus, String exponent, Promise promise) {
        try {
            String encryptString = RSAUtils.encrypt(plainText, modulus, exponent);
            if (encryptString != null) {
                promise.resolve(encryptString);
            } else {
                promise.reject("error", "Result is null");
            }
        } catch (Exception e) {
            promise.reject("error", e.getMessage());
        }
    }

    @ReactMethod
    public void encryptWithCallback(String plainText, String modulus, String exponent, Callback callback) {
        try {
            String encryptString = RSAUtils.encrypt(plainText, modulus, exponent);
            if (encryptString != null) {
                callback.invoke("success", encryptString);
            } else {
                callback.invoke("error", "Result is null");
            }
        } catch (Exception e) {
            callback.invoke("error", e.getMessage());
        }
    }

}
