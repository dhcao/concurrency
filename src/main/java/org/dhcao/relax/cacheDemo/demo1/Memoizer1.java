package org.dhcao.relax.cacheDemo.demo1;

import java.util.HashMap;
import java.util.Map;
import org.dhcao.relax.cacheDemo.Computable;

/**
 * 第一个缓存类：缓存compute的值
 * 缺点有：
 * 1. 直接对compute方法加锁，效率太低；
 * @Author: dhcao
 * @Version: 1.0
 */
public class Memoizer1<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 直接在方法上加锁，但这样会使得程序更慢；
     * @param arg
     * @return
     * @throws InterruptedException
     */
    public synchronized V compute(A arg) throws Exception {

        V result = cache.get(arg);

        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
