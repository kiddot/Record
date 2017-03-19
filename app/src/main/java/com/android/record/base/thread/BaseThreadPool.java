package com.android.record.base.thread;

import android.os.Build;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kiddo on 17-1-10.
 */

public class BaseThreadPool extends ThreadPoolExecutor {

    public BaseThreadPool(ThreadPoolParams threadPoolParams) {


        super(threadPoolParams.getCorePoolSize(),
                threadPoolParams.getMaximumPoolSize(),
                threadPoolParams.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(threadPoolParams.getMaximumPoolSize()),//线程池所使用的缓冲队列
                new CallerRunsPolicy());//线程池对拒绝任务的处理策略,重试添加当前的任务，ta会自动重复调用execute()方法

        /**
         * ??????
         */
        if (Build.VERSION.SDK_INT > 10) {
            this.allowCoreThreadTimeOut(threadPoolParams.isAllowCoreThreadTimeOut());
        }
    }
}
