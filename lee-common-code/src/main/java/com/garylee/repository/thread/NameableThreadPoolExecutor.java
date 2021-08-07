package com.garylee.repository.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: GaryLeeeee
 * @date: 2021-08-07 18:17
 * @description: 根据名称隔离的多线程执行器
 **/
@Slf4j
public class NameableThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * 线程池名称
     */
    private String poolName;

    /**
     * 活跃线程数
     */
    int ac = 0;

    /**
     * 当前所有线程消耗的时间
     */
    private AtomicLong totalCostTime = new AtomicLong();

    /**
     * 当前执行的线程总数
     */
    private AtomicLong totalTasks = new AtomicLong();

    /**
     * 最短执行时间
     */
    private long minCostTime;

    /**
     * 最长执行时间
     */
    private long maxCostTime;

    public NameableThreadPoolExecutor(String poolName, int corePoolSize, int maximunPoolSize, boolean isDaemon) {
        this(poolName, corePoolSize, maximunPoolSize, Long.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedTransferQueue<>(),
                new NameableThreadFactory(poolName, isDaemon), new ThreadRejectedExecutionHandler(poolName));
    }

    /**
     * 带名称的构造方法
     *
     * @param poolName
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param handler
     */
    public NameableThreadPoolExecutor(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.poolName = poolName;
    }
}
