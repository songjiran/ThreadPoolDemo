package com.sjr.threadpooldemo.util;


/**
 * Created by 宋家任 on 2016/5/29 22:07.
 * 线程池工厂，生产线程池
 */
public class ThreadPoolFactory {
    private static ThreadPoolProxy commonThreadPool;//获取一个普通的线程池实例
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();//获得CPU数量
    private static final int COMMOM_CORE_POOL_SIZE = CPU_COUNT+1;//根据CPU核数来设定核心线程数
    private static final int COMMON_MAXPOOL_SIZE = CPU_COUNT*2+1;//最大线程池数
    private static final long COMMON_KEEP_LIVE_TIME = 1L;//持续时间

    public static ThreadPoolProxy getCommonThreadPool() {
        if (commonThreadPool == null)
            synchronized (ThreadPoolFactory.class) {
                if (commonThreadPool == null)
                    commonThreadPool = new ThreadPoolProxy(COMMOM_CORE_POOL_SIZE,
                            COMMON_MAXPOOL_SIZE, COMMON_KEEP_LIVE_TIME);
            }
        return commonThreadPool;
    }
}
