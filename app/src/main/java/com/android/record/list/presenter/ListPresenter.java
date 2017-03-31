package com.android.record.list.presenter;

import android.content.Context;
import android.util.Log;

import com.android.record.base.dao.AppDaoManager;
import com.android.record.base.dao.DaoManager;
import com.android.record.base.util.Check;
import com.android.record.bean.GsonCard;
import com.android.record.bean.SwipeCardBean;
import com.android.record.common.AppConstant;
import com.android.record.common.dao.CommonDao;
import com.android.record.common.dao.RecordDaoHelper;
import com.android.record.common.dao.RecordDaoManager;
import com.android.record.list.CardEvent;
import com.android.record.list.api.ListService;
import com.android.record.list.contract.ListTaskContract;

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
 * Created by kiddo on 17-3-31.
 */

public class ListPresenter implements ListTaskContract.Presenter{
    public static final String TAG = ListPresenter.class.getSimpleName();
    private ListTaskContract.View mListTaskView;
    private Retrofit mRetrofit;
    private ListService mListService;

    public ListPresenter(ListTaskContract.View listTaskView) {
        this.mListTaskView = listTaskView;
        initNet();
        mListTaskView.setPresenter(this);
    }

    private void initNet(){
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        mListService = mRetrofit.create(ListService.class);
    }

    @Override
    public void subscribe() {
        Log.d(TAG, "subscribe: 开始执行");
    }

    @Override
    public void unsubscribe() {
        Log.d(TAG, "unsubscribe: 停止数据执行");
    }

    @Override
    public void sendCard() {
        mListService.sendCard("send", mListTaskView.getUserName(), mListTaskView.getPosition(),
                mListTaskView.getDescription(),System.currentTimeMillis())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GsonCard>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GsonCard gsonCard) {
                        Log.d(TAG, "onNext: GsonCard" + gsonCard.getCode());
                    }
                });
    }

    @Override
    public void getCard(final Context context) {
        mListService.getCard("get", mListTaskView.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<GsonCard>() {//将获取到的数据插入本地数据库
                    @Override
                    public void call(GsonCard gsonCard) {
                        List<SwipeCardBean> cardBeans = gsonCard.getData();
                        if (!Check.isEmpty(cardBeans)){
                            CommonDao.insert(context, cardBeans);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GsonCard>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GsonCard gsonCard) {
                        Log.d(TAG, "onNext: code" + gsonCard.getCode());
                        if (gsonCard.getCode() == 200){
                            EventBus.getDefault().post(new CardEvent(true, gsonCard.getData()));
                        }
                    }
                });
    }
}
