package com.garylee.repository.loadingcache;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author: GaryLeeeee
 * @date: 2021-07-21 22:55
 * @description: 本地缓存(guava - cache)
 **/
@Service
@Slf4j
public class LoadingCacheService {

    /**
     * 刷新间隔
     */
    private static final int REFRESH_INTERVAL = 5;

    /**
     * refreshAfterWrite：当缓存项上一次更新操作之后的多久会被刷新。
     * expireAfterWrite：当缓存项在指定的时间段内没有更新就会被回收（移除key），需要等待获取新值才会返回。
     * expireAfterAccess: 当缓存项在指定的时间段内没有被读或写就会被回收。
     */
    public final LoadingCache<String, String> TEST_LOADING_CACHE = CacheBuilder.newBuilder()
            .refreshAfterWrite(REFRESH_INTERVAL, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String str) throws Exception {
                    return getStringAndPrint(str);
                }
            });

    /**
     * 空值处理
     */
    public final LoadingCache<String, Optional<String>> TEST_NULL_LOADING_CACHE = CacheBuilder.newBuilder()
            .refreshAfterWrite(REFRESH_INTERVAL, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Optional<String>>() {
                @Override
                public Optional<String> load(String str) throws Exception {
                    return Optional.ofNullable(null);
                }
            });

    /**
     * 测试用数据源
     */
    private String getStringAndPrint(String str) {
        System.out.println("now:" + System.currentTimeMillis() + ",str:" + str);
        return str;
    }
}
