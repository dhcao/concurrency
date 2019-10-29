package org.dhcao.relax.uncaught;

/**
 * 线程捕获异常处理器，如果要为线程池中的所有线程设置一个UncaughtExceptionHandler，
 * 那么需要ThreadPoolExecutor的构造函数提供一个ThreadFactory。具体请见ThreadPoolExecutor的用法
 * 用法请运行{@link UEHLoggerTest#main(String[])}
 * @Author: dhcao
 * @Version: 1.0
 */
public class UEHLogger implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(222222);
    }
}
