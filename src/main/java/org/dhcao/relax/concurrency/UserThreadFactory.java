package org.dhcao.relax.concurrency;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class UserThreadFactory implements ThreadFactory {

    private final String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger(1);

    public UserThreadFactory(String whatFeatureOfGroup) {
        this.namePrefix = "UserThreadFactory`s " + whatFeatureOfGroup + "-Worker-";
    }

    public Thread newThread(Runnable task) {

        String name = namePrefix + nextId.getAndIncrement();
        Thread thread = new Thread(task,name);
        System.out.println(thread.getName());
        return thread;
    }
}
