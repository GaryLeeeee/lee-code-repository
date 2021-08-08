package com.garylee.repository.loadingcache;

import com.garylee.repository.LeeCommonCodeApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = LeeCommonCodeApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class LoadingCacheServiceTest {

    @Autowired
    private LoadingCacheService loadingCacheService;

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
    @Test
    public void testLoadingCache() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("print i:" + loadingCacheService.TEST_LOADING_CACHE.get("test"));
            Thread.sleep(1000);
        }

    }

    @Test
    public void testNullLoadingCache() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("print i:" + loadingCacheService.TEST_NULL_LOADING_CACHE.get("test").orElse(null));
            Thread.sleep(1000);
        }
    }
}