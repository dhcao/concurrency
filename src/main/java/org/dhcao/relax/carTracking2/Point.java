package org.dhcao.relax.carTracking2;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class Point {

    public final int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x: " + x +",  y: " + y;
    }
}
