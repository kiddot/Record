package com.android.record.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by kiddo on 17-3-24.
 */
@Entity
public class user {
    private String userName;
    private String password;
    private String sex;
    private String phone;
    private String email;
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Generated(hash = 1017129979)
    public user(String userName, String password, String sex, String phone,
                String email) {
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
    }
    @Generated(hash = 591315204)
    public user() {
    }
}
