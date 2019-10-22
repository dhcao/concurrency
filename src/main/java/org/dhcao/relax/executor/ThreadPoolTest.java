package org.dhcao.relax.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class ThreadPoolTest {



    public static void main(String[] args) {

        Executor exe = Executors.newFixedThreadPool(3);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };

        for (int i = 0; i < 14; i++) {
            exe.execute(run);
        }
    }
}
