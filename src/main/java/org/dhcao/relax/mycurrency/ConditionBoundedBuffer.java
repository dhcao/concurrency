package org.dhcao.relax.mycurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显式的Condition来作为条件队列
 * @Author: dhcao
 * @Version: 1.0
 */
public class ConditionBoundedBuffer<T> {

    private final static int BUFFER_SIZE = 10;
    private int tail;
    private int head;
    private int count;

    protected final Lock lock = new ReentrantLock();

    /** 条件谓词：not full */
    private final Condition notFull = lock.newCondition();

    /** 条件谓词：not Empty */
    private final Condition notEmpty = lock.newCondition();

    private final T[] items = (T[]) new Object[BUFFER_SIZE];

    /**
     * 在notFull条件队列中来保存等待的线程
     * signal(nofify)唤醒当前队列中的线程，则可以不实用notifyAll，因为条件谓词确定队列
     * @param x
     * @throws InterruptedException
     */
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }

            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }

            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }

            T x = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }

            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
