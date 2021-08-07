package com.garylee.repository.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: GaryLeeeee
 * @date: 2021-08-07 19:36
 * @description:
 **/
@Slf4j
public class ThreadRejectedExecutionHandler implements RejectedExecutionHandler {
    private final String poolName;

    public ThreadRejectedExecutionHandler(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (executor.isTerminating() || executor.isTerminated() || executor.isShutdown()) {
            log.error("线程池-{}执行拒绝策略，原因：线程池已关闭!", poolName);
            throw new RejectedExecutionException();
        }

        log.error("线程池-{}执行拒绝策略，原因：线程数已达最大值，且队列已满！", poolName);
        throw new RejectedExecutionException();
    }
}
