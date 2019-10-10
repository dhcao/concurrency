package org.dhcao.relax.readThread;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{

        ReaderThread(String name){
            super(name);
        }

        @Override
        public void run() {
            while(!ready){
                Thread.yield();
                System.out.println(11);
            }
            System.out.println(Thread.currentThread().getName() + ": " + number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread("执行线程").start();
        number = 42;
        ready = true;
    }
}
