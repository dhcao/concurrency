package org.dhcao.relax.executor.newtaskfor;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public interface CancellableTask<T> extends Callable<T> {

    void cancel();
    RunnableFuture<T> newTask();
}
