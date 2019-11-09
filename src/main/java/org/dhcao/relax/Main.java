package org.dhcao.relax;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class Main {

    public volatile static AtomicLong a = new AtomicLong(0);

    public static void main(String[] args) throws Exception {


//         Runtime.getRuntime().addShutdownHook(new Thread(){
//             @Override
//             public void run() {
//                 System.out.println("主程序关闭了，来触发关闭钩子，钩子线程：" + Thread.currentThread().getName());
//             }
//         });
//
//        System.out.println(Thread.currentThread().getName() + " 运行完毕");

        Map map  = new HashMap<>();
        map.put("3",3);

        ExecutorService exe1 = Executors.newFixedThreadPool(50);
        ExecutorService exe2 = Executors.newFixedThreadPool(50);


        for (int i = 0; i < 30000; i++) {
            exe1.execute(() -> {
                System.out.println(map.get("3") + " - " + a.addAndGet(1));
            });
        }

        for (int i = 0; i < 30000; i++) {
            exe2.execute(() -> map.put(Math.random(),Math.random()));
        }


    }
}
