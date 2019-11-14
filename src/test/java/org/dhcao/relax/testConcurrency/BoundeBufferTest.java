package org.dhcao.relax.testConcurrency;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class BoundeBufferTest {

    BoundeBuffer buffer = new BoundeBuffer(10);


    @Test
    public void isEmpty() {
    }

    @Test
    public void isFull() {
    }

    @Test
    public void put() throws InterruptedException {
        buffer.put(4);

    }

    @Test
    public void take() {
    }

    /**
     * 测试阻塞:
     * 如果向一个空的队列中获取数据，take();那么队列该阻塞。
     */
    @Test
    public void testTakeBlocksWhenEmpty(){
        final BoundeBuffer bb = new BoundeBuffer(10);
        Thread taker = new Thread(){
          public void run(){
              try {
//                  bb.put(3);
                  Object unused = bb.take();
                  // 如果执行到这一步，说明没有阻塞，那么失败了
                  fail();
              } catch (InterruptedException success){
                  try {
                      throw success;
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              } finally{
                  System.out.println("++++++++");
              }
          }
        };

        try {
            taker.start();
            Thread.sleep(10000);
            taker.interrupt();
            taker.join(10000);
            assertFalse(taker.isAlive());
        } catch (InterruptedException e) {
            fail();
        }

    }

    private void fail(){
        System.out.println("=============");
    }
}