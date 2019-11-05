package org.dhcao.relax.threadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 自定义线程工厂
 * 每当线程池需要创建一个线程时，都是通过线程工厂方法来完成的。
 * @Author: dhcao
 * @Version: 1.0
 */
public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {

        return new MyAppThread(r,poolName);
    }

    /**
     * 测试
     * 发现：在设置核心线程只有2时，如果不限定 队列有界（就是使用无界队列时）。只会有核心线程2条来执行任务，其他任务无限
     * 堆在队列中。如果设置有界队列，只有在队列满来，才会创建新线程，直到达到最大线程！
     * @param args
     */
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2,10,30,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(17), new MyThreadFactory("这是我的  "),
                new ThreadPoolExecutor.CallerRunsPolicy());

        MyAppThread.setDebug(true);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                pool.execute(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                });

            }).start();
            System.out.println("存活： " + MyAppThread.getThreadsAlive());
            System.out.println("创建： " + MyAppThread.getThreadsCreated());
        }

    }
}

class MyAppThread extends Thread{

    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();

    public MyAppThread(Runnable target) {
        super(target,DEFAULT_NAME);
    }

    public MyAppThread(Runnable runnable, String name){

        super(runnable, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler((Thread t, Throwable throwable) -> {
                 log.log(Level.SEVERE,"UNCAUGHT in thread " + t.getName(), throwable);
                }
        );
    }

    public void run(){

        boolean debug = debugLifecycle;
        if (debug) {
//            log.log(Level.SEVERE.FINE, "Created " + getName());
            System.out.println("Created " + getName());
        }

        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) {
//                log.log(Level.FINE, "Exiting " + getName());
                System.out.println("Exiting " + getName());
            }
        }
    }

    public static int getThreadsCreated(){
        return created.get();
    }

    public static int getThreadsAlive(){
        return alive.get();
    }

    public static boolean getDebug(){
        return debugLifecycle;
    }

    public static void setDebug(boolean b){
        debugLifecycle = b;
    }
}