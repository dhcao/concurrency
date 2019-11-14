package org.dhcao.relax.mycurrency;

/**
 * 自定义有界缓存
 * @Author: dhcao
 * @Version: 1.0
 */
public class BaseBoundedBuffer<V> {

    /** 缓存队列 */
    private final V[] buf;
    /** 当前准备好存放数据的指针位置 */
    private int tail;
    private int head;
    private int count;

    protected BaseBoundedBuffer(int capacity){
        this.buf = (V[]) new Object[capacity];
    }

    /**
     * put V into the buffer and the pointer point the next position before the buffer full,
     * otherwise the pointer will cycle to the first position;
     * @param v
     */
    protected synchronized final void doPut(V v){
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }

        ++count;
    }

    /**
     * take from the buffer and move the pointer
     * @return
     */
    protected synchronized final V doTake(){
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }

        -- count;
        return v;
    }

    public synchronized final boolean isFull(){
        return count == buf.length;
    }

    public synchronized final boolean isEmpty(){
        return count == 0;
    }
}
