# react-native-rsa-jiuye
RSA native module for Android and  IOS



Usage:
1. Install the module from github
> npm install --save git+https://github.com/stefli/react-native-rsa-jiuye.git
or Install from NPM
> npm install --save react-native-rsa-jiuye

2. Link the project
> react-native link react-native-rsa-jiuye

3. Import the RSAUtils, then use it
> import RSAUtils from 'react-native-rsa-jiuye'
> 
> RSAUtils.encrypt(plainText, modulus, exponent).then(data => {
>   console.log('Data->', data);
> });
