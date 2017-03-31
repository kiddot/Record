package com.android.record.list.contract;

import android.content.Context;

import com.android.record.base.mvp.BasePresenter;
import com.android.record.base.mvp.BaseView;

/**
 * Created by kiddo on 17-3-31.
 */

public class ListTaskContract {

    public interface View extends BaseView<Presenter>{
        String getDescription();
        String getUserName();
        int getPosition();
    }

    public interface Presenter extends BasePresenter{
        void sendCard();
        void getCard(Context context);
    }
}
