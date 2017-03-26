package com.android.record.login.model;

import android.util.Log;

import com.android.record.base.util.Check;
import com.android.record.bean.GsonUser;
import com.android.record.bean.user;
import com.android.record.common.AppConstant;
import com.android.record.databinding.ActivityLoginBinding;
import com.android.record.login.api.LoginService;
import com.android.record.login.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kiddo on 17-3-26.
 */

public class LoginModel {
    public static final String TAG = LoginModel.class.getSimpleName();
    private String mBaseUrl = AppConstant.Url;
    private Retrofit mRetrofit;
    private ActivityLoginBinding mLoginBinding;

    public LoginModel() {
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    public void login(String format, String username, String password){
        LoginService loginService = mRetrofit.create(LoginService.class);
        loginService.getUser(format, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GsonUser>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new LoginEvent(false));
                    }

                    @Override
                    public void onNext(GsonUser gsonUser) {
                        Log.d(TAG, "onNext: "+ gsonUser.getCode());
                        if (gsonUser.getUser() != null){
                            EventBus.getDefault().post(new LoginEvent(true));
                        }else {
                            EventBus.getDefault().post(new LoginEvent(false));
                        }
                    }
                });

    }
}
