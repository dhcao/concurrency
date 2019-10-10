package org.dhcao.relax.unsafeDemo;

/**
 * 一个线程不安全的demo
 * @Author: dhcao
 * @Version: 1.0
 */
public class UnsafeSequence {

    private int value;

    /**
     * 竞态条件：getNext是否会返回正确的值，取决于运行时对线程中操作的交替执行方式
     * 这不是我们想要的
     * @return
     */
    public int getNext(){
        return value++;
    }


    public static void main(String[] args) {
        System.out.println();
    }
}
