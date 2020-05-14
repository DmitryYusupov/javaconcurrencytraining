package ru.yusdm.concurrency.syncronizers.semaphore;

import ru.yusdm.concurrency.common.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public class SemaphoreMain {

    private static final int MAX_SIZE_OF_PARKING = 4;

    public static void main(String args[]) {
        Semaphore semaphore = new Semaphore(MAX_SIZE_OF_PARKING, true);
        List<Car> parking = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 5 * MAX_SIZE_OF_PARKING; i++) {
            new Thread(new Car(i + "", parking, semaphore)).start();
        }
    }

    private static class Car implements Runnable {

        private String carName;
        private List<Car> parking;
        private Semaphore semaphore;

        Car(String carName, List<Car> parking, Semaphore semaphore) {
            this.carName = carName;
            this.parking = parking;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                System.err.println("Car_" + carName + " try to enter parking. " + getParkingState());
                semaphore.acquire();
                parking.add(this);
                System.err.println("Car_" + carName + " comes to parking." + getParkingState());
                ConcurrencyUtils.sleepInSecs(2);
                parking.remove(this);
                semaphore.release();
                System.err.println("Car_" + carName + " has left parking. " + getParkingState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private String getParkingState(){
            return "Busy/all " + "'" + parking.size() + "/" + MAX_SIZE_OF_PARKING + "'";
        }
    }

}
