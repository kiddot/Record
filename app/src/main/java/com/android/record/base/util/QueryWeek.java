package com.android.record.base.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kiddo on 17-4-21.
 */

public class QueryWeek {

    public static String getWeek(Date date){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
