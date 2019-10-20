package org.dhcao.relax.cacheDemo.demo2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dhcao.relax.cacheDemo.Computable;

/**
 * 第二个缓存类：基于ConcurrentHashMap，来简化synchronized
 * @Author: dhcao
 * @Version: 1.0
 */
public class Memoizer2<A, V> implements Computable<A, V> {

    // 使用同步map
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * 使用同步Map来提升性能，但是同步只在get和put时有效，组合时会破坏同步；
     * 如果同时运行f(2),可能compute方法会重复执行（在第一此调用还未放入缓存时第二次又来）
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
