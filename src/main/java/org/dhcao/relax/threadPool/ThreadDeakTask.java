package org.dhcao.relax.threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程饥饿死锁
 * @Author: dhcao
 * @Version: 1.0
 */
public class ThreadDeakTask {

    static ExecutorService exec = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {

            Future<?> header = exec.submit(() -> System.out.println("加载header.html"));
            Future<?> footer = exec.submit(() -> System.out.println("加载footer.html"));


            System.out.println("ssss");

            return header.get() + " body " + footer.get();
        }
    }

    /**
     * 当我们在exec中提交RenderPageTask时会发生死锁。
     * 因为第一个任务等待其他任务完成。除非线程池够大，否则会死锁。
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        exec.submit(ThreadDeakTask::new);
    }

}
