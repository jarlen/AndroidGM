# AndroidGM

## 简介

**AndroidGM** 是基于Bouncy Castle封装常用国密算法处理类,支持SM3算法、支持SM4算法、支持对文本、文件等数据进行SM2加解密操作

## 接口说明

### SM3

```
/**
 * sm3算法(杂凑算法)
 * <p>SM3算法是我国自主设计的密码杂凑算法，适用于商用密码应用中的数字签名和验证消息认证码的生成与验证
 * 以及随机数的生成</p>
 * <p>优于128比特的MD5以及160比特的SHA-1，其输出长度为256比特</p>
 * <p>类比：SHA256</p>
 *
 * @param plainText 待加密的明文字符串
 * @return 长度为32字节的16进制字符串
 */
SM3.sm3(String plainText)
```

```
/**
 * sm3算法(杂凑算法)
 * <p>SM3算法是我国自主设计的密码杂凑算法，适用于商用密码应用中的数字签名和验证消息认证码的生成与验证
 * 以及随机数的生成</p>
 * <p>优于128比特的MD5以及160比特的SHA-1，其输出长度为256比特</p>
 * <p>类比：SHA256</p>
 *
 * @param plainByte 待加密的明文byte数组
 * @return 长度为256位 byte数组
 */
SM3.sm3(byte[] plainByte)
```

```
/**
 * 使用sm3算法构造的hMac
 *
 * @param plainText 待加密的明文字符串
 * @param keyText   密钥字符串
 * @return 加密后固定长度为64的16进制字符串
 */
SM3.hMacWithSm3(String plainText, String keyText)
```

```
/**
 * 使用sm3算法构造的hMac
 *
 * @param plainByte 待加密的明文数组
 * @param keyByte   密钥数组
 * @return 加密后固定长度为64的16进制数组
 */
SM3.hMacWithSm3(byte[] plainByte, byte[] keyByte)
```

### SM4

#### CBC模式

```
/**
 * SM4算法加密(byte数组)
 * <p>对称算法</p>
 * <p>CBC模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param plainData 待加密的明文byte数组
 * @param key       秘钥byte数组
 *                  <p>密钥长度不能小于128 byte</p>
 * @param iv        初始化向量
 * @return 加密之后的密文比特数组
 */
SM4.encrypt(byte[] plainData, byte[] key, byte[] iv)
```

```
/**
 * SM4算法解密(byte数组)
 * <p>对称算法</p>
 * <p>CBC模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param encryptedData 待解密的密文byte数组
 * @param key           解密密钥
 *                      <p>密钥长度不能小于128 byte</p>
 * @param iv            初始化向量
 * @return 解密之后的明文比特数组
 */
SM4.decrypt(byte[] plainData, byte[] key, byte[] iv)
```

```
/**
 * SM4算法加密(文件)
 * <p>CBC模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param plainFile     带加密的文件
 * @param encryptedFile 加密之后的文件
 * @param key           加密密钥
 *                      <p>密钥长度不能小于16字符，可包含字母、数字、标点</p>
 * @param iv            初始化向量
 * @return 是否成功
 * <p>true:加密成功</p>
 * <p>false:加密失败</p>
 */
SM4.encrypt(File plainFile, File encryptedFile, byte[] key, byte[] iv)
```

```

/**
 * SM4算法解密(文件)
 * <p>CBC模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param encryptedFile 带解密的文件
 * @param plainFile     解密之后的文件
 * @param key           解密密钥
 *                      <p>密钥长度不能小于16字符，可包含字母、数字、标点</p>
 * @param iv            初始化向量
 * @return 是否成功
 * <p>true:解密成功</p>
 * <p>false:解密失败</p>
 */
SM4.encrypt(File encryptedFile, File plainFile, byte[] key, byte[] iv)
```

#### ECB模式

```
/**
 * SM4算法加密(byte数组)
 * <p>对称算法</p>
 * <p>ECB模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param plainData 待加密的明文byte数组
 * @param key       秘钥byte数组
 *                  <p>密钥长度不能小于128 byte</p>
 * @return 加密之后的密文比特数组
 */
SM4.encrypt(byte[] plainData, byte[] key)
```

```
/**
 * SM4算法解密(byte数组)
 * <p>对称算法</p>
 * <p>ECB模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param encryptedData 待解密的密文byte数组
 * @param key           解密密钥
 *                      <p>密钥长度不能小于128 byte</p>
 * @return 解密之后的明文比特数组
 */
SM4.decrypt(byte[] plainData, byte[] key)
```

```
/**
 * SM4算法加密(文件)
 * <p>ECB模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param plainFile     带加密的文件
 * @param encryptedFile 加密之后的文件
 * @param key           加密密钥
 *                      <p>密钥长度不能小于16字符，可包含字母、数字、标点</p>
 * @return 是否成功
 * <p>true:加密成功</p>
 * <p>false:加密失败</p>
 */
SM4.encrypt(File plainFile, File encryptedFile, byte[] key)
```

```
/**
 * SM4算法解密(文件)
 * <p>ECB模式</p>
 * <p>PKCS7Padding</p>
 *
 * @param encryptedFile 带解密的文件
 * @param plainFile     解密之后的文件
 * @param key           解密密钥
 *                      <p>密钥长度不能小于16字符，可包含字母、数字、标点</p>
 * @return 是否成功
 * <p>true:解密成功</p>
 * <p>false:解密失败</p>
 */
SM4.decrypt(File encryptedFile, File plainFile, byte[] key)
```

### SM2

#### SM2加解密

```
/**
 * SM2算法加密<文本>
 * <p>非对称算法</p>
 *
 * @param plainText 待加密的数据字符串
 * @param publicKey 公钥
 * @return 加密后的数据字符串
 * <p>16进制字符串</p>
 */
SM2.encrypt(String plainText, PublicKey publicKey)
```

```
/**
 * SM2算法解密<文本>
 * <p>非对称算法</p>
 *
 * @param encryptedText 待解密的数据字符串
 * @param privateKey    私钥
 * @return 解密后的数据字符串
 */
SM2.decrypt(String encryptedText, PrivateKey privateKey)
```

```
/**
 * SM2算法加密
 * <p>非对称算法</p>
 *
 * @param plainData 待加密的数据byte数组
 * @param publicKey 公钥
 * @return 加密后的数据byte数组
 */
SM2.encrypt(byte[] plainData, PublicKey publicKey)
```

```
/**
 * SM2算法解密
 * <p>非对称算法</p>
 *
 * @param encryptedData 待解密的数据byte数组
 * @param privateKey    私钥
 * @return 解密后的数据byte数组
 */
SM2.encrypt(byte[] encryptedData, PrivateKey privateKey)
```

#### SM3WithSM2签名验签

```
/**
 * SM2签名
 * <p>使用SM3WithSM2算法</p>
 *
 * @param plainText  待签名的数据
 * @param privateKey 签名私钥
 * @return 签名数据
 */
SM2.sign(String plainText, PrivateKey privateKey)
```

```
/**
 * SM2验签
 * <p>使用SM3WithSM2算法</p>
 *
 * @param plainText 待签名的数据
 * @param signText  签名数据
 * @param publicKey 签名公钥
 * @return 签名数据
 */
SM2.verifySign(String plainText, String signText, PublicKey publicKey)
```

```
/**
 * SM2签名
 * <p>使用SM3WithSM2算法</p>
 *
 * @param plainDataBytes 待签名的数据
 * @param privateKey     签名私钥
 * @return 签名数据
 */
SM2.sign(byte[] plainDataBytes, PrivateKey privateKey)
```

```
/**
 * SM2验签
 * <p>使用SM3WithSM2算法</p>
 *
 * @param plainDataBytes 待签名的数据
 * @param signDataBytes  签名数据
 * @param publicKey      签名公钥
 * @return 签名数据
 */
SM2.verifySign(byte[] plainDataBytes, byte[] signDataBytes, PublicKey publicKey)
```