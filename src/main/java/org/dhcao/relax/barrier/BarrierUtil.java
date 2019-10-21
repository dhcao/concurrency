package org.dhcao.relax.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 5个同事去吃饭，约定在饭店门口见面
 * @Author: dhcao
 * @Version: 1.0
 */
public class BarrierUtil {

    /**
     * 定义同事类
     */
    private static class Colleague extends Thread{

        // 要约定栅栏；所有的同事实现类都要被此栅栏约束
        private CyclicBarrier barrier;

        Colleague(CyclicBarrier barrier, String name){
            super(name);
            this.barrier = barrier;
        }


        @Override
        public void run() {

            try {

                System.out.println("同事：" + getName() + " 已经到达约定地点,等待其他人");
                barrier.await();
                System.out.println("人齐了，去吃饭");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        int colleagueNum = 5;

        // 1. 定义一个栅栏
        CyclicBarrier barrier = new CyclicBarrier(colleagueNum, new Runnable() {
            @Override
            public void run() {
                System.out.println("执行此操作的线程：" + Thread.currentThread().getName() + "。所有人均已到达约定地点；出发....");
            }
        });

        // 2. 将此栅栏约束在线程上
        for (int i = 0; i < colleagueNum; i++) {
            new Colleague(barrier,"同事" + i).start();
        }
    }
}
