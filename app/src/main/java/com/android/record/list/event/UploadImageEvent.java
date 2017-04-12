package com.android.record.list.event;

/**
 * Created by kiddo on 17-4-12.
 */

public class UploadImageEvent {
    private int position;

    public UploadImageEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
