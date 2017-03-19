package com.android.record.base.thread;

/**
 * Created by kiddo on 17-1-10.
 */

public class ThreadPoolConst {
    /**
     * 普通工作线程池
     */
    public static final int THREAD_TYPE_WORK = 0;

    /**
     * 接口请求线程池
     */
    public static final int THREAD_TYPE_SIMPLE_HTTP = 1;

    /**
     * 文件传输线程池
     */
    public static final int THREAD_TYPE_FILE_HTTP = 2;

    /**
     * 其他线程池
     * 注意，如果是此线程类型，执行的响应操作都在本身线程通知，而不在主线程上。
     */
    public static final int THREAD_TYPE_OTHERS = 3;

    /**
     * 空闲线程存活时间,5秒
     */
    public static final long KEEP_ALIVE_TIME = 5000;

    /**
     * 有界队列长度
     */
    public final static int DEFAULT_WORK_QUEUE_SIZE = 10;
}
