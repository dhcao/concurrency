package org.dhcao.relax.executor.newtaskfor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 重写newTaskFor方法
     * @param callable
     * @param <T>
     * @return
     */
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {

        if (callable instanceof CancellableTask) {
            return ((CancellableTask<T>) callable).newTask();
        }
        return super.newTaskFor(callable);
    }

    /**
     * 实现CancellabledTask
     */
    public abstract class SocketUsingTask<T> implements CancellableTask<T> {
        // todo
    }
}
