package com.gitee.rabbitnoteeth.bedrock.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorUtils {

    private ExecutorUtils() {
    }

    private static <T> List<T> invoke(int threads, boolean stopWhenErrorOccurs, Collection<? extends Callable<T>> tasks) throws ExecutionException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(threads)) {
            // check arguments
            if (threads < 1) {
                throw new IllegalArgumentException("the value of threads can not less than 1");
            }
            if (tasks == null || tasks.isEmpty()) {
                throw new IllegalArgumentException("the value of tasks can not be null or empty");
            }
            List<T> res = new ArrayList<>();
            List<Future<T>> futures = executorService.invokeAll(tasks);
            for (Future<T> future : futures) {
                try {
                    T t = future.get();
                    res.add(t);
                } catch (ExecutionException e) {
                    if (stopWhenErrorOccurs) {
                        throw e;
                    } else {
                        res.add(null);
                    }
                }
            }
            return res;
        } catch (Throwable e) {
            throw new ExecutionException(e);
        }
    }

    public static <T> List<T> invokeIgnoreErrors(int threads, Collection<? extends Callable<T>> tasks) throws ExecutionException {
        return invoke(threads, false, tasks);
    }

    public static <T> List<T> invoke(int threads, Collection<? extends Callable<T>> tasks) throws ExecutionException {
        return invoke(threads, true, tasks);
    }

    public static ExecutorService createVirtualThreadExecutor(String threadNamePrefix) {
        return Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name(threadNamePrefix, 0).factory());
    }

}
