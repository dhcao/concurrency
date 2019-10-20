package org.dhcao.relax.latchAndFutureTask;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 测试闭锁：countDownLatch
 * @Author: dhcao
 * @Version: 1.0
 */
public class CountDownLatchTest {

    /**
     * 测试并发执行任务的时间(包含了创建线程的时间)
     * @param nThreads 线程数量
     * @param task 需要执行的任务
     * @return 任务完成时间
     * @throws InterruptedException 线程中断异常交由客户端处理
     */
    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException{

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {

                    try {
                        // 1. 每条线程出来都开始等待；for循环共创建nThread条线程，所有
                        startGate.await();
                        try{
                            task.run();
                        }finally {
                            // 2. 每条线程完成run任务之后，先等待着，直到nThreads线程都完成；
                            endGate.countDown();
                        }
                    }catch (Exception ignored){

                    }
                }
            });
            t.start();
        }

        long start = System.nanoTime();

        // 3.所有线程准备好之后，统一放行
        startGate.countDown();
        // 4.nThreads条线程都完成任务之后，endGate统计最后时间！
        endGate.await();
        long end = System.nanoTime();

        return end - start;
    }

    /**
     * nThreads条线程同时开始执行任务task
     * @param nThreads 线程数量
     * @param task 需要执行的任务
     * @return
     * @throws InterruptedException 线程中断异常交由客户端处理
     */
    public static void concurrencyTasks(int nThreads, final Runnable task) throws InterruptedException{

        final CountDownLatch startGate = new CountDownLatch(1);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        // 1. 每条线程出来都开始等待；for循环共创建nThread条线程，线程就绪之后等待信号
                        startGate.await();
                        task.run();
                    }catch (InterruptedException ignored){

                    }
                }
            });
            t.start();
        }

        // 2.startGate - 1 = 0；在1中等待的线程开始执行。即已经准备好的nThreads条线程同时开始执行
        startGate.countDown();
    }

    public static void main(String[] args) throws Exception{
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println(new HashMap().size());
            }
        };

        System.out.println(timeTasks(50,runnable));
    }
}
