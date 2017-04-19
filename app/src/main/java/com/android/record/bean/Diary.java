package com.android.record.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by kiddo on 17-4-19.
 */
@Entity
public class Diary {
    private String title;//标题
    private String date;//日期
    private String week;//星期
    private String content;//内容
    private String emotion;//心情
    private String time;//时间

    @Transient
    private String username;
}
