package ru.yusdm.concurrency.common;

import java.util.concurrent.CountDownLatch;

public class ConcurrencyUtils {
    private ConcurrencyUtils() {

    }

    public static void sleepInSecs(float secsToSleep) {
        try {
            Thread.sleep((long) (secsToSleep * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForCountDownLatch(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
