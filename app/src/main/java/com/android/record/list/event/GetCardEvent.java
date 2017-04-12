package com.android.record.list.event;

import com.android.record.bean.SwipeCardBean;

import java.util.List;

/**
 * Created by kiddo on 17-3-31.
 */

public class GetCardEvent {

    private boolean isSuccess;

    private List<SwipeCardBean> mCardList;

    private int position;

    public List<SwipeCardBean> getmCardList() {
        return mCardList;
    }

    public void setmCardList(List<SwipeCardBean> mCardList) {
        this.mCardList = mCardList;
    }

    public GetCardEvent(boolean isSuccess, List<SwipeCardBean> list, int position) {
        this.isSuccess = isSuccess;
        this.mCardList = list;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
