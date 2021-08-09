package com.garylee.repository.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author: GaryLeeeee
 * @date: 2021-08-08 20:44
 * @description: redission分布式锁帮忙类
 **/
public class RedissonLockUtil {

    private static DistributedLocker redissonLock;


    public static DistributedLocker getRedissonLock() {
        return redissonLock;
    }

    public static void setRedissonLock(DistributedLocker locker) {
        redissonLock = locker;
    }

    public static void lock(String lockKey) {
        redissonLock.lock(lockKey);
    }

    public static void unlock(String lockKey) {
        redissonLock.unlock(lockKey);
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout
     * @param timeUnit
     */
    public static void lock(String lockKey, long timeout, TimeUnit timeUnit) {
        redissonLock.lock(lockKey, timeout, timeUnit);
    }

    /**
     * 是否锁住
     *
     * @param lockKey
     * @return
     */
    public static boolean isLocked(String lockKey) {
        return redissonLock.isLocked(lockKey);
    }

    /**
     * 是否被当前线程占用
     *
     * @param lockKey
     * @return
     */
    public static boolean isHeldByCurrentThread(String lockKey) {
        return redissonLock.isHeldByCurrentThread(lockKey);
    }
}
