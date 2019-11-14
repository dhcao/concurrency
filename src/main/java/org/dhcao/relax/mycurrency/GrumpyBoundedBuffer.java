package org.dhcao.relax.mycurrency;

/**
 * 扩展一下{@link BaseBoundedBuffer},将前提条件
 * 的失败传递给调用者。
 * 在有界缓存的前提条件：队列空时不可take，队列满时不可put
 *
 * 但这不是一种好的方法，毕竟队列满了，不是一种异常，队列空了也不是异常，就像红灯不是交通灯坏了，只是到了这个状态了。
 * 这种时候需要调用者自己处理异常{@link GrumpyBoundedBufferTest}
 * @Author: dhcao
 * @Version: 1.0
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    /**
     * it will throw exception when put v into the full queue
     * @throws Exception
     */
    public synchronized void put(V v) throws RuntimeException{
        if (isFull()) {
            throw new RuntimeException();
        }

        doPut(v);
    }

    /**
     * it will throw exception when take v from the empty queue
     * @return v
     * @throws Exception
     */
    public synchronized V take() throws RuntimeException{
        if (isEmpty()) {
            throw new RuntimeException();
        }

        return doTake();
    }
}
