package com.android.record.base.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 * Created by kiddo on 17-1-10.
 */

public class ThreadPoolManager implements ThreadPoolInterface {
    public static final String TAG =ThreadPoolManager.class.getSimpleName();

    private final Map<Integer , BaseThreadPool> threadPoolMap = new HashMap<>();//考虑使用SparseArray

    public static Handler handler = new Handler(Looper.getMainLooper());

    public static ThreadPoolManager mThreadPoolManager;

    /**
     * 单利模式
     * @return
     */
    public static ThreadPoolManager getInstance() {
        if (mThreadPoolManager == null) {
            synchronized (ThreadPoolManager.class) {
                if (mThreadPoolManager == null) {
                    mThreadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return mThreadPoolManager;
    }


    @Override
    public <T> void addTask(final Tasker<T> tasker) {
        if (tasker != null){
            BaseThreadPool threadPool = null;
            synchronized (threadPoolMap){
                threadPool = threadPoolMap.get(tasker.getThreadPoolType());
                if (threadPool == null){
                    threadPool = new BaseThreadPool(ThreadPoolParams.getParams(tasker.getThreadPoolType()));
                    threadPoolMap.put(tasker.getThreadPoolType() , threadPool);
                }
            }

            Callable<T> call = new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return postResult(tasker , tasker.doInBackground());
                }
            };

            FutureTask<T> task = new FutureTask<T>(call){
                @Override
                protected void done() {
                    try {
                        get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        throw new RuntimeException("An error occured while executing doInBackground()", e.getCause());
                    }
                }
            };
            tasker.setTask(task);
            threadPool.execute(task);
        }
    }

    @Override
    public BaseThreadPool getThreadPool(int threadPoolType) {
        BaseThreadPool threadPool = null;
        synchronized (threadPoolMap){
            threadPool = threadPoolMap.get(threadPoolType);
            if (threadPool == null) {
                threadPool = new BaseThreadPool(ThreadPoolParams.getParams(threadPoolType));
            }
        }
        return threadPool;
    }

    @Override
    public <T> boolean removeTask(Tasker<T> tasker) {
        BaseThreadPool threadPool = threadPoolMap.get(tasker.getThreadPoolType());

        if (threadPool != null){
            return threadPool.remove(tasker.getTask());
        }
        return false;
    }

    @Override
    public void stopAllTask() {
        if (threadPoolMap.size() != 0){
            for (Integer key : threadPoolMap.keySet()){
                BaseThreadPool threadPool = threadPoolMap.get(key);

                if (threadPool != null){
                    threadPool.shutdown();
                }
            }

            threadPoolMap.clear();
        }
    }

    /**
     * 将子线程结果传递到UI线程
     *
     * @param tasker
     * @param result
     * @return
     */
    private <T> T postResult(final Tasker<T> tasker, final T result) {
        tasker.status = Tasker.Status.FINISHED;
        if (tasker.getThreadPoolType() == ThreadPoolConst.THREAD_TYPE_OTHERS) {
            tasker.onPostExecute(result);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tasker.onPostExecute(result);
                }
            });
        }
        return result;
    }

    /**
     * 将子线程结果传递到UI线程
     *
     * @param tasker
     * @return
     * @mainThread
     */
    private void postCancel(final Tasker tasker) {
        if (tasker.getThreadPoolType() == ThreadPoolConst.THREAD_TYPE_OTHERS) {
            tasker.onCanceled();
        } else {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    tasker.onCanceled();
                }
            });
        }
    }
}
