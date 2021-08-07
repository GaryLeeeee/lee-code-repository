package com.garylee.repository.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: GaryLeeeee
 * @date: 2021-08-07 18:27
 * @description: 根据名称隔离的线程构造工厂
 **/
@Slf4j
public class NameableThreadFactory implements ThreadFactory {

    /**
     * 线程组
     */
    private final ThreadGroup group;

    /**
     * 线程名前缀
     */
    private final String namePrefix;

    /**
     * 线程ID
     */
    private final AtomicInteger threadId;

    /**
     * 是否为守护线程
     */
    private final boolean isDaemon;

    public NameableThreadFactory(String name, boolean isDaemon) {
        SecurityManager s = System.getSecurityManager();
        this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = name;
        this.threadId = new AtomicInteger(0);
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadId.getAndIncrement());
        t.setUncaughtExceptionHandler((thread, throwable) -> {
            log.error("common ThreadPool {} got exception", thread, throwable);
        });
        t.setDaemon(isDaemon);

        //The default priority that is assigned to a thread.
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }

        return t;
    }
}
