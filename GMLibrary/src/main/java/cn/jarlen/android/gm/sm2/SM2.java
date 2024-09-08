package cn.jarlen.android.gm.sm2;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;

import javax.crypto.Cipher;

import cn.jarlen.android.gm.exception.CryptoException;

/**
 * SM2算法
 *
 * @author Jarlen
 */
public class SM2 {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
        } else {
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        }
        Security.addProvider((Provider) new BouncyCastleProvider());
    }

    /**
     * SM2算法加密<文本>
     * <p>非对称算法</p>
     *
     * @param plainText 待加密的数据字符串
     * @param publicKey 公钥
     * @return 加密后的数据字符串
     * <p>16进制字符串</p>
     */
    public static String encrypt(String plainText, PublicKey publicKey) {
        return Hex.toHexString(encrypt(plainText.getBytes(StandardCharsets.UTF_8), publicKey));
    }

    /**
     * SM2算法解密<文本>
     * <p>非对称算法</p>
     *
     * @param encryptedText 待解密的数据字符串
     * @param privateKey    私钥
     * @return 解密后的数据字符串
     */
    public static String decrypt(String encryptedText, PrivateKey privateKey) {
        return new String(decrypt(Hex.decode(encryptedText), privateKey), StandardCharsets.UTF_8);
    }

    /**
     * SM2算法加密
     * <p>非对称算法</p>
     *
     * @param plainData 待加密的数据byte数组
     * @param publicKey 公钥
     * @return 加密后的数据byte数组
     */
    public static byte[] encrypt(byte[] plainData, PublicKey publicKey) {
        return crypto(plainData, publicKey, true);
    }

    /**
     * SM2算法解密
     * <p>非对称算法</p>
     *
     * @param encryptedData 待解密的数据byte数组
     * @param privateKey    私钥
     * @return 解密后的数据byte数组
     */
    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) {
        return crypto(encryptedData, privateKey, false);
    }

    /**
     * SM2加解密处理
     *
     * @param data      待处理数据
     * @param key       加解密密钥
     * @param isEncrypt 是否加密
     *                  <p>true:加密</p>
     *                  <p>false:解密</p>
     * @return 处理后的数据
     */
    private static byte[] crypto(byte[] data, Key key, boolean isEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("SM2", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException("SM2" + (isEncrypt ? "加密" : "解密") + "失败：" + e.getMessage());
        }
    }

    /**
     * SM2签名
     * <p>使用SM3WithSM2算法</p>
     *
     * @param plainDataBytes 待签名的数据
     * @param privateKey     签名私钥
     * @return 签名数据
     */
    public static byte[] sign(byte[] plainDataBytes, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SM3WithSM2", BouncyCastleProvider.PROVIDER_NAME);
            signature.initSign(privateKey);
            signature.update(plainDataBytes);
            return signature.sign();
        } catch (Exception e) {
            throw new CryptoException(e.getMessage());
        }
    }

    /**
     * SM2验签
     * <p>使用SM3WithSM2算法</p>
     *
     * @param plainDataBytes 待签名的数据
     * @param signDataBytes  签名数据
     * @param publicKey      签名公钥
     * @return 签名数据
     */
    public static boolean verifySign(byte[] plainDataBytes, byte[] signDataBytes, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SM3WithSM2", BouncyCastleProvider.PROVIDER_NAME);
            signature.initVerify(publicKey);
            signature.update(plainDataBytes);
            return signature.verify(signDataBytes);
        } catch (Exception e) {
            throw new CryptoException(e.getMessage());
        }
    }

    /**
     * SM2签名
     * <p>使用SM3WithSM2算法</p>
     *
     * @param plainText  待签名的数据
     * @param privateKey 签名私钥
     * @return 签名数据
     */
    public static String sign(String plainText, PrivateKey privateKey) {
        return Hex.toHexString(sign(plainText.getBytes(StandardCharsets.UTF_8), privateKey));
    }

    /**
     * SM2验签
     * <p>使用SM3WithSM2算法</p>
     *
     * @param plainText 待签名的数据
     * @param signText  签名数据
     * @param publicKey 签名公钥
     * @return 签名数据
     */
    public static boolean verifySign(String plainText, String signText, PublicKey publicKey) {
        return verifySign(plainText.getBytes(StandardCharsets.UTF_8), Hex.decode(signText), publicKey);
    }
}
