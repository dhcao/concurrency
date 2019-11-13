package org.dhcao.relax.testConcurrency;

import java.util.concurrent.Semaphore;

/**
 * 这是一个基于信号量的有界缓存
 * 相应的单元测试请看：test目录对应的单元测试
 * @Author: dhcao
 * @Version: 1.0
 */
public class BoundeBuffer<E> {

    /** 信号量：队列已占用 */
    private final Semaphore availableItems;
    /** 信号量：队列空闲 */
    private final Semaphore availableSpaces;
    /** 队列数组 */
    private final E[] items;
    private int putPosition = 0, takePosition = 0;

    public BoundeBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    /**
     * 队列是否为空
     * @return 占用计数信号量是否为0
     */
    public boolean isEmpty(){
        return availableItems.availablePermits() == 0;
    }

    /**
     * 队列是否已满
     * @return 空闲计数信号量是否为0
     */
    public boolean isFull(){
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException{
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException{
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private synchronized E doExtract(){
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length)? 0 : i;
        return x;
    }

    /**
     * 在队列中放入时统计putPosition，如果到最后一个，从0开始计
     * @param x
     */
    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length)? 0 : i;
    }

}
