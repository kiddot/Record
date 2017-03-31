package com.android.record.common.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.android.record.R;
import com.dd.processbutton.iml.ActionProcessButton;



/**
 * 输入框
 * Created by kid on 2016/11/10.
 */
public class InputDialog extends DialogFragment implements View.OnClickListener{
    public static final String TAG = InputDialog.class.getSimpleName();

    private EditText inputEdit;
    private ActionProcessButton inputConfirm;
    private InputListener mInputListener;

    public void setInputListener(InputListener inputListener){
        mInputListener = inputListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_confirm :
                inputConfirm.setProgress(50);
                if (mInputListener != null){
                    mInputListener.onInputComplete(inputEdit.getText().toString());
                } else {
                    Log.d(TAG, " mInputListener" + "请进行事件监听");
                }
                inputEdit.setText("");
                break;
        }
    }

    public void dismissInputDialog(int code){
        if (code == -1){
            inputConfirm.setProgress(-1);
        }else {
            inputConfirm.setProgress(100);
        }
        dismiss();
    }

    public interface InputListener {
        void onInputComplete(String text);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_input_dialog, container);
        initView(view);
        //mText = inputEdit.getText().toString();
        inputConfirm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.8), (int) (dm.heightPixels * 0.4));
    }

    private void initView(View view){
        inputEdit = (EditText) view.findViewById(R.id.input_edit);
        inputConfirm = (ActionProcessButton) view.findViewById(R.id.input_confirm);
    }
}
