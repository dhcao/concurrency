package org.dhcao.relax.carTracking2;

import java.util.HashMap;

/**
 * 这是无意义的（是我的一些测试...但是调整参数的过程并没有展示出来）
 * @Author: dhcao
 * @Version: 1.0
 */
public class TestMain {
    public static void main(String[] args) {

        final HashMap<String, Point> map = new HashMap<String, Point>();
        map.put("1",new Point(1,1));

        final DelegatingVehicleTracker tracker =
                new DelegatingVehicleTracker(map);


        new Thread(new Runnable() {
            public void run() {
                final Point point = tracker.getLocations().get("1");
                for (int i = 0; i < 5; i++) {
                    System.out.println(point.toString());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"线程1").start();

        new Thread(new Runnable() {
            public void run() {
                tracker.setLocations("1",1,2);
            }
        },"线程2").start();
    }
}
