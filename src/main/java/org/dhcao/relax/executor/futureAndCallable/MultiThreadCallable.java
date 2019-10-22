package org.dhcao.relax.executor.futureAndCallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class MultiThreadCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService exe = Executors.newFixedThreadPool(2);

        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {

                // 标记2： 会发现单线程中，等待的线程是main；
                System.out.println(Thread.currentThread().getName() + " ：等待3秒");
                SECONDS.sleep(3);

                return "hhhh";
            }
        };

        // 标记1：FutureTask是要run方法启动的
        Future<String> future =  exe.submit(call);

        System.out.println("kkkkk");

        // 会发现在等待3秒过程中，get()是得不到数据的，所以主线程会等待
        System.out.println(future.get());
    }
}
