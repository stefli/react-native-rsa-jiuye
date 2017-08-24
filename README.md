# react-native-rsa-jiuye
RSA native module for Android



Usage:
1. Install the module from github
npm install --save git+https://github.com/stefli/react-native-rsa-jiuye.git

2. Link the project
react-native link react-native-rsa-jiuye

3. Import the RSAUtils, then use it
import RSAUtils from 'react-native-rsa-jiuye'

RSAUtils.encrypt(plainText, modulus, exponent).then(data => {
  console.log('Data->', data);
});
