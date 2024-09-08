package cn.jarlen.android.gm.sample;

import android.content.Context;
import android.widget.Toast;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.List;

import cn.jarlen.android.gm.sm4.SM4;

/**
 * SM3执行处理类
 */
public class SM4CBCDecryptProcess extends CryptoProcess {

    public SM4CBCDecryptProcess(ProcessCallBack callBack) {
        super(callBack);
    }

    @Override
    void onProcess(Context context, String data, List<String> params) {
        if (params == null || params.size() < 2) {
            Toast.makeText(context, "请输入解密密钥KEY和初始化向量IV", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] key = Hex.decode(params.get(0));
        byte[] iv = Hex.decode(params.get(1));
        byte[] encrypt = SM4.encrypt(Hex.decode(data), key, iv);
        getCallBack().onResult(new String(encrypt, StandardCharsets.UTF_8));
    }

}
