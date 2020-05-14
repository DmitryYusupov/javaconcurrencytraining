package ru.yusdm.concurrency.syncronizers.exchanger;

import ru.yusdm.concurrency.common.ConcurrencyUtils;

import java.util.concurrent.Exchanger;

public class ExchangerMain {

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        Thread subSum1 = new Thread(new SumCounter(1, 1, exchanger, 1));
        Thread subSum2 = new Thread(new SumCounter(3, 3, exchanger, 5));
        Thread subSum3 = new Thread(new SumCounter(5, 5, exchanger, 5));

        subSum1.start();
        subSum2.start();

        //subSum3.start();
    }

    private static class SumCounter implements Runnable {

        private int a;
        private int b;
        private int sleepSec;

        private Exchanger<Integer> exchanger;

        public SumCounter(int a, int b, Exchanger<Integer> exchanger, int sleepSec) {
            this.a = a;
            this.b = b;
            this.sleepSec = sleepSec;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            int result = a + b;
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " Count [" + a + " + " + b + " = " + (a + b) + "]");
            ConcurrencyUtils.sleepInSecs(sleepSec);
            try {
                System.out.println(threadName + " I am blocking and waiting for other thread to exchange");
                int exchanged = exchanger.exchange(result);
                System.out.println(threadName + " " + exchanged);
                System.out.println(threadName + " TOTAL " + (exchanged + result));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
