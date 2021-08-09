package com.garylee.repository.lock;

import com.garylee.repository.LeeCommonCodeApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = LeeCommonCodeApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class RedissonLockUtilTest {

    @Test
    public void lock() {
        String lockKey = "test_lock_key";
        try {
            RedissonLockUtil.lock(lockKey);
            System.out.println(1);
        } catch (Exception e) {
            log.error("lock err", e);
        } finally {
            RedissonLockUtil.unlock(lockKey);
        }
    }
}