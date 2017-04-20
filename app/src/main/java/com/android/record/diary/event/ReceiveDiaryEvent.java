package com.android.record.diary.event;

import com.android.record.bean.Diary;

import java.util.List;

/**
 * Created by kiddo on 17-4-20.
 */

public class ReceiveDiaryEvent {
    private boolean success;
    private List<Diary> mData;

    public ReceiveDiaryEvent(boolean success, List<Diary> mData) {
        this.success = success;
        this.mData = mData;
    }

    public List<Diary> getmData() {
        return mData;
    }

    public void setmData(List<Diary> mData) {
        this.mData = mData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
