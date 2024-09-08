package cn.jarlen.android.gm.sm3;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.Security;

import cn.jarlen.android.gm.exception.CryptoException;

/**
 * SM3算法
 *
 * @author Jarlen
 */
public class SM3 {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
        } else {
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        }
        Security.addProvider((Provider) new BouncyCastleProvider());
    }

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
    public static String sm3(String plainText) {
        byte[] srcData = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] sm3 = sm3(srcData);
        return Hex.toHexString(sm3);
    }

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
    public static byte[] sm3(byte[] plainByte) {
        SM3Digest digest = new SM3Digest();
        digest.update(plainByte, 0, plainByte.length);
        byte[] sm3 = new byte[digest.getDigestSize()];
        digest.doFinal(sm3, 0);
        return sm3;
    }

    /**
     * sm3算法(杂凑算法)
     * <p>SM3算法是我国自主设计的密码杂凑算法，适用于商用密码应用中的数字签名和验证消息认证码的生成与验证
     * 以及随机数的生成</p>
     * <p>优于128比特的MD5以及160比特的SHA-1，其输出长度为256比特</p>
     * <p>类比：SHA256</p>
     *
     * @param plainFile 待加密的文件
     * @return 长度为256位比特数组
     */
    public static byte[] sm3(File plainFile) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(plainFile);
            byte[] buffer = new byte[4096];
            int byteRead;
            SM3Digest sm3Digest = new SM3Digest();
            while ((byteRead = fis.read(buffer)) != -1) {
                sm3Digest.update(buffer, 0, byteRead);
            }
            byte[] hash = new byte[sm3Digest.getDigestSize()];
            sm3Digest.doFinal(hash, 0);
            if (fis != null) {
                fis.close();
            }
            return hash;
        } catch (FileNotFoundException e) {
            throw new CryptoException("SM3文件处理失败,源文件打开失败:" + e);
        } catch (Exception e) {
            throw new CryptoException("SM3文件处理失败," + e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ignore) {

            }
        }
    }


    /**
     * 使用sm3算法构造的hMac
     *
     * @param plainText 待加密的明文字符串
     * @param keyText   密钥字符串
     * @return 加密后固定长度为64的16进制字符串
     */
    public static String hMacWithSm3(String plainText, String keyText) {
        return Hex.toHexString(hMacWithSm3(plainText.getBytes(StandardCharsets.UTF_8), keyText.getBytes()));
    }

    /**
     * 使用sm3算法构造的hMac
     *
     * @param plainByte 待加密的明文数组
     * @param keyByte   密钥数组
     * @return 加密后固定长度为64的16进制数组
     */
    public static byte[] hMacWithSm3(byte[] plainByte, byte[] keyByte) {
        KeyParameter keyParameter = new KeyParameter(keyByte);
        SM3Digest sm3Digest = new SM3Digest();
        HMac mac = new HMac(sm3Digest);
        mac.init(keyParameter);
        mac.update(plainByte, 0, plainByte.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }
}
