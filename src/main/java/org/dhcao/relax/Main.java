package org.dhcao.relax;


/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class Main {

    public static void main(String[] args) {
        int count = Integer.SIZE - 3;
        System.out.println(count);
        System.out.println((1 << count) - 1);
        System.out.println(-1 << count);
        System.out.println(0 << count);
    }
}
