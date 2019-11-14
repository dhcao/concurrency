package org.dhcao.relax.mycurrency;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class GrumpyBoundedBufferTest {

    static GrumpyBoundedBuffer buffer = new GrumpyBoundedBuffer(10);

    public static void main(String[] args) throws InterruptedException {
        while (true) {

            try {
                Object item = buffer.take();
            } catch (RuntimeException e){
                Thread.sleep(100);
            }
        }
    }
}
