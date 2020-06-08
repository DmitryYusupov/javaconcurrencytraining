package ru.yusdm.concurrency.asyncmethod.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThenCombineDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> increment = new CompletableFuture<>();
        CompletableFuture<Integer> square = new CompletableFuture<>();
        CompletableFuture<Integer> result = increment.thenCombine(square, (a, b) -> {
            return a + b;
        });

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(() -> increment.complete(increment(1)));
        executorService.submit(() -> square.complete(square(3)));
        System.out.println(result.get());
        executorService.shutdown();
    }

    public static int increment(int i) {
        System.out.println("increment");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ++i;
    }

    public static int square(int i) {
        System.out.println("square");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i * i;
    }

}
