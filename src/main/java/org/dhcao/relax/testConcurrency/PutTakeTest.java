package org.dhcao.relax.testConcurrency;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试程序：
 * BoundedBuffer的生产者-消费者程序
 *
 * 测试目的：多线程时，放入的值和取出的值是否相同，并以此判断是否有丢失。
 * 测试要点：注意用来计数的值最好不要有规律，以免编译器提前优化，才有随机数最好。
 *
 * @Author: dhcao
 * @Version: 1.0
 */
public class PutTakeTest {

    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BoundeBuffer<Integer> bb;
    private final int nTrials, nPairs;

    PutTakeTest(int capacity, int npairs, int ntrials){
        this.bb = new BoundeBuffer<>(capacity);
        this.nPairs = npairs;
        this.nTrials = ntrials;
        this.barrier = new CyclicBarrier(2 * npairs + 1);
    }

    public static void main(String[] args) {
        new PutTakeTest(10, 10, 100000).test();
        pool.shutdown();
    }

    void test(){
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());

            }
            barrier.await();
            barrier.await();
            if (putSum.get() == takeSum.get()) {
                System.out.println("=========");
            }else{
                System.out.println("putSum : " + putSum.get());
                System.out.println("takeSum : " + takeSum.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生产者
     */
    class Producer implements Runnable{

        @Override
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0;

                barrier.await();

                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }

                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消费者
     */
    class Consumer implements Runnable{

        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }

                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private int xorShift(int y) {

        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
