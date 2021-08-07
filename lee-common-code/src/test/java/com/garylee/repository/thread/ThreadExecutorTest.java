package com.garylee.repository.thread;

import com.garylee.repository.LeeCommonCodeApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest(classes = LeeCommonCodeApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadExecutorTest {
    @Test
    public void execute() {
        //由于execute是异步的，所以会先打印end再打印test:_common0
        ThreadExecutor.execute(() -> {
            log.info("test:{}", Thread.currentThread().getName());

        });

        log.info("end");
    }

    @Test
    public void submit() throws ExecutionException, InterruptedException {
        //submit是会返回结果的
        Future<String> future = ThreadExecutor.submit(() -> {
            return "test";
        });

        log.info("step1");
        //阻塞住等线程执行完
        String result = future.get();
        log.info("step2 result:{}", result);
    }

    private ThreadPoolExecutor threadPoolExecutor = ThreadExecutor.createThreadPoolExecutor("_test",
            ThreadExecutor.getProcessCount(),
            ThreadExecutor.getProcessCount() * 2, true);

    @Test
    public void createThreadPoolExecutor() {
        threadPoolExecutor.execute(() -> {
            log.info("execute poolName:{}", Thread.currentThread().getName());
        });

        log.info("end");
    }
}