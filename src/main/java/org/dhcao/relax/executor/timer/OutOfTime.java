package org.dhcao.relax.executor.timer;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @Author: dhcao
 * @Version: 1.0
 */
public class OutOfTime {

    /**
     * Timer的缺点：
     * 1. timer是单线程的，如果你设置每5秒执行一次，而任务执行时间需要10秒，那么将破坏定时器，毕竟但线程要等待任务结束之后才能执行下一个。
     * 2. timer无法处理异常，异常会中断整个Timer，已经被调度但尚未执行但TimerTask将不会被执行
     * 所以，在java5以后不会使用Timer
     *
     * ScheduledThreadPoolExecutor：将能解决上述问题
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(),1);
        SECONDS.sleep(1);

        timer.schedule(new ThrowTask(),1);
        SECONDS.sleep(5);
    }

    public static class ThrowTask extends TimerTask{

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}


