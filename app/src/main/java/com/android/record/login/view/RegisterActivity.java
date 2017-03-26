package com.android.record.login.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.databinding.ActivityRegisterBinding;
import com.android.record.login.model.LoginModel;
import com.dd.processbutton.iml.ActionProcessButton;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by kiddo on 17-3-26.
 */

public class RegisterActivity extends BaseActivity{
    private ActivityRegisterBinding mRegisterBinding;
    private LoginModel mLoginModel;

    @Override
    protected void getLayoutBinding() {
        mRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mLoginModel = new LoginModel();
        mRegisterBinding.registerBtnRegister.setOnClickListener(this);
        mRegisterBinding.registerBtnRegister.setMode(ActionProcessButton.Mode.ENDLESS);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn_register :
                String username = mRegisterBinding.registerEtUsername.getText().toString();
                String password = mRegisterBinding.registerEtPassword.getText().toString();
                String pwd = mRegisterBinding.registerEtPwd.getText().toString();
                String sex = mRegisterBinding.registerEtSex.getText().toString();
                String email = mRegisterBinding.registerEtEmail.getText().toString();
                String phone = mRegisterBinding.registerEtPhone.getText().toString();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
