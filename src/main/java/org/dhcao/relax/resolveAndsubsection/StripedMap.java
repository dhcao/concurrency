package org.dhcao.relax.resolveAndsubsection;

/**
 * 基于散列都Map中使用锁分段技术
 * 它拥有N_LOCKS个锁，并且每个锁保护散列桶的一个子集。
 *
 * 同步策略：buckets[n] 由 locks[n%N_LOCKS]来保护
 * @Author: dhcao
 * @Version: 1.0
 */
public class StripedMap {

    private static final int N_LOCKS = 16; // 默认分为16段
    private final Node[] buckets; // 节点数量
    private final Object[] locks; // 锁

    public static class Node<K,V> {

        public V value;
        public K key;
        public Node<K,V> prev;
        public Node<K,V> next;

        public Node(K k, V d, Node<K,V> p, Node<K,V> n) {
            key = k;
            value = d;
            prev = p;
            next = n;
        }

        public Node<K,V> next(){
            return next;
        }

    }

    public StripedMap(int numBuckets) {
        this.buckets = new Node[numBuckets];
        this.locks = new Object[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new Object();
        }
    }

    private final int hash(Object key){
        return Math.abs(key.hashCode() % buckets.length);
    }

    /**
     * 利用锁分段,只需要找到对应的锁，锁定对应的分段就行
     * @param key
     * @return
     */
    public Object get(Object key){
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]){
            for(Node m = buckets[hash]; m != null; m = m.next()){
                if (m.key.equals(key)) {
                    return m.value;
                }
            }
        }
        return null;
    }

    /**
     * 特殊操作需要拿到所有的锁
     */
    public void clear(){
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]){
                buckets[i] = null;
            }
        }
    }
}
