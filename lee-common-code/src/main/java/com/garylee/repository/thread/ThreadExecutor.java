package com.garylee.repository.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: GaryLeeeee
 * @date: 2021-07-22 21:18
 * @description: 多线程执行器
 **/
public class ThreadExecutor {

    private static ThreadExecutor instance = new ThreadExecutor();

    private static ThreadExecutor getInstance() {
        return instance;
    }

    /**
     * 系统cpu数
     */
    public static final int SYSTEM_DEFAULT_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static int getProcessCount() {
        return Math.max(SYSTEM_DEFAULT_PROCESSORS, 1);
    }

    /**
     * 默认执行器
     */
    private ThreadPoolExecutor defaultThreadExecutor;

    private ThreadExecutor() {
        int size = getProcessCount() <= 1 ? 4 : getProcessCount() * 2;
        defaultThreadExecutor = createThreadPoolExecutor("_common", size, size, true);
    }

    /**
     * 创建多线程执行池子，请自行管理创建的线程池对象。当前类不管理用户创建的线程池对象。
     *
     * @param name            线程池名称
     * @param corePoolSize    核心池子大小
     * @param maximumPoolSize 最大池子大小
     * @param isDaemon        是否守护线程(如果是的话，则当用户进程关闭时，守护线程会自动消亡)
     * @return
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(String name, int corePoolSize, int maximumPoolSize, boolean isDaemon) {
        return new NameableThreadPoolExecutor(name, corePoolSize, maximumPoolSize, isDaemon);
    }

    /**
     * 默认的线程执行器，不获得返回结果
     * 如果存在以下两种任务，请调用 createThreadPoolExecutor 创建单独线程池执行
     * 1、执行的任务中存在长时间的任务，如：线程中存在sleep，或者超长时间等待远程调用返回结果的请求。(超长时间处理是可以使用线程池的)
     * 2、永久占用线程的任务，如：监控线程。
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        ThreadExecutor.getInstance().defaultThreadExecutor.execute(runnable);
    }

    /**
     * 在线程池中执行，可以获得返回结果
     * Future<String> future = ThreadExecutor.submit(new Callable<String>() {
     * public String call() throws Exception { return "test"; }
     * );
     * future.get();//等待线程执行完毕，并且返回"test"
     *
     * @param callable 回调函数
     * @param <V>      回调函数返回的数据类型
     * @return
     */
    public static <V> Future<V> submit(Callable<V> callable) {
        return ThreadExecutor.getInstance().defaultThreadExecutor.submit(callable);
    }
}
