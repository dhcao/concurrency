package org.dhcao.relax.mycurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.dhcao.relax.threadPool.MyThreadFactory;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class BoundedBufferTest {

    public static final BoundedBuffer buff1 = new BoundedBuffer(3);
    public static final BoundedBuffer buff2 = new BoundedBuffer(3);

    static ThreadPoolExecutor exec1 = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
    static ThreadPoolExecutor exec2 = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {
        final MyThreadFactory aa = new MyThreadFactory("线程11111");
        final MyThreadFactory bb = new MyThreadFactory("线程======");
        exec1.setThreadFactory(aa);
        exec2.setThreadFactory(bb);

        // exec1在buff1中阻塞，会进入条件队列1
        for (int i = 0; i < 5; i++) {
            exec1.execute(()-> {
                try {
                    buff1.put("2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(1000);
        }

        for (int i = 0; i < 5; i++) {
            exec2.execute(()-> {
                try {
                    buff2.put("999");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(1000);
        }


        for (int i = 0; i < 2; i++) {
            exec2.execute(()-> {
                try {
                    System.out.println(buff2.take());
//                    Thread.currentThread().interrupt();
                } catch (InterruptedException e) {
//                    Thread.currentThread().stop();
                }
            });
            exec1.shutdown();
            Thread.sleep(2222);
            exec2.shutdown();

        }

    }
}
