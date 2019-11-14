package org.dhcao.relax.mycurrency;

/**
 * 实现一种简单的轮询与休眠机制来处理阻塞
 * @Author: dhcao
 * @Version: 1.0
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer {
    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    /**
     * 自旋，直到能插入...休眠时间决定来cpu有多忙；
     * @throws InterruptedException
     */
    public void put(V v) throws InterruptedException{
        while(true){
            synchronized (this){
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(100);
        }
    }

    /**
     * 自旋，直到能take...休眠时间决定来cpu有多忙；
     * @return
     * @throws InterruptedException
     */
    public V take()throws InterruptedException{
        while(true){
            synchronized (this){
                if (!isEmpty()) {
                    return (V) doTake();
                }
            }
            Thread.sleep(100);
        }
    }

}
