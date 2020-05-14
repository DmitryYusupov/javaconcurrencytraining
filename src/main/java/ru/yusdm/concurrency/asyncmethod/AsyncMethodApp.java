package ru.yusdm.concurrency.asyncmethod;

import ru.yusdm.concurrency.asyncmethod.completablefuture.CommonPoolFutureStringUpperCaseService;

public class AsyncMethodApp {

    public static void main(String args[]) {
        execWithCommonPoolFutureService();
    }

    private static void execWithCommonPoolFutureService(){
        new CommonPoolFutureStringUpperCaseService().upperCaseAndPrintService("hello world");
    }
}
