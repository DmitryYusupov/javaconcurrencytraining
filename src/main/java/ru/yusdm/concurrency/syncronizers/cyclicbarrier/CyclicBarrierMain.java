package ru.yusdm.concurrency.syncronizers.cyclicbarrier;

import ru.yusdm.concurrency.common.ConcurrencyUtils;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierMain {


    /**
     * Task: Imagine we prepare some dinner: dinner exists of
     * fruits, meat and drinks. And if dinner is ready
     * we can say client that it is ready, and wash dishes after
     * food preparation
     */
    public static void main(String args[]) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new DinnerReadyNotifier());
        int numberOfClients = 3;

        for (int i = 0; i < numberOfClients; i++) {
            Thread fruitsThread = new Thread(new Food("Fruits_" + i, cyclicBarrier));
            Thread drinksThread = new Thread(new Food("Drinks_" + i, cyclicBarrier));
            Thread meatThread = new Thread(new Food("Meat_" + i, cyclicBarrier));

            fruitsThread.start();
            drinksThread.start();
            meatThread.start();
            ConcurrencyUtils.sleepInSecs(3);
            System.out.println();
        }
    }

    private static class Food implements Runnable {

        private String foodName;
        /**
         * The main difference between CountDownLatch is that CountDonwLatch can not be reusable,
         * if it's value is 0
         */
        private CyclicBarrier cyclicBarrier;

        Food(String foodName, CyclicBarrier cyclicBarrier) {
            this.foodName = foodName;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("Begin to prepare '" + foodName + "'");
            ConcurrencyUtils.sleepInSecs(2);
            System.out.println("Food '" + foodName + "' is ready");
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Begin to wash dishes for '" + foodName + "'");
        }
    }

    private static class DinnerReadyNotifier implements Runnable {

        @Override
        public void run() {
            System.out.println("Dinner is ready! We can pass it to client!\n\n");
        }
    }
}
