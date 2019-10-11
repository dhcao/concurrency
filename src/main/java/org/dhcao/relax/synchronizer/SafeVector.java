package org.dhcao.relax.synchronizer;

import java.util.Vector;

/**
 * 同步类容器Vector，Hashtable等，通过自身的锁来保护它的每个方法；
 * 所以在客户端加锁时，要加它们自身的锁！
 * @Author: dhcao
 * @Version: 1.0
 */
public class SafeVector {

    public static Object getLast(Vector list){
        /*
        同步类容器在方法上加锁，其锁是它的内置锁，在客户端加锁时，为来线程安全，
        也要使用容器的内置锁！
         */
        synchronized (list){
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(Vector list){
        synchronized (list){
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
        }
    }
}
