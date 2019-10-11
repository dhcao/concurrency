package org.dhcao.relax.carTracking2;

/**
 *
 * 线程安全的Point，保证x，y同时更新，成对修改或获取。
 * @Author: dhcao
 * @Version: 1.0
 */
public class SafePoint {

    private int x,y;

    private SafePoint(int[] a){
        this(a[0],a[1]);
    }

    private SafePoint(SafePoint p){
        this(p.get());
    }

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get(){
        return new int[]{x,y};
    }

    public synchronized void set(int x, int y){
        this.x = x;
        this.y = y;
    }
}
