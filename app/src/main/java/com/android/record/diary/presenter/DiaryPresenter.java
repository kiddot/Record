package com.android.record.diary.presenter;

import android.content.Context;
import android.util.Log;

import com.android.record.base.util.Check;
import com.android.record.bean.Diary;
import com.android.record.bean.GsonDiary;
import com.android.record.common.AppConstant;
import com.android.record.common.dao.CommonDao;
import com.android.record.common.sp.UserManager;
import com.android.record.diary.api.DiaryService;
import com.android.record.diary.contract.DiaryTaskContract;
import com.android.record.diary.event.ReceiveDiaryEvent;
import com.android.record.diary.event.SaveDiaryEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
    public void getDiary(final Context context) {
        Log.d(TAG, "getDiary: " + mDiaryTaskView.getUserName());
        mDiaryService.getDiary("get",mDiaryTaskView.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<GsonDiary>() {
                    @Override
                    public void call(GsonDiary gsonDiary) {
                        List<Diary> diaryList = gsonDiary.getData();
                        if (!Check.isEmpty(diaryList)){
                            CommonDao.insert(context, diaryList);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GsonDiary>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GsonDiary gsonDiary) {
                        Log.d(TAG, "onNext: " + gsonDiary.getCode());
                        if (gsonDiary.getCode() == 200){
                            EventBus.getDefault().post(new ReceiveDiaryEvent(true, gsonDiary.getData()));
                        } else {
                            EventBus.getDefault().post(new ReceiveDiaryEvent(false, gsonDiary.getData()));
                        }
                    }
                });
    }

    @Override
    public void saveDiary(final Context context) {
        mDiaryService.saveDiary("save", mDiaryTaskView.getUserName(),
                mDiaryTaskView.getDiaryTitle(), mDiaryTaskView.getDiaryDate(),
                mDiaryTaskView.getWeek(), mDiaryTaskView.getContent(),
                mDiaryTaskView.getEmotion(), System.currentTimeMillis())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnNext(new Action1<GsonDiary>() {
                    @Override
                    public void call(GsonDiary diary) {
                        if (diary != null){
                            Log.d(TAG, "call: " + diary.getCode());
                            //TODO 保存到本地数据库
                            //UserManager.getInstance(context).getDaoManager().insert(diary.getData().get(0));
                        }
                    }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<GsonDiary>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(GsonDiary gsonDiary) {
                    if (gsonDiary.getCode() == 200){
                        Log.d(TAG, "onNext: 200 save diary");
                        Diary diary = new Diary(mDiaryTaskView.getDiaryTitle(), mDiaryTaskView.getDiaryDate(),
                                mDiaryTaskView.getWeek(), mDiaryTaskView.getContent(),
                                mDiaryTaskView.getEmotion(), System.currentTimeMillis());
                        EventBus.getDefault().post(new SaveDiaryEvent(true, diary));
                    } else {
                        EventBus.getDefault().post(new SaveDiaryEvent(false, null));
                    }
                }
            });

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
