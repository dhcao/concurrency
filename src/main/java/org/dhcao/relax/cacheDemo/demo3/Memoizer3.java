package org.dhcao.relax.cacheDemo.demo3;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.dhcao.relax.cacheDemo.Computable;

/**
 * 基于FutureTask的缓存类
 * FutureTask的特性：如果某个操作已经开始，则等待其结果返回，线程阻塞；弥补类Memoizer2的缺陷
 * @Author: dhcao
 * @Version: 1.0
 */
public class Memoizer3<A, V> implements Computable<A, V> {

    // 使用同步map
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * FutureTask的特点：如果某个操作已经开始，则线程会等待结果；
     * @param arg
     * @return
     * @throws Exception
     */
    public V compute(final A arg) throws Exception {

        // 1. 获取到对应的Future
        Future<V> f = cache.get(arg);

        // 2. 如果我们获取到了一个空的Future，代表该key是新的，我们创建一个新的Future
        if (f == null) {

            // 2.1 创建一个新的Callable（Future依赖于此），它将计算compute(A)的值
            Callable<V> var = new Callable<V>() {
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };

            // 2.2 创建一个var回调回来的Future；
            FutureTask<V> ft = new FutureTask<V>(var);
            f = ft;
            cache.put(arg, f);

            // run方法将执行call方法。
            ft.run();
        }
        try{
            return f.get();
        } catch (ExecutionException e){
            throw e;
        }

    }
}
