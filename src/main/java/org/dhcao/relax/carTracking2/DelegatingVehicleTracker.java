package org.dhcao.relax.carTracking2;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用线程安全的工具来保证locations是线程安全的
 * Collections.unmodifiableMap：保证当locations中的值被修改时，通过getLocations能同步获取到修改后的值。
 * @Author: dhcao
 * @Version: 1.0
 */
public class DelegatingVehicleTracker {

    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> locations) {
        this.locations = new ConcurrentHashMap<String, Point>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations(){
        return unmodifiableMap;
    }

    public Point getLocations(String id){
        return locations.get(id);
    }

    public void setLocations(String id, int x, int y){
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }
    }


}
