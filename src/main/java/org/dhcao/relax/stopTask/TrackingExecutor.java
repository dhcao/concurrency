package org.dhcao.relax.stopTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 给出如何在关闭过程中判断正在执行的任务，通过封装ExecutorService并使得execute记录哪些
 * 任务是在关闭之后取消的
 * @Author: dhcao
 * @Version: 1.0
 */
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService exec;
    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    /**
     * 对外的接口，
     * @return
     */
    public List<Runnable> getCancelledTasks(){

        if (!exec.isTerminated()) {
            throw new IllegalStateException("任务尚未停止");
        }

        return new ArrayList<Runnable>(tasksCancelledAtShutdown);

    }


    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout,unit);
    }

    /**
     * 如果线程在正在进行时中断，就放大set中
     * @param command
     */
    @Override
    public void execute(Runnable command) {
        exec.execute(() -> {
            try{
                command.run();
            } finally {
                if (isShutdown() && Thread.currentThread().isInterrupted()) {
                    tasksCancelledAtShutdown.add(command);
                }
            }
        });
    }
}
