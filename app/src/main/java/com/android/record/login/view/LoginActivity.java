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
import com.android.record.login.model.LoginModel;
import com.dd.processbutton.iml.ActionProcessButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by kiddo on 17-3-25.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    public static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding mLoginBinding;
    private LoginModel mLoginModel;

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
        mLoginModel = new LoginModel();
        mLoginBinding.loginBtnLogin.setOnClickListener(this);
        mLoginBinding.loginBtnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login :
                String username = mLoginBinding.loginEtUsername.getText().toString();
                String password = mLoginBinding.loginEtPassword.getText().toString();
                if (Check.isEmpty(username) || Check.isEmpty(password)){
                    showToast("请检查账号或者密码是否为空");
                }
                mLoginModel.login("login", username, password);
                mLoginBinding.loginBtnLogin.setProgress(50);
                Log.d(TAG, "用户正在尝试登录  ----ing ");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveLoginEvent(LoginEvent event){
        boolean isSuccess = event.isSuccess();
        Log.d(TAG, "onReceiveLoginEvent: " + isSuccess);
        if (isSuccess){
            mLoginBinding.loginBtnLogin.setProgress(100);
        } else {
            mLoginBinding.loginBtnLogin.setProgress(-1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
