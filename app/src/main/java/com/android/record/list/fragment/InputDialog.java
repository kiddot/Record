package com.android.record.list.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.record.R;


/**
 * 输入框
 * Created by kid on 2016/11/10.
 */
public class InputDialog extends DialogFragment implements View.OnClickListener{

    EditText inputEdit;
    Button inputConfirm;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_confirm :
                InputListener listener = (InputListener) getActivity();
                listener.onInputComplete(inputEdit.getText().toString());
                inputEdit.setText("");
                break;
        }
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
        inputConfirm = (Button) view.findViewById(R.id.input_confirm);
    }
}
