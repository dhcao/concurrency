package org.dhcao.relax.useVolatile;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class useVolatile {

    /**
     * flag作为标志位，如果发生改变，即要通知所有线程
     */
    volatile boolean flag = false;

    public void stop(){


        while(!flag){
            System.out.println("还不到改变时候...");
        }
    }
}
