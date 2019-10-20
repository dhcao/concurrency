package org.dhcao.relax.semaphore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 使用信号量（semaphore）构建一个有界容器。信号量的值为容器的大小
 * @Author: dhcao
 * @Version: 1.0
 */
public class BoundedHashSet<T> {

    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        // 初始化信号量的值，作为set容器的边界
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.sem = new Semaphore(bound);
    }

    /**
     * 如果添加成功，则信号量的可用值减少一个；否则，被acquire占用的信号量要释放。
     * @param o
     * @return
     * @throws InterruptedException
     */
    public boolean add(T o) throws InterruptedException{

        // 1. 尝试取获取一个信号量，如果获取不到，此方法阻塞，直到能够获取到信号量
        sem.acquire();
        boolean wasAdded = false;

        try{
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            // 2. finaly会在return之前执行，但是不会改变return的值。
            if (!wasAdded) {
                // 3. 如果添加失败，则set没有增加数据，则信号量不需要计数，所以释放它。
                sem.release();
            }
        }
    }

    /**
     * 容器删除一个数据是，要释放对应的信号量
     * @param o
     * @return
     */
    public boolean remove(Object o){
        final boolean remove = set.remove(o);

        if (remove) {
            sem.release();
        }

        return remove;
    }

}
