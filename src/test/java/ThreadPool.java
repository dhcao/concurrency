import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class ThreadPool {

    @Test
    public void test1(){
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,10,30,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),Executors.privilegedThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
