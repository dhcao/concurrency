package org.dhcao.relax.executor.threadCancel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 线程安全
 * 这是个素数生成器；
 * 连续的枚举素数直到调用取消方法
 * @Author: dhcao
 * @Version: 1.0
 */
public class PrimeGenerator implements Runnable {

    // 存放素数
    private final List<BigInteger> primes = new ArrayList<BigInteger>();
    // 用volatile作为标志位，这也是volatile类型的常规用法
    private volatile boolean cancelled;

    /**
     * 连续不断的生成素数,直到cancelled标志为取消
     */
    @Override
    public void run() {
        // 第一个素数是1
        BigInteger p = BigInteger.ONE;

        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }
    }

    /**
     * 取消运行，通常在Runnable或者Task等任务类中有这个方法；
     */
    public void cancel(){
        cancelled = true;
    }

    /**
     * 返回一个素数集合的副本，由于拷贝副本有可能引起线程竞争，所以用syn同步
     * @return
     */
    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }

}
