package com.garylee.repository.loadingcache;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @author: GaryLeeeee
 * @date: 2021-07-21 22:55
 * @description: 本地缓存(guava - cache)
 **/
public class LoadingCacheDemo {

    /**
     * 刷新间隔
     */
    private static final int REFRESH_INTERVAL = 5;

    /**
     * refreshAfterWrite：当缓存项上一次更新操作之后的多久会被刷新。
     * expireAfterWrite：当缓存项在指定的时间段内没有更新就会被回收（移除key），需要等待获取新值才会返回。
     * expireAfterAccess: 当缓存项在指定的时间段内没有被读或写就会被回收。
     */
    private final LoadingCache<String, String> TEST_LOADING_CACHE = CacheBuilder.newBuilder()
            .refreshAfterWrite(REFRESH_INTERVAL, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String str) throws Exception {
                    return getStringAndPrint(str);
                }
            });

    /**
     * 测试用数据源
     */
    private String getStringAndPrint(String str) {
        System.out.println("now:" + System.currentTimeMillis() + ",str:" + str);
        return str;
    }

    /**
     * 测试：
     * 设置refreshAfterWrite为5s，在10s内每秒去请求一次，理论上是5秒内都是读的本地缓存，5秒后再次请求会重新刷新
     * <p>
     * 结果：
     * now:1626880020934,str:test
     * print i:test
     * print i:test
     * print i:test
     * print i:test
     * print i:test
     * now:1626880025939,str:test
     * print i:test
     * print i:test
     * print i:test
     * print i:test
     * print i:test
     */
    public static void main(String[] args) throws Exception {
        LoadingCacheDemo demo = new LoadingCacheDemo();

        for (int i = 0; i < 10; i++) {
            System.out.println("print i:" + demo.TEST_LOADING_CACHE.get("test"));
            Thread.sleep(1000);
        }

    }
}
