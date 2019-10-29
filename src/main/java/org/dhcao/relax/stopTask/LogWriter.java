package org.dhcao.relax.stopTask;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消费者日志服务；生产-消费者模型
 * 缺陷：没有关闭线程功能
 * @Author: dhcao
 * @Version: 1.0
 */
public class LogWriter {

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;

    public LogWriter(LoggerThread logger) {
        this.queue = new LinkedBlockingQueue<>(10);
        this.logger = logger;
    }

    public void start(){
        logger.start();
    }

    /**
     * 生产者放进来日志
     * @param msg
     * @throws InterruptedException
     */
    public void log(String msg) throws InterruptedException{
        queue.put(msg);
    }


    private class LoggerThread extends Thread{
        private final PrintWriter writer;

        private LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true){
                    // 不断的输出日志就行了
                    writer.println(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }
}
