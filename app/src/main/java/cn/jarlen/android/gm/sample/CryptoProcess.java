package cn.jarlen.android.gm.sample;

import android.content.Context;

import java.util.List;

public abstract class CryptoProcess {

    private ProcessCallBack callBack;

    public CryptoProcess(ProcessCallBack callBack) {
        this.callBack = callBack;
    }

    abstract void onProcess(Context context, String data, List<String> params);

    public ProcessCallBack getCallBack() {
        return callBack;
    }

    protected interface ProcessCallBack {
        public void onResult(String result);
    }
}
