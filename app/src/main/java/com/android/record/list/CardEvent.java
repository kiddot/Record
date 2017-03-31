package com.android.record.list;

import com.android.record.bean.SwipeCardBean;

import java.util.List;

/**
 * Created by kiddo on 17-3-31.
 */

public class CardEvent {

    private boolean isSuccess;

    private List<SwipeCardBean> mCardList;

    public List<SwipeCardBean> getmCardList() {
        return mCardList;
    }

    public void setmCardList(List<SwipeCardBean> mCardList) {
        this.mCardList = mCardList;
    }

    public CardEvent(boolean isSuccess, List<SwipeCardBean> list) {
        this.isSuccess = isSuccess;
        this.mCardList = list;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
