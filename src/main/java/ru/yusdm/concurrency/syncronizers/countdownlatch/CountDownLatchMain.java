package ru.yusdm.concurrency.syncronizers.countdownlatch;

import ru.yusdm.concurrency.common.ConcurrencyUtils;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchMain {

    /**
     * Task: Imagine we prepare some dinner: dinner exists of
     * fruits, meat and drinks. And if dinner is ready
     * we can say client that it is ready, and wash dishes after
     * food preparation
     */
    public static void main(String args[]) {
        prepareDinnerForGroupOfPeople(2);

        /**
         * But if you call method like this:
         * prepareDinnerForGroupOfPeople(2 or any number > 1);
         *
         * Y'll see incorrect behaviour. Because count down latch already has value = 0.
         *
         * See example with CyclicBarrier, barrier can be reused even in case if we have passed it
         */
    }

    private static void prepareDinnerForGroupOfPeople(int numberOfClients) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        for (int i = 0; i < numberOfClients; i++) {
            Thread fruitsThread = new Thread(new Food("Fruits_" + i, countDownLatch));
            Thread drinksThread = new Thread(new Food("Drinks_" + i, countDownLatch));
            Thread meatThread = new Thread(new Food("Meat_" + i, countDownLatch));

            fruitsThread.start();
            drinksThread.start();
            meatThread.start();
            System.out.println();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Dinner is ready, take it please!");
        }

    }

    private static class Food implements Runnable {

        private String foodName;

        private CountDownLatch countDownLatch;

        Food(String foodName, CountDownLatch countDownLatch) {
            this.foodName = foodName;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("Begin to prepare '" + foodName + "'");
            ConcurrencyUtils.sleepInSecs(2);
            System.out.println("Food '" + foodName + "' is ready");
            countDownLatch.countDown();
            /*try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
