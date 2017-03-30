package com.android.record.login.model;

import android.content.Context;
import android.util.Log;

import com.android.record.bean.GsonUser;
import com.android.record.bean.User;
import com.android.record.common.AppConstant;
import com.android.record.common.sp.UserManager;
import com.android.record.login.api.LoginService;
import com.android.record.login.event.LoginEvent;
import com.android.record.login.event.RegisterEvent;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kiddo on 17-3-26.
 */

public class LoginAndRegister {
    public static final String TAG = LoginAndRegister.class.getSimpleName();
    private String mBaseUrl = AppConstant.Url;
    private Retrofit mRetrofit;
    private LoginService mLoginService;

    public LoginAndRegister() {
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        mLoginService = mRetrofit.create(LoginService.class);
    }

    public void login(final Context context, String format, String username, String password){
        //LoginService loginService = mRetrofit.create(LoginService.class);
        mLoginService.getUser(format, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<GsonUser, Boolean>() {
                    @Override
                    public Boolean call(GsonUser gsonUser) {
                        Log.d(TAG, "onNext: "+ gsonUser.getCode());
                        boolean isSuccess = gsonUser.getCode() == 200;
                        if (isSuccess){
                            saveUserInfo(context, gsonUser.getUser());
                            return true;
                        }
                        return false;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: 登录");
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new LoginEvent(false));
                    }

                    @Override
                    public void onNext(Boolean success) {
                        if (success){
                            EventBus.getDefault().post(new LoginEvent(true));
                        }else {
                            EventBus.getDefault().post(new LoginEvent(false));
                        }
                    }
                });

    }

    private void saveUserInfo(Context context, User user){
        LoginInfoSave.trySaveUserInfo(context, user);
    }

    public void register(String format, String username, String password, String email, String sex, String phone){
        mLoginService.addUser(format,username,password,sex,email,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GsonUser>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: 注册");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GsonUser gsonUser) {
                        int code = gsonUser.getCode();
                        Log.d(TAG, "onNext: "+ code);
                        if (code == 200){
                            //成功
                            EventBus.getDefault().post(new RegisterEvent(200));
                        }else if (code == 203){
                            //当前必填项有为空的，
                            Log.d(TAG, "onNext: 当前必填项有为空的");
                            EventBus.getDefault().post(new RegisterEvent(203));
                        } else if (code == 205) {
                            //当前用户名已经被注册
                            Log.d(TAG, "onNext: 当前用户名已经被注册");
                            EventBus.getDefault().post(new RegisterEvent(205));
                        }
                    }
                });
    }
}
