package com.sjr.threadpooldemo.util;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 宋家任 on 2016/5/29 22:16.
 * 线程池代理
 */
public class ThreadPoolProxy {
    private ThreadPoolExecutor executor;
    private int corePollSize;//核心线程数
    private int maximumPoolSize;//最大线程数
    private long keepAliveTime;//非核心线程持续时间

    public ThreadPoolProxy(int corePollSize, int maximumPoolSize, long keepAliveTime) {
        this.corePollSize = corePollSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    /**
     * 单例，产生线程
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void getThreadPoolProxyInstant() {
        if (executor == null)
            synchronized (ThreadPoolProxy.class) {
                if (executor == null) {
                    BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<Runnable>(2);//表示无界队列，任务数最大为IntegerMAX
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();//默认的线程工厂
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();//拒绝任务时的处理策略，直接抛异常
                    executor = new ThreadPoolExecutor(corePollSize,
                            maximumPoolSize,
                            keepAliveTime,
                            TimeUnit.SECONDS,
                            workQueue,
                            threadFactory,
                            handler);
                }
            }

    }

    /**
     * 执行任务
     *
     * @param task
     */
    public void excute(Runnable task) {
        getThreadPoolProxyInstant();
        executor.execute(task);
    }

    /**
     * 删除任务
     *
     * @param task
     */
    public boolean remove(Runnable task) {
        getThreadPoolProxyInstant();
        boolean remove = executor.remove(task);
        return remove;
    }
}
