package org.dhcao.relax.nonblocking;


import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用Treiber算法构造的非阻塞栈
 * Treiber算法（1986）：该算法的基本原理是：只有当您知道要添加的项目是自开始操作以来唯一添加的项目时，才会添加新的项目。
 * @Author: dhcao
 * @Version: 1.0
 */
public class ConcurrentStack<E> {

    AtomicReference<Node> top = new AtomicReference<Node>();

    /**
     * 压入栈
     * @param item
     */
    public void push(E item){
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    /**
     * 出栈
     * @return
     */
    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }


    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item){
            this.item = item;
        }
    }
}
