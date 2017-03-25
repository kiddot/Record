package com.android.record.login.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.android.record.R;
import com.android.record.base.componet.BaseActivity;
import com.android.record.databinding.ActivityLoginBinding;

/**
 * Created by kiddo on 17-3-25.
 */

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding mLoginBinding;

    @Override
    protected void getLayoutBinding() {
        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
