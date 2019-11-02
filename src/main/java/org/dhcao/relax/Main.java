package org.dhcao.relax;


/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class Main {

    public static void main(String[] args) {


         Runtime.getRuntime().addShutdownHook(new Thread(){
             @Override
             public void run() {
                 System.out.println("主程序关闭了，来触发关闭钩子，钩子线程：" + Thread.currentThread().getName());
             }
         });

        System.out.println(Thread.currentThread().getName() + " 运行完毕");


    }
}
