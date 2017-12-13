//
//  RSAUtils.m
//  RSAUtils
//
//  Created by 小莫 on 2017/12/7.
//  Copyright © 2017年 小莫. All rights reserved.
//

#import "RSAUtils.h"


@implementation RSAUtils

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
    
}
RCT_EXPORT_METHOD(encrypt:(NSString *)password modulus:(NSString *)modulus exponent:(NSString *)exponent resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    if ([[modulus substringToIndex:2] compare:@"00"]==NSOrderedSame){
        modulus = [modulus substringFromIndex:2];
    }
    SecKeyRef pubKey = [RSAPubKey stringToRSAPubKey:modulus andExponent:exponent];
    if( pubKey == nil )
        return;
    
    char  t[128] ;
    char * t1 =[password UTF8String] ;
    for (int i =0; i< 128; i++) {
        if (i < password.length){
            t[i]= t1[i];}
        else{
            t[i] = 0;
        }
    }
    
    NSData* testData = [[NSData alloc] initWithBytes:(unsigned char *)t length:password.length];
    NSData* result = [testData encryptWithRSA:pubKey];
    Byte *bytes = (Byte *)[result bytes];
    NSString *hexStr=@"";
    for(int i=0;i<[result length];i++)
    {
        NSString *newHexStr = [NSString stringWithFormat:@"%x",bytes[i]&0xff]; ///16进制数
        if([newHexStr length]==1)
            hexStr = [NSString stringWithFormat:@"%@0%@",hexStr,newHexStr];
        else
            hexStr = [NSString stringWithFormat:@"%@%@",hexStr,newHexStr];
    }
    
    RCTLogInfo(@"lg-----------Pretending to create an event ------\n%@\n ------\n%@\n -------\n%@\n---------\n%@\n", password, modulus, exponent,hexStr);
    if (hexStr && hexStr.length > 0) {
        resolve(hexStr);
    }else{
        reject(@"-1001", @"RSA Encryption failed", nil);
    }
}
-(void)encryptWithpassword:(NSString *)password modulus:(NSString *)modulus exponent:(NSString *)exponent{
    
    SecKeyRef pubKey = [RSAPubKey stringToRSAPubKey:modulus andExponent:exponent];
    if( pubKey == nil )
        return;
    
    char  t[128] ;
    char * t1 =[password UTF8String] ;
    for (int i =0; i< 128; i++) {
        if (i < password.length){
            t[i]= t1[i];}
        else{
            t[i] = 0;
        }
    }
    
    NSData* testData = [[NSData alloc] initWithBytes:(unsigned char *)t length:password.length];
    NSData* result = [testData encryptWithRSA:pubKey];
    Byte *bytes = (Byte *)[result bytes];
    NSString *hexStr=@"";
    for(int i=0;i<[result length];i++)
    {
        NSString *newHexStr = [NSString stringWithFormat:@"%x",bytes[i]&0xff]; ///16进制数
        if([newHexStr length]==1)
            hexStr = [NSString stringWithFormat:@"%@0%@",hexStr,newHexStr];
        else
            hexStr = [NSString stringWithFormat:@"%@%@",hexStr,newHexStr];
    }
    NSLog(@"bytes 的16进制数为:%@",hexStr);
}
@end
