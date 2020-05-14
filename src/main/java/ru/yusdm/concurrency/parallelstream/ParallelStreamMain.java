package ru.yusdm.concurrency.parallelstream;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static ru.yusdm.concurrency.common.ConcurrencyUtils.*;

public class ParallelStreamMain {

    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    // https://habr.com/company/otus/blog/338770/

    public static void main(String args[]) {
        //runParallelWithDefaultPoolSize();
        //runParallelWithDefinedPoolSize();
        //  runWithoutParallelism();
        runOnCustomPool();
    }

    private static void runOnCustomPool() {
        countDownLatch = new CountDownLatch(1);
        long currentTime = System.currentTimeMillis();
        ForkJoinPool customPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        customPool.submit(() -> {
            runStreamBodyParallel();
        });
        waitForCountDownLatch(countDownLatch);
        System.out.println("Time = " + (System.currentTimeMillis() - currentTime));
    }

    private static void runWithoutParallelism() {
        long currentTime = System.currentTimeMillis();

        Set<String> threads = IntStream.range(1, 12)
                .mapToObj(ParallelStreamMain::longOperation)
                .collect(toSet());
        System.out.println("Threads size is: '" + threads.size() + "'; Max availableProcessors = " + Runtime.getRuntime().availableProcessors());

        System.out.println("Time = " + (System.currentTimeMillis() - currentTime));
    }

    private static void runParallelWithDefinedPoolSize() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
        runParallelWithDefaultPoolSize();
    }

    private static void runParallelWithDefaultPoolSize() {
        long currentTime = System.currentTimeMillis();

        Thread th = new Thread(ParallelStreamMain::runStreamBodyParallel);
        th.start();

        th = new Thread(ParallelStreamMain::runStreamBodyParallel);
        th.start();

        waitForCountDownLatch(countDownLatch);
        System.out.println("Time = " + (System.currentTimeMillis() - currentTime));
    }

    private static void runStreamBodyParallel() {
        Set<String> threads = IntStream.range(1, 12)
                .parallel()
                .mapToObj(ParallelStreamMain::longOperation)
                .collect(toSet());
        System.out.println("Threads size is: '" + threads.size() + "'; Max availableProcessors = " + Runtime.getRuntime().availableProcessors());
        countDownLatch.countDown();
    }

    private static String longOperation(int i) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + " " + i);
        sleepInSecs(1.5f);
        return currentThreadName;
    }
}
