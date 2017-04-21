package com.android.record.diary.event;

import com.android.record.bean.Diary;


/**
 * Created by kiddo on 17-4-21.
 */

public class SaveDiaryEvent {
    private boolean success;
    private Diary diary;

    public SaveDiaryEvent(boolean success, Diary diary) {
        this.success = success;
        this.diary = diary;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
