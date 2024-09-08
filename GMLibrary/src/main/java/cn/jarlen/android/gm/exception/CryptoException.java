package cn.jarlen.android.gm.exception;

/**
 * 国密算法异常处理类
 *
 * @author jarlen
 * @date 2024/07/31 23:54
 **/
public class CryptoException extends RuntimeException {
    public CryptoException(String message) {
        super(message);
    }
}
