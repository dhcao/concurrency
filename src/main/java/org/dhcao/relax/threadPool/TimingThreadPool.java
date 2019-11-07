package org.dhcao.relax.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * 扩展线程池：
 * 利用beforeExecute和afterExecute来为线程增加统计功能
 * 记录日志和计时等功能
 * @Author: dhcao
 * @Version: 1.0
 */
public class TimingThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final Logger log = Logger.getLogger("TimingThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 记录任务执行开始时间
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info(String.format("Thread %s: start %s", t, r));
        startTime.set(System.nanoTime());
    }


    @Override
    protected void afterExecute(Runnable r, Throwable t) {

        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.info(String.format("Thread %s: end %s, time = %dns", t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }

    }

    @Override
    protected void terminated() {

        try {
            log.info(String.format("Terminated: avg time = %dns",totalTime.get()/numTasks.get()));
        } finally {
            super.terminated();
        }
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        ThreadPoolExecutor exec = new TimingThreadPool(3,3,20,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));

        for (int i = 0; i <6; i++) {
            exec.execute(() -> System.out.println("这是干啥呢"));
        }

        exec.shutdown();
    }
}
