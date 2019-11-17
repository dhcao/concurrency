package org.dhcao.relax.CAS;

import org.dhcao.relax.nonblocking.SimulatedCAS;

/**
 * 基于CAS实现到非阻塞计数器
 * @Author: dhcao
 * @Version: 1.0
 */
public class CasCounter {

    private SimulatedCAS value;

    public int getValue(){
        return value.get();
    }

    /**
     * 如果拿不到正确到值，就一直循环，不会阻塞；
     * @return 正确value.get() + 1的值。
     */
    public int increment(){
        int v;
        do{
            v = value.get();
        }
        while (v != value.compareAndSwap(v, v+1));

        return v + 1;

    }
}
