package com.garylee.repository.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author: GaryLeeeee
 * @date: 2021-08-08 20:45
 * @description:
 **/
public interface DistributedLocker {
    /**
     * 加锁
     *
     * @param lockKey
     */
    void lock(String lockKey);

    /**
     * 加锁
     *
     * @param lockKey
     * @param timeout
     * @param timeUnit
     */
    void lock(String lockKey, long timeout, TimeUnit timeUnit);

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @return 拿锁是否成功
     */
    boolean tryLock(String lockKey);

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param timeout
     * @param timeUnit
     * @return 拿锁是否成功
     */
    boolean tryLock(String lockKey, long timeout, TimeUnit timeUnit);

    /**
     * 释放锁
     *
     * @param lockKey
     */
    void unlock(String lockKey);

    /**
     * 判断锁是否可用
     *
     * @param lockKey
     * @return
     */
    boolean isLocked(String lockKey);

    /**
     * 是否被当前线程占用
     *
     * @param lockKey
     * @return
     */
    boolean isHeldByCurrentThread(String lockKey);
}
