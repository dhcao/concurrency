package org.dhcao.relax.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于锁实现的信号量
 * @Author: dhcao
 * @Version: 1.0
 */
public class SemaphoreOnLock {

    private final Lock lock = new ReentrantLock();

    private final Condition permitsAvailable = lock.newCondition();

    private int permits;

    SemaphoreOnLock (int initialPermits) {
        lock.lock();
        try{
            permits = initialPermits;
        } finally {
            lock.unlock();
        }
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0) {
                permitsAvailable.await();
            }

            -- permits;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 直接增加许可后通知到条件队列
     */
    public void release(){
        lock.lock();
        try {
            ++ permits;
            permitsAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
