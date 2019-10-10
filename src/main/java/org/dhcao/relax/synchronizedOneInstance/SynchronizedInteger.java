package org.dhcao.relax.synchronizedOneInstance;

/**
 * 这是一个线程安全类
 * @Author: dhcao
 * @Version: 1.0
 */
public class SynchronizedInteger {

    /** 定义属性 */
    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }
}
