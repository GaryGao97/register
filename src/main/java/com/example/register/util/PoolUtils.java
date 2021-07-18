package com.example.register.util;

import com.example.register.config.system.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工具
 *
 * @author: Gary Gao(修远)
 * @date: 2021/5/24
 */
@Component
public class PoolUtils {
    @Autowired
    private WebConfig webConfig;

    private ExecutorService defaultPool;

    @PostConstruct
    public void init() {
        defaultPool = new ThreadPoolExecutor(Math.max(webConfig.getDefaultPoolMaxSize() / 2, 1),
                webConfig.getDefaultPoolMaxSize(),
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ReceiveThreadFactory("task-thread"));
    }

    public ExecutorService getDefaultPool() {
        return defaultPool;
    }

    private static class ReceiveThreadFactory implements ThreadFactory {
        private final String threadName;
        private final AtomicInteger count = new AtomicInteger(0);

        ReceiveThreadFactory(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String newThreadName = this.threadName + count.addAndGet(1);
            t.setName(newThreadName);
            return t;
        }
    }
}


