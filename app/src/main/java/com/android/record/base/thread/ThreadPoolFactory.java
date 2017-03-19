package com.android.record.base.thread;


import com.android.record.base.util.SingletonFactory;

/**
 *
 * Created by kiddo on 17-1-10.
 */

public class ThreadPoolFactory {

    public static ThreadPoolInterface getThreadPoolManager(){
        return SingletonFactory.getInstance(ThreadPoolManager.class);
    }
}
