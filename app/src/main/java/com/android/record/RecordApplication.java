package com.android.record;

import android.app.Application;
import android.content.Context;

/**
 * Created by kiddo on 17-3-19.
 */

public class RecordApplication extends Application{
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
