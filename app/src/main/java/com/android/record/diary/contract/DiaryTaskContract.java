package com.android.record.diary.contract;

import android.content.Context;

import com.android.record.base.mvp.BasePresenter;
import com.android.record.base.mvp.BaseView;

/**
 * Created by kiddo on 17-4-13.
 */

public class DiaryTaskContract {

    public interface View extends BaseView<Presenter>{
        String getContent();
        String getUserName();
        String getDiaryTime();
        String getTitle();
    }

    public interface Presenter extends BasePresenter{
        void getDiary(Context context);
        void sendDiary();
    }
}
