package com.android.record.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by kiddo on 17-4-19.
 */
@Entity
public class Diary {
    @SerializedName("title")
    private String title;//标题
    @SerializedName("date")
    private String date;//日期
    @SerializedName("week")
    private String week;//星期
    @SerializedName("content")
    private String content;//内容
    @SerializedName("emotion")
    private String emotion;//心情
    @SerializedName("time")
    private long time;//时间

    @Transient
    private String username;

}
