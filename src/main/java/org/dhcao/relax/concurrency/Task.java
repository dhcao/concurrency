package org.dhcao.relax.concurrency;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class Task implements Runnable {


    private final AtomicLong count = new AtomicLong(0L);

    public void run() {
        System.out.println("running_" + count.getAndIncrement());
    }
}
