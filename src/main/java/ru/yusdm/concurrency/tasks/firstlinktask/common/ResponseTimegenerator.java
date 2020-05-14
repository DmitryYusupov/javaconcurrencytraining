package ru.yusdm.concurrency.tasks.firstlinktask.common;

import java.util.Random;

public class ResponseTimegenerator {

    private static final int MAX_RESPONSE_TIME = 5;


    private ResponseTimegenerator() {

    }

    private static int getRandomInDiapason(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static int getRandomResponseTime() {
        return getRandomInDiapason(1, MAX_RESPONSE_TIME) * 1000;
       // return 3;
    }

}
