package com.sjr.threadpooldemo.util;

import android.os.Handler;

/**
 * Created by 宋家任 on 2016/5/29 22:22.
 */
public class ThreadPoolUtil {
    /**
     * 在非UI线程中执行
     *
     * @param task
     */
    public static void runTaskInThread(Runnable task) {
        ThreadPoolFactory.getCommonThreadPool().excute(task);
    }

    public static Handler handler = new Handler();

    /**
     * 在UI线程中执行
     *
     * @param task
     */
    public static void runTaskInUIThread(Runnable task) {
        handler.post(task);
    }

    /**
     * 移除线程池中线程
     *
     * @param task
     */
    public static boolean removeTask(Runnable task) {
        boolean remove = ThreadPoolFactory.getCommonThreadPool().remove(task);
        return remove;
    }
}
