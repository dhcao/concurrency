package org.dhcao.relax.putIfAbsent;

import java.util.List;

/**
 * 利用组合来维护list的安全性；
 * ImprovedList保护来list的安全，当在构造函数把list传进来之后，list就不再是可以被访问到的了。
 * @Author: dhcao
 * @Version: 1.0
 */
public class ImprovedList<T>{

    private final List<T> list;

    public ImprovedList(List<T> list) {
        this.list = list;
    }

    /**
     * 若没有则添加；
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent(T x){

        boolean contains = !list.contains(x);
        if (contains) {
            list.add(x);
        }

        return !contains;
    }
}
