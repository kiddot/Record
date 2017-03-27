package com.android.record.login.event;

/**
 * Created by kiddo on 17-3-27.
 */

public class RegisterEvent {
    private int code; // 203:必填项为空 205：用户名已经被注册 200：注册成功

    public RegisterEvent(int code) {
        this.code = code;
    }

    public int  getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
