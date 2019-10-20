package org.dhcao.relax.cacheDemo;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public interface Computable<A, V> {

    V compute(A arg) throws InterruptedException, Exception;
}
