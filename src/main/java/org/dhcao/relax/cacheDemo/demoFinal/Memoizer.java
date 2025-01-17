package org.dhcao.relax.cacheDemo.demoFinal;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.dhcao.relax.cacheDemo.Computable;

/**
 * 基于FutureTask的缓存类
 * FutureTask的特性：如果某个操作已经开始，则等待其结果返回，线程阻塞；
 * @Author: dhcao
 * @Version: 1.0
 */
public class Memoizer<A, V> implements Computable<A, V> {

    // 使用同步map
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * Memoizer3的终结版本；cache.putIfAbsent()原子操作，避免了3中微弱的get和put的竞态条件出现；
     * 这也是个Callable和Future接口的好例子；
     * @param arg
     * @return
     * @throws Exception
     */
    public V compute(final A arg) throws Exception {

        // 循环获取，是为了防止，在执行callback中获取值失败；
        while (true){
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> var = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<>(var);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }

            try{
                return f.get();
            } catch (CancellationException e){
                cache.remove(arg, f);
            } catch (ExecutionException ex){
                throw ex;
            }

        }

    }
}
