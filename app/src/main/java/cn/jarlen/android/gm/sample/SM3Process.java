package cn.jarlen.android.gm.sample;

import android.content.Context;

import java.util.List;

import cn.jarlen.android.gm.sm3.SM3;

/**
 * SM3执行处理类
 */
public class SM3Process extends CryptoProcess {

    public SM3Process(ProcessCallBack callBack) {
        super(callBack);
    }

    @Override
    void onProcess(Context context, String data, List<String> params) {
        String sm3 = SM3.sm3(data);
        getCallBack().onResult(sm3);
    }

}
