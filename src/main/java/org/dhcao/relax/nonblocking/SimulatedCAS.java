package org.dhcao.relax.nonblocking;

/**
 * 自己实现一个CAS对象
 * @Author: dhcao
 * @Version: 1.0
 */
public class SimulatedCAS {
    private int value;


    public synchronized int get(){
        return value;
    }

    /**
     * CAS操作：取出value，如果value与我预期值expectedValue相等，则将新值newValue设置到value
     * @param expectedValue 预期值
     * @param newValue 新值
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }

        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {

        return expectedValue == compareAndSwap(expectedValue,newValue);
    }
}
