package ru.yusdm.concurrency.tasks.firstlinktask.simplethread;

import ru.yusdm.concurrency.tasks.firstlinktask.common.FirstLinkTaskService;
import ru.yusdm.concurrency.tasks.firstlinktask.common.ImgResource;
import ru.yusdm.concurrency.tasks.firstlinktask.common.Storage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleThreadTaskNoSyncHandler implements FirstLinkTaskService {

    private ImgResource result = null;

    @Override
    public void handleTask() throws InterruptedException {
        Storage storage = new Storage();
        storage.printStorage();

        String[] resources = new String[]{"A", "B", "C"};
        CountDownLatch countDownLatch = new CountDownLatch(resources.length);

        for (String r : resources) {
            ImgResource resource = storage.getResourcesMap().get(r);
            new Thread(new SimpleThreadRunnable(resource, countDownLatch)).start();
        }

        countDownLatch.await(4, TimeUnit.SECONDS);
    }

    public class SimpleThreadRunnable implements Runnable {
        private ImgResource resourceToGet;
        private CountDownLatch countDownLatch;

        public SimpleThreadRunnable(ImgResource resourceToGet, CountDownLatch countDownLatch) {
            this.resourceToGet = resourceToGet;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(resourceToGet.getLoadTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (result == null) {
                StringBuilder stringBuilder = new StringBuilder(Thread.currentThread().getName() + ": Prepare response  " + resourceToGet.getName() + " ");
                result = resourceToGet;
                stringBuilder.append("Returned resource is ").append(result.getName());
                System.out.println(stringBuilder);
            }

            countDownLatch.countDown();
        }
    }

}
