package org.dhcao.relax.mycurrency;

/**
 * 有界缓存的终极版,哪些有界缓存就这么做的，例如{@link java.util.concurrent.ArrayBlockingQueue#take()}
 * @Author: dhcao
 * @Version: 1.0
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer {
    protected BoundedBuffer(int capacity) {
        super(capacity);
    }

    /**
     * 满的时候等待，不满就插入；
     * @param v
     * @throws InterruptedException
     */
    public synchronized void put(V v) throws InterruptedException {
        while(isFull()){
            wait();
        }
        doPut(v);
        notifyAll();
    }

    /**
     * 空的时候等待，不空就获取。
     * @return
     * @throws InterruptedException
     */
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }

        V v =(V) doTake();
        notifyAll();
        return v;
    }
}
