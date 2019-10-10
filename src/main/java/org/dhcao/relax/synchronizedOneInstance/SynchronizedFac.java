package org.dhcao.relax.synchronizedOneInstance;

/**
 * 我们测试，如果统一个对象，有2个方法都使用synchronized。
 * 验证：每个对象有唯一的对象锁；（称为：内置锁或监视器锁）
 * 那么当线程一在访问方法一时，已经持有方法该对象锁。
 * 其他线程若想执行方法二，必须等待线程一释放该对象锁。
 * @Author: dhcao
 * @Version: 1.0
 */
public class SynchronizedFac {

    public void methodOne() throws Exception {
        synchronized (this.getClass()){
            String threadName = Thread.currentThread().getName();
            Thread.sleep(3000);
            System.out.println(threadName + "  执行方法1");
        }

    }

    /**
     * 第二次测试，方法二加锁，我们预期：
     * 当线程一执行方法1时，其他线程是不可以访问方法二的；
     * 必须等待线程一释放锁
     */
    public void methodTwo() {
        synchronized (this.getClass()){
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "  执行方法2");
        }

    }


    public static void main(String[] args) throws Exception{

//        final SynchronizedFac fac = new SynchronizedFac();

        for (int i = 0; i < 5; i++) {
            // 我们让线程一来访问方法一
            if (i == 0) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            new SynchronizedFac().methodOne();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, "thread -- " + i).start();
            }else{
                // 其他线程则访问方法二
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            new SynchronizedFac().methodTwo();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, "thread -- " + i).start();
            }

        }
    }
}
