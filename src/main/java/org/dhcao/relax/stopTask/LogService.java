package org.dhcao.relax.stopTask;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消费者日志服务；生产-消费者模型
 * 在{@link LogWriter}的基础上优化：
 * 添加可靠的取消操作
 * @Author: dhcao
 * @Version: 1.0
 */
public class LogService {

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private final PrintWriter writer;

    private boolean isShutdown;
    private int reservations; // 队列中排队的消息

    public LogService(LoggerThread logger, PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>(10);
        this.logger = logger;
        this.writer = writer;
    }

    /**
     * 启动日志线程
     */
    public void start(){
        logger.start();
    }

    /**
     * 停止日志服务：将标志位标记为停止
     * 将日志线程标记为：中断
     */
    public void stop(){
        synchronized (this){
            isShutdown = true;
        }
        logger.interrupt();
    }

    /**
     * 生产者放进来日志：这时候要判断是否日志服务是否已经停止，如果停了，就不该再放进任务
     * @param msg
     * @throws InterruptedException
     */
    public void log(String msg) throws InterruptedException{

        synchronized (this){

            if (isShutdown) {
                throw new IllegalStateException("服务已停止");
            }

            ++ reservations;
        }
        queue.put(msg);
    }


    private class LoggerThread extends Thread{

        /**
         * 停止服务的条件：
         * 1. 服务已经通过isShutDown标记为停止
         * 2. 队列中已经没有排队的消息了
         */
        @Override
        public void run() {
            try {
                while (true){
                    try{
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String msg = queue.take();
                        synchronized (LogService.this){
                            -- reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e){
                        /*
                        retry
                         */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
