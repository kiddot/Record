package com.android.record.list.presenter;

import android.content.Context;
import android.util.Log;

import com.android.record.base.util.Check;
import com.android.record.base.util.CompressImagesHelper;
import com.android.record.bean.GsonCard;
import com.android.record.bean.SwipeCardBean;
import com.android.record.common.AppConstant;
import com.android.record.common.dao.CommonDao;
import com.android.record.list.event.GetCardEvent;
import com.android.record.list.api.ListService;
import com.android.record.list.contract.ListTaskContract;
import com.android.record.list.event.SendCardEvent;
import com.android.record.list.event.UploadImageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
    public void sendCard(final Context context) {
        mListService.sendCard("send", mListTaskView.getUserName(), mListTaskView.getPosition(),
                mListTaskView.getDescription(),System.currentTimeMillis())

                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<GsonCard>() {
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
                        Log.d(TAG, "onNext: GsonCard" + gsonCard.getCode());
                        if (gsonCard.getCode() == 200){
                            EventBus.getDefault().post(new SendCardEvent(true,null));
                        }
                    }
                });
    }

    @Override
    public void getCard(final Context context, final int position) {
        mListService.getCard("get", mListTaskView.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<GsonCard>() {//将获取到的数据插入本地数据库
                    @Override
                    public void call(GsonCard gsonCard) {
                        Log.d(TAG, "call: 开始录入本地数据库");
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
                            EventBus.getDefault().post(new GetCardEvent(true, gsonCard.getData(), position));
                        } else {
                            EventBus.getDefault().post(new GetCardEvent(false, gsonCard.getData(), position));
                        }
                    }
                });
    }

    @Override
    public void uploadPhoto(Context context, String path, final String username, final int position) {
//        List<String> originImage = new ArrayList<>();
//        originImage.add(path);
//        //TODO 压缩图片放在线程中
//        final String compressPath = CompressImagesHelper.compress(originImage).get(0);
//        File imageFile = new File(compressPath);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);
        final String[] compressPath = new String[1];
        Observable.just(path)
                .subscribeOn(Schedulers.computation())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        //压缩图片
                        Log.d(TAG, "call: 原图路径" + s);
                        compressPath[0] = CompressImagesHelper.compress(s);
                        Log.d(TAG, "call: 压缩图路径" + compressPath[0]);
                        return compressPath[0];
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        Log.d(TAG, "call: 压缩图路径" + s);
                        File imageFile = new File(s);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);
                        return mListService.upload("file",username, position, body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        CompressImagesHelper.clearCacheFiles(compressPath[0]);//清除压缩图
                        EventBus.getDefault().post(new UploadImageEvent(position));
                    }
                })
                .subscribe();
//        Call<ResponseBody> call = mListService.upload("file",username, position, body);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.d("Upload", "success:"+response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("Upload error:", t.getMessage());
//            }
//        });
    }

    @Override
    public void downloadPhoto(Context context) {

    }
}
