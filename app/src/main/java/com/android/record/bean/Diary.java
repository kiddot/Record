package com.android.record.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

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

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getEmotion() {
        return this.emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(hash = 558323644)
    public Diary(String title, String date, String week, String content,
            String emotion, long time) {
        this.title = title;
        this.date = date;
        this.week = week;
        this.content = content;
        this.emotion = emotion;
        this.time = time;
    }

    @Generated(hash = 112123061)
    public Diary() {
    }

}
