package org.dhcao.relax.executor.threadCancel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class TTT {

    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(3);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit){
        final Thread taskThread = Thread.currentThread();

        cancelExec.schedule(() -> {
                taskThread.interrupt();
        },timeout, unit);
        r.run();
    }


    public static void main(String[] args) {
        timedRun(() -> {
            while (true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }

        },3, TimeUnit.SECONDS);
    }

}
