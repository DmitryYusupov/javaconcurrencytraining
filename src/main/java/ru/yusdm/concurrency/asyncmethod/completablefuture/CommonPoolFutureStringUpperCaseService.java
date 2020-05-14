package ru.yusdm.concurrency.asyncmethod.completablefuture;

import ru.yusdm.concurrency.asyncmethod.common.StringUpperCaseService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CommonPoolFutureStringUpperCaseService implements StringUpperCaseService {

    @Override
    public void upperCaseAndPrintService(String str) {
        //is executed on ForkJoinPool.commonPool() - by default
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return str.toUpperCase();
        });
        future.thenAccept(result -> System.out.println(result));

        System.out.println("Other actions!");
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
