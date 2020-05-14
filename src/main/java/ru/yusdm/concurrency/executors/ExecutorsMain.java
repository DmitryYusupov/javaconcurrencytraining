package ru.yusdm.concurrency.executors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static ru.yusdm.concurrency.common.ConcurrencyUtils.sleepInSecs;

public class ExecutorsMain {


    private static Map<String, Integer> countChars(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyMap();
        } else {
            Map<String, Integer> result = new HashMap<>();
            for (String occur: s.split("")){
                result.put(occur, Optional.ofNullable(result.get(occur)).map(i-> ++i).orElse(1));
            }
            return result;
        }
    }

    public static void main(String args[]) throws Exception {
        /*Stream.of(1, 2, 3, 4, 5).map(i -> {
            System.out.println("map i " + i);
            return i + 1;
        }).filter(i -> {
            System.out.println("fileter " + i);
            return i > 3;
        }).collect(Collectors.toList());
*/
        //testSingleThreadExecutor();
        //testNThreadExecutor(3);
        String str = "dimadasha";
        Map<String, Integer> result = countChars(str);
        System.out.println();

        /**
         * We see if task in separate thread was finished, we have working thread in pool.
         * As doc says it will be in working state for 60 sec, so it takes new task
         *
         *
         * pool-1-thread-1: index = 0
         * pool-1-thread-2: index = 1
         * pool-1-thread-3: index = 2
         * pool-1-thread-4: index = 3
         *
         * pool-1-thread-1: index = 4
         * pool-1-thread-2: index = 5
         * pool-1-thread-3: index = 6
         * pool-1-thread-4: index = 7
         *
         * pool-1-thread-5: index = 8
         * pool-1-thread-2: index = 9
         */
        // testCachedThreadExecutor(2, 0.5f);

        /**
         * pool-1-thread-6: index = 5
         * pool-1-thread-4: index = 3
         * pool-1-thread-1: index = 0
         * pool-1-thread-3: index = 2
         * pool-1-thread-8: index = 7
         * pool-1-thread-7: index = 6
         * pool-1-thread-5: index = 4
         * pool-1-thread-2: index = 1
         * pool-1-thread-9: index = 8
         * pool-1-thread-10: index = 9
         */
        //  testCachedThreadExecutor(2, 0);
    }

    /**
     * pool-1-thread-1: index = 0
     * pool-1-thread-1: index = 1
     * ..........................
     * pool-1-thread-1: index = 9
     */
    private static void testSingleThreadExecutor() {
        /**
         * new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
         */
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10).forEach(i -> {
            executorService.submit(new MyRunnable(i));
        });
        executorService.shutdown();
    }

    /**
     * pool-1-thread-3: index = 2
     * pool-1-thread-1: index = 0
     * pool-1-thread-2: index = 1
     * <p>
     * pool-1-thread-1: index = 3
     * pool-1-thread-2: index = 4
     * pool-1-thread-3: index = 5
     * <p>
     * pool-1-thread-1: index = 6
     * pool-1-thread-3: index = 8
     * pool-1-thread-2: index = 7
     * <p>
     * pool-1-thread-1: index = 9
     */
    private static void testNThreadExecutor(int threadNumber) {
        /**
         * ThreadPoolExecutor(nThreads, nThreads,
         *                                       0L, TimeUnit.MILLISECONDS,
         *                                       new LinkedBlockingQueue<Runnable>());
         */
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        IntStream.range(0, 10).forEach(i -> {
            executorService.submit(new MyRunnable(i));
        });
        executorService.shutdown();
    }

    private static void testCachedThreadExecutor(float sleepTime, float sleepTimeAfterSubmit) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        IntStream.range(0, 10).forEach(i -> {
            System.out.println(i);
            executorService.submit(new MyRunnable(i, sleepTime));
            if (Float.compare(0, sleepTimeAfterSubmit) != 0) {
                sleepInSecs(sleepTimeAfterSubmit);
            }
        });
        executorService.shutdown();
    }

    public static class MyRunnable implements Runnable {

        private int index;
        private float sleep = 1.5f;

        MyRunnable(int index) {
            this.index = index;
        }

        public MyRunnable(int index, float sleep) {
            this.index = index;
            this.sleep = sleep;
        }

        @Override
        public void run() {
            sleepInSecs(sleep);
            System.out.println(Thread.currentThread().getName() + ": index = " + index);
        }
    }

}