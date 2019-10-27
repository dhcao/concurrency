package org.dhcao.relax.executor.threadCancel;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * 素数生成器，不同于{@link PrimeGenerator}使用cancelled标志来停止生成器，
 * 这里直接使用线程的中断状态来停止生成器
 * @Author: dhcao
 * @Version: 1.0
 */
public class PrimeProduct extends Thread{

    private final BlockingQueue<BigInteger> queue;

    public PrimeProduct(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run(){
        BigInteger p = BigInteger.ONE;

        try {
            // 对中断位进行判定，中断就退出循环
            while( !Thread.currentThread().isInterrupted()){
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            // 允许线程退出，将中断信息打印，并恢复中断；
            e.printStackTrace();
//            Thread.currentThread().interrupt();

        }

    }

    /**
     * 调用线程的中断方法来改变中断位
     */
    public void cancel(){
        interrupt();
    }
}
