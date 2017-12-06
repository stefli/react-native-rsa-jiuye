//
//  RSAUtilsModule.m
//  RSAUtilsModule
//
//  Created by 小莫 on 2017/12/5.
//  Copyright © 2017年 小莫. All rights reserved.
//

#import "RSAUtilsModule.h"

@implementation RSAUtilsModule

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
    
}
RCT_EXPORT_METHOD(RSAEncryptString:(NSString *)string modulus:(NSString *)modulus exponent:(NSString *)exponent resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    RCTLogInfo(@"lg-----------Pretending to create an event %@ at %@ ---%@", string, modulus, exponent);
    
    NSString * result = [RSAEncryptTool RSAEncryptString:string modulus:modulus exponent:exponent];
    RCTLogInfo(@"lg-----------Pretending to create an event ------\n%@\n ------\n%@\n -------\n%@\n---------\n%@\n", string, modulus, exponent,result);
    if (result && result.length > 0) {
        resolve(result);
    }else{
        reject(@"-1001", @"RSA Encryption failed", nil);
    }
}

@end
