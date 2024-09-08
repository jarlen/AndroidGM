package cn.jarlen.android.gm.sample;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import cn.jarlen.android.gm.sm3.SM3;

/**
 * SM3执行处理类
 */
public class HMacWithSm3Process extends CryptoProcess {

    public HMacWithSm3Process(ProcessCallBack callBack) {
        super(callBack);
    }

    @Override
    void onProcess(Context context, String data, List<String> params) {

        if (params == null || params.isEmpty()) {
            Toast.makeText(context, "请输入KEY", Toast.LENGTH_SHORT).show();
            return;
        }
        String sm3 = SM3.hMacWithSm3(data, params.get(0));
        getCallBack().onResult(sm3);
    }

}
