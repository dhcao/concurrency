package org.dhcao.relax.uncaught;

/**
 * 测试UncaughtExceptionHandler
 * @Author: dhcao
 * @Version: 1.0
 */
public class UEHLoggerTest {
    public static void main(String[] args) {

        // 这个放在逻辑上方，那么本线程出错之后将交给UEHLogger处理
        Thread.currentThread().setUncaughtExceptionHandler(new UEHLogger());

        String str = null;
        str.toUpperCase();

    }
}
