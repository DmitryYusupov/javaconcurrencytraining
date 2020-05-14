package ru.yusdm.concurrency.tasks.firstlinktask;


import ru.yusdm.concurrency.tasks.firstlinktask.completablefuture.CompletableFutureFirstLinkTaskService;
import ru.yusdm.concurrency.tasks.firstlinktask.executors.completionservice.CompletionFirstLinkTaskService;
import ru.yusdm.concurrency.tasks.firstlinktask.simplethread.SimpleThreadTaskAtomicSyncHandler;
import ru.yusdm.concurrency.tasks.firstlinktask.simplethread.SimpleThreadTaskNoSyncHandler;
import ru.yusdm.concurrency.tasks.firstlinktask.simplethread.SimpleThreadTaskSyncHandler;

public class FirstLinkTaskApp {

    public static void main(String args[]) throws Exception {
        runWithCompletableFutureComplexMode();
    }

    private static void runWithCompletableFutureComplexMode() throws Exception {
        new CompletableFutureFirstLinkTaskService(false).handleTask();
    }

    private static void runWithCompletableFutureSimpleMode() throws Exception {
        new CompletableFutureFirstLinkTaskService(true).handleTask();
    }

    private static void runWithCompletionService() throws Exception {
        new CompletionFirstLinkTaskService().handleTask();
    }

    private static void runWithSimpleThreadTaskAtomicSyncHandler() throws InterruptedException {
        new SimpleThreadTaskAtomicSyncHandler().handleTask();
    }

    private static void runWithSimpleThreadTaskNoSyncHandler() throws InterruptedException {
        new SimpleThreadTaskNoSyncHandler().handleTask();
    }

    private static void runWithSimpleThreadTaskSyncHandler() throws InterruptedException {
        new SimpleThreadTaskSyncHandler().handleTask();
    }


}
