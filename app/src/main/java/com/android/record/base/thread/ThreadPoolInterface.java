package com.android.record.base.thread;

/**
 * Created by kiddo on 17-1-10.
 */

public interface ThreadPoolInterface {

    /**
     * 往线程池中增加一个线程任务
     * @param tasker 线程任务
     */
    public <T> void addTask(Tasker<T> tasker);

    /**
     *
     * @description:获取指定类型的线程池，如果不存在则创建
     * @param @param ThreadPoolType
     * @return BaseThreadPool
     * @throws
     */
    public BaseThreadPool getThreadPool(int threadPoolType);

    /**
     * 从线程队列中移除一个线程任务
     * @param tasker 线程任务
     * @return 是否移除成功
     */
    public <T> boolean removeTask(Tasker<T> tasker);

    /**
     * 停止所有任务
     */
    public void stopAllTask();

}
