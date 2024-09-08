package cn.jarlen.android.gm.sample;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.snackbar.Snackbar;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSpinnerItemSelectedListener, View.OnClickListener, CryptoProcess.ProcessCallBack {

    private AppCompatEditText etInputData;
    private AppCompatEditText etInputKey;
    private AppCompatEditText etInputIv;

    private NiceSpinner spinner;
    private AppCompatButton btnDo;
    private AppCompatButton btnCopy;

    private AppCompatTextView tvResult;

    private List<String> spinnerList;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        etInputData = findViewById(R.id.et_input_data);
        etInputKey = findViewById(R.id.et_input_key);
        etInputIv = findViewById(R.id.et_input_iv);

        spinner = findViewById(R.id.spinner_menu);
        btnDo = findViewById(R.id.btn_process);
        btnCopy = findViewById(R.id.btn_copy_result_to_input);
        tvResult = findViewById(R.id.tv_result);

        spinnerList = Arrays.asList(getResources().getStringArray(R.array.spinner_menu));
        spinner.attachDataSource(spinnerList);
        spinner.setOnSpinnerItemSelectedListener(this);

        btnDo.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
        mCurrentPosition = position;
    }

    private CryptoProcess findCryptoProcess(String menuClassName) {
        try {
            Class<?> processClass = Class.forName(menuClassName);
            Constructor constructor = processClass.getConstructor(CryptoProcess.ProcessCallBack.class);
            CryptoProcess cryptoProcess = (CryptoProcess) constructor.newInstance(this);
            return cryptoProcess;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_process:
                String data = etInputData.getText().toString();
                if (TextUtils.isEmpty(data)) {
                    Snackbar.make(view, "请输入待处理的数据", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                String[] menuClassArray = getResources().getStringArray(R.array.spinner_menu_class);
                if (mCurrentPosition > menuClassArray.length - 1) {
                    Snackbar.make(view, "未找到该算法处理类", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                String menuClassName = menuClassArray[mCurrentPosition];
                CryptoProcess cryptoProcess = findCryptoProcess(menuClassName);
                if (cryptoProcess == null) {
                    Snackbar.make(view, "该算法处理类初始化失败", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                try {
                    List<String> paramList = new ArrayList<>();
                    if (!TextUtils.isEmpty(etInputKey.getText())) {
                        paramList.add(etInputKey.getText().toString());
                    }

                    if (paramList.size() > 1
                            && !TextUtils.isEmpty(etInputIv.getText())) {
                        paramList.add(etInputIv.getText().toString());
                    }

                    cryptoProcess.onProcess(this, data, paramList);
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_copy_result_to_input:
                String result = tvResult.getText().toString();
                etInputData.setText(result);
                if (!TextUtils.isEmpty(result)) {
                    etInputData.setSelection(result.length());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResult(String result) {
        if (TextUtils.isEmpty(result)) {
            return;
        }
        tvResult.setText(result);
    }
}

