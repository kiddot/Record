package com.android.record.login.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.base.util.Check;
import com.android.record.databinding.ActivityRegisterBinding;
import com.android.record.login.event.RegisterEvent;
import com.android.record.login.model.LoginAndRegister;
import com.dd.processbutton.iml.ActionProcessButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by kiddo on 17-3-26.
 */

public class RegisterActivity extends BaseActivity{
    public static final String TAG = RegisterActivity.class.getSimpleName();
    private ActivityRegisterBinding mRegisterBinding;
    private LoginAndRegister mLoginAndRegister;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

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
        mLoginAndRegister = new LoginAndRegister();
        mRegisterBinding.registerBtnRegister.setMode(ActionProcessButton.Mode.ENDLESS);
        EventBus.getDefault().register(this);
    }

    public void register(View view){
        String username = mRegisterBinding.registerEtUsername.getText().toString();
        String password = mRegisterBinding.registerEtPassword.getText().toString();
        String pwd = mRegisterBinding.registerEtPwd.getText().toString();
        String sex = mRegisterBinding.registerEtSex.getText().toString();
        String email = mRegisterBinding.registerEtEmail.getText().toString();
        String phone = mRegisterBinding.registerEtPhone.getText().toString();
        if (Check.isEmpty(username) && Check.isEmpty(password)){
            showToast("必填项不能为空，请检查~");
            return;
        }else if (password.equals(pwd)){
            mLoginAndRegister.register("register",username,password,email,sex,phone);
        }else {
            showToast("两次密码输入不一致，请检查~");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveLoginEvent(RegisterEvent event){
        int code = event.getCode();
        Log.d(TAG, "onReceiveRegisterEvent: " + code);
        if (code == 200){
            mRegisterBinding.registerBtnRegister.setProgress(100);
            showToast("注册成功");
        } else if (code == 205){
            showToast("注册失败，该用户名已经被注册");
            mRegisterBinding.registerBtnRegister.setProgress(-1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
