package org.dhcao.relax.closeInstance;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class PersonSet {

    private final Set<String> mySet = new HashSet<String>();

    public synchronized void add(String str){
        mySet.add(str);
    }

    public synchronized boolean contains(String str){
        return mySet.contains(str);
    }
}
