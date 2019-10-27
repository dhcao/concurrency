package org.dhcao.relax.executor.threadCancel;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class CreatePrime {

    public static void main(String[] args) throws InterruptedException {

        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();

        // 执行1秒，然后取消
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }

        System.out.println(generator.get());
    }
}
