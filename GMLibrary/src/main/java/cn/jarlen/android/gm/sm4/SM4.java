package cn.jarlen.android.gm.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Provider;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.jarlen.android.gm.exception.CryptoException;

/**
 * SM4算法
 *
 * @author Jarlen
 */
public class SM4 {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
        } else {
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        }
        Security.addProvider((Provider) new BouncyCastleProvider());
    }

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
    public static byte[] encrypt(byte[] plainData, byte[] key, byte[] iv) {
        return crypto(plainData, key, iv, true);
    }

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
    public static byte[] decrypt(byte[] encryptedData, byte[] key, byte[] iv) {
        return crypto(encryptedData, key, iv, false);
    }

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
    public static byte[] encrypt(byte[] plainData, byte[] key) {
        return crypto(plainData, key, true);
    }

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
    public static byte[] decrypt(byte[] encryptedData, byte[] key) {
        return crypto(encryptedData, key, false);
    }

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
    public static boolean encrypt(File plainFile, File encryptedFile, byte[] key, byte[] iv) {
        return crypto(plainFile, encryptedFile, key, iv, true);
    }

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
    public static boolean decrypt(File encryptedFile, File plainFile, byte[] key, byte[] iv) {
        return crypto(plainFile, encryptedFile, key, iv, false);
    }

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
    public static boolean encrypt(File plainFile, File encryptedFile, byte[] key) {
        return crypto(plainFile, encryptedFile, key, true);
    }

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
    public static boolean decrypt(File encryptedFile, File plainFile, byte[] key) {
        return crypto(plainFile, encryptedFile, key, false);
    }

    /**
     * SM4算法加解密
     * <p>对称算法</p>
     * <p>CBC模式</p>
     * <p>PKCS7Padding</p>
     *
     * @param data 待解密的密文byte数组
     * @param key  密钥byte数组
     *             <p>密钥长度不能小于128 byte</p>
     * @param iv   初始化向量
     * @return 解密之后的明文比特数组
     */
    private static byte[] crypto(byte[] data, byte[] key, byte[] iv, boolean isEncrypt) {
        SecretKey secretKey = new SecretKeySpec(key, "SM4");
        try {
            Cipher cipher = Cipher.getInstance("SM4/CBC/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException("SM4" + (isEncrypt ? "加密" : "解密") + "失败: " + e.getMessage());
        }
    }

    /**
     * SM4算法加解密
     * <p>对称算法</p>
     * <p>ECB模式</p>
     * <p>PKCS7Padding</p>
     *
     * @param data 待解密的密文byte数组
     * @param key  密钥byte数组
     *             <p>密钥长度不能小于128 byte</p>
     * @return 解密之后的明文比特数组
     */
    private static byte[] crypto(byte[] data, byte[] key, boolean isEncrypt) {
        SecretKey secretKey = new SecretKeySpec(key, "SM4");
        try {
            Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException("SM4" + (isEncrypt ? "加密" : "解密") + "失败: " + e.getMessage());
        }
    }

    /**
     * 文件加解密
     * <p>CBC模式</p>
     * <p>PKCS7Padding</p>
     *
     * @param srcFile    源文件，待处理的文件
     * @param targetFile 处理之后的文件
     * @param key        加解密密钥
     * @param iv         初始化向量
     * @param isEncrypt  是否加密
     * @return 是否成功
     * <p>true:解密成功</p>
     * <p>false:解密失败</p>
     */
    private static boolean crypto(File srcFile, File targetFile, byte[] key, byte[] iv, boolean isEncrypt) {
        SecretKey secretKey = new SecretKeySpec(key, "SM4");
        try {
            Cipher cipher = Cipher.getInstance("SM4/CBC/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            sm4FileExecute(cipher, srcFile, targetFile);
        } catch (Exception e) {
            throw new CryptoException("SM4" + (isEncrypt ? "加密" : "解密") + "失败: " + e.getMessage());
        }
        return true;
    }

    /**
     * 文件加解密
     * <p>ECB模式</p>
     * <p>PKCS7Padding</p>
     *
     * @param srcFile    源文件，待处理的文件
     * @param targetFile 处理之后的文件
     * @param key        加解密密钥
     * @param isEncrypt  是否加密
     * @return 是否成功
     * <p>true:解密成功</p>
     * <p>false:解密失败</p>
     */
    private static boolean crypto(File srcFile, File targetFile, byte[] key, boolean isEncrypt) {
        SecretKey secretKey = new SecretKeySpec(key, "SM4");
        try {
            Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
            sm4FileExecute(cipher, srcFile, targetFile);
        } catch (Exception e) {
            throw new CryptoException("SM4" + (isEncrypt ? "加密" : "解密") + "失败: " + e.getMessage());
        }
        return true;
    }

    /**
     * 对文件进行SM4s算法加密
     *
     * @param cipher        加密句柄
     * @param plainFile     待加密的文件
     * @param encryptedFile 加密后的文件
     */
    private static void sm4FileExecute(Cipher cipher, File plainFile, File encryptedFile) {
        byte[] inputBuffer = new byte[cipher.getBlockSize()];
        byte[] outputBuffer = new byte[cipher.getOutputSize(inputBuffer.length)];
        FileInputStream plainFileInputStream = null;
        FileOutputStream outputFileStream = null;
        try {
            plainFileInputStream = new FileInputStream(plainFile);
            outputFileStream = new FileOutputStream(encryptedFile);
            int bytesRead;
            while ((bytesRead = plainFileInputStream.read(inputBuffer)) != -1) {
                int outputLength = cipher.update(inputBuffer, 0, bytesRead, outputBuffer, 0);
                if (outputLength > 0) {
                    outputFileStream.write(outputBuffer, 0, outputLength);
                }
            }
            int finalOutputLength = cipher.doFinal(outputBuffer, 0);
            if (finalOutputLength > 0) {
                outputFileStream.write(outputBuffer, 0, finalOutputLength);
            }
        } catch (IllegalBlockSizeException | ShortBufferException | IOException | BadPaddingException e) {
            throw new CryptoException(e.getMessage());
        } finally {
            try {
                if (outputFileStream != null) {
                    outputFileStream.close();
                }
                if (plainFileInputStream != null) {
                    plainFileInputStream.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
