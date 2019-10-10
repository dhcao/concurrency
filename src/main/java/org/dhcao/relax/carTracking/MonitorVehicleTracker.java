package org.dhcao.relax.carTracking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于监视器模式的车辆追踪
 * 以返回副本的形式来保证locations是线程安全的
 * @Author: dhcao
 * @Version: 1.0
 */
public class MonitorVehicleTracker {

    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = locations;
    }

    /**
     * 获取所有的车辆位置：得到的是当前位置集合的副本
     * @return
     */
    public synchronized Map<String, MutablePoint> getLocations(){
        return deepCopy(locations);
    }

    /**
     * 获取车辆的位置，依然得到的是一个副本；
     * @param id
     * @return
     */
    public synchronized  MutablePoint getLocation(String id){
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    /**
     * 设置车辆的位置
     * @param id
     * @param x
     * @param y
     */
    public synchronized void setLocations(String id, int x, int y){
        MutablePoint loc = locations.get(id);
        if (loc == null) {
            throw new IllegalArgumentException("no such ID: " + id);
        }
        loc.x = x;
        loc.y = y;
    }

    /**
     * 返回当前位置集合的一个副本；
     * @param m
     * @return
     */
    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m){

        Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();
        for (String id : m.keySet()) {
            result.put(id, new MutablePoint(m.get(id)));
        }

        return Collections.unmodifiableMap(result);

    }
}
