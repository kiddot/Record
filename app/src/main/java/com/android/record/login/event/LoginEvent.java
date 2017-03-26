package com.android.record.login.event;

/**
 * Created by kiddo on 17-3-26.
 */

public class LoginEvent {
    private boolean isSuccess;

    public LoginEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
