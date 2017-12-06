//
//  RSAEncryptTool.m
//  RSAwithModulusAndExponent
//
//  Created by Mac on 16/4/18.
//  Copyright © 2016年 Mac. All rights reserved.
//

#import "RSAEncryptTool.h"

#include <openssl/opensslv.h>

#include <openssl/rsa.h>

#include <openssl/evp.h>

#include <openssl/bn.h>

#import "GTMBase64.h"


@implementation RSAEncryptTool

//十六进制字符串转为正常data
+ (NSData *)setBase64Data:(NSString *)str{
    NSMutableData *hexData = [[NSMutableData alloc] initWithCapacity:8];
    NSRange range;
    if ([str length] % 2 == 0) {
        range = NSMakeRange(0, 2);
    } else {
        range = NSMakeRange(0, 1);
    }
    for (NSInteger i = range.location; i < [str length]; i += 2) {
        unsigned int anInt;
        NSString *hexCharStr = [str substringWithRange:range];
        NSScanner *scanner = [[NSScanner alloc] initWithString:hexCharStr];
        
        [scanner scanHexInt:&anInt];
        NSData *entity = [[NSData alloc] initWithBytes:&anInt length:1];
        [hexData appendData:entity];
        
        range.location += range.length;
        range.length = 2;
    }
    return hexData;
}
//正常data转为十六进制字符串
+ (NSString *)getBase64:(NSData *)result{
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
    return hexStr;
}
+ (NSString *)RSAEncryptString:(NSString *)data modulus:(NSString *)modulus exponent:(NSString *)exponent
{
    //一般情况这两个字符是被base64加密过的
    // NSString *modulus = @"keAcC2TrYsqoYn1gyMwbX0VxzOF7iAdMVqGE0Xt/i/VHo1L7hwlRerr5xiSedjJEm2OxVG+FPn4ol1jw/adt8t2fpN7CAyTHrokqS+LcYbQPslH0pOy9ty0CctWCJhk1HD3T1ByP4K+9dpHmbbb13ixavbC318gElz8UoSeWEz8=";//通过这个可以得到其中的N
    //NSString *exponent = @"AQAB";//通过这个其中的e,(e也可以是一个很大的数)
    
    //NSString *data = @"1234";
    
    //        NSData *nsdataFromBase64String = [[NSData alloc]
    //                                      initWithBase64EncodedString:base64 options:0];
    
    NSData *m1 = [[NSData alloc]
                 initWithBase64EncodedString:modulus options:0];;
    NSData *e1 = [[NSData alloc]
                 initWithBase64EncodedString:exponent options:0];;
    
    NSData * m = [RSAEncryptTool setBase64Data:modulus];
    NSData * e = [RSAEncryptTool setBase64Data:exponent];
    
    RSA *r;
    BIGNUM *bne, *bnn;//rsa算法中的 e和N
    int blockLen;//每次最大加密字节数
    unsigned char *encodeData;//加密后的数据
    
    bnn = BN_new();
    bne = BN_new();
    
    r = RSA_new();
    //看到网上有人用BN_hex2bn这个函数来转化的，但我用这个转化总是失败，最后选择了BN_bin2bn
    r->e = BN_bin2bn([e bytes], [e length], bne);
    r->n = BN_bin2bn([m bytes], [m length], bnn);
    
    blockLen = RSA_size(r);// 公钥长度/8 - 11
    
    encodeData = (unsigned char *)malloc(blockLen);
    bzero(encodeData, blockLen);
    
    //由于需要加密的内容都在最大加密长度内，所以我没有分块，如果你的文本内容长度超过了blockLen，请分块处理，然后拼接起来
    char  t[128] ;
    char * t1 =[data UTF8String] ;
    for (int i =0; i< blockLen; i++) {
        if (i < data.length){
            t[i]= t1[i];}
        else{
            t[i] = 0;
        }
    }
    int ret = RSA_public_encrypt(blockLen, (unsigned char *)t, encodeData, r, RSA_NO_PADDING);
    //这里的 RSA_PKCS1_PADDING选择的不同，对应的最大加密长度就不一样，当时在网上看到过，现在找不到了，你们自己上网找找吧
    
    
    RSA_free(r);
    if(ret < 0)
    {
        NSLog(@"encrypt failed !");
        return @"";
    }
    else
    {
//      NSData *result = [Base64 encodeBytes:encodeData length:ret];
//      char * a = (char*)malloc(sizeof(byte)*16);
        NSData *result = [NSData dataWithBytes: encodeData   length:ret];
        NSString *cipherString = [GTMBase64 stringByEncodingData:result];
//      NSLog(@"加密后的base64:%@",cipherString);
        free(encodeData);
//      return [[NSString alloc] initWithData:result encoding:NSUTF8StringEncoding];
        
        NSString * resultstr = [RSAEncryptTool getBase64:result];
    
        return resultstr;
    }
}


@end
