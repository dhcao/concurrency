package org.dhcao.relax.stopTask;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 将线程的管理工作交给ExecutorService而不是自己管理，在{@link LogService}上的一种改进
 * @Author: dhcao
 * @Version: 1.0
 */
public class LogServiceUseExecutor {

    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private final PrintWriter writer;

    public LogServiceUseExecutor(PrintWriter writer) {
        this.writer = writer;
    }


    /*
    省略些代码，可参照{@link LogService}
     */
    public void start(){

    }

    /**
     * 重点在这里，通过exec来关闭线程
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException{
        try{
            exec.shutdown();
            exec.awaitTermination(5, TimeUnit.SECONDS);
        } finally {
            writer.close();
        }
    }

    public void log (String msg) {
        try{
            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException e){
            /*
            异常策略
             */
        }
    }

    private class WriteTask implements Runnable{
        private final String msg;

        private WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.println(msg);
        }
    }
}
