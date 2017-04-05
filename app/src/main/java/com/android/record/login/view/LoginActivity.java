package com.android.record.login.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.base.util.Check;
import com.android.record.databinding.ActivityLoginBinding;
import com.android.record.login.event.LoginEvent;
import com.android.record.login.model.LoginAndRegister;
import com.android.record.main.view.MainActivity;
import com.dd.processbutton.iml.ActionProcessButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by kiddo on 17-3-25.
 */

public class LoginActivity extends BaseActivity{
    public static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding mLoginBinding;
    private LoginAndRegister mLoginAndRegister;

    @Override
    protected void getLayoutBinding() {
        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
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
        mLoginBinding.loginBtnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        EventBus.getDefault().register(this);
    }

    public void login(View view){
//        String username = mLoginBinding.loginEtUsername.getText().toString();
//        String password = mLoginBinding.loginEtPassword.getText().toString();
//        if (Check.isEmpty(username) || Check.isEmpty(password)){
//            showToast("请检查账号或者密码是否为空");
//        }else {
//            mLoginAndRegister.login(this, "login", username, password);
//            mLoginBinding.loginBtnLogin.setProgress(50);
//            Log.d(TAG, "用户正在尝试登录  ----ing ");
//        }
        MainActivity.startActivity(this);
    }

    public void signUp(View view){
        RegisterActivity.startActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveLoginEvent(LoginEvent event){
        boolean isSuccess = event.isSuccess();
        Log.d(TAG, "onReceiveLoginEvent: " + isSuccess);
        if (isSuccess){
            mLoginBinding.loginBtnLogin.setProgress(100);
            MainActivity.startActivity(this);
        } else {
            showToast("登录失败，请检查用户名和密码是否有误");
            mLoginBinding.loginBtnLogin.setProgress(-1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
