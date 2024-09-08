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
public class SM4ECBDecryptProcess extends CryptoProcess {

    public SM4ECBDecryptProcess(ProcessCallBack callBack) {
        super(callBack);
    }

    @Override
    void onProcess(Context context, String data, List<String> params) {
        if (params == null || params.isEmpty()) {
            Toast.makeText(context, "请输入解密密钥KEY", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] key = Hex.decode(params.get(0));
        byte[] encrypt = SM4.encrypt(Hex.decode(data), key);
        getCallBack().onResult(new String(encrypt, StandardCharsets.UTF_8));
    }

}
