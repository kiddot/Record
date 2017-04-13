package com.android.record.diary.presenter;

import android.util.Log;

import com.android.record.common.AppConstant;
import com.android.record.diary.api.DiaryService;
import com.android.record.diary.contract.DiaryTaskContract;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kiddo on 17-4-13.
 */

public class DiaryPresenter implements DiaryTaskContract.Presenter{
    public static final String TAG = DiaryPresenter.class.getSimpleName();
    private DiaryTaskContract.View mDiaryTaskView;
    private Retrofit mRetrofit;
    private DiaryService mDiaryService;

    public DiaryPresenter(DiaryTaskContract.View diaryTaskView) {
        this.mDiaryTaskView = diaryTaskView;
        initNet();
        mDiaryTaskView.setPresenter(this);
    }

    private void initNet() {
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        mDiaryService = mRetrofit.create(DiaryService.class);
    }

    @Override
    public void getDiary() {

    }

    @Override
    public void sendDiary() {

    }

    @Override
    public void subscribe() {
        Log.d(TAG, "subscribe: 开始执行");
    }

    @Override
    public void unsubscribe() {
        Log.d(TAG, "unsubscribe: 停止数据执行");
    }
}
