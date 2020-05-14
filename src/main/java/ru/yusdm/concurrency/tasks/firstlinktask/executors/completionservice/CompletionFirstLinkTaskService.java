package ru.yusdm.concurrency.tasks.firstlinktask.executors.completionservice;

import ru.yusdm.concurrency.tasks.firstlinktask.common.FirstLinkTaskService;
import ru.yusdm.concurrency.tasks.firstlinktask.common.ImgResource;
import ru.yusdm.concurrency.tasks.firstlinktask.common.Storage;
import ru.yusdm.concurrency.tasks.firstlinktask.executors.common.ImgResourceGrabber;

import java.util.concurrent.*;

public class CompletionFirstLinkTaskService implements FirstLinkTaskService {

    @Override
    public void handleTask() throws Exception {
        Storage storage = new Storage();
        storage.printStorage();

        String[] resources = new String[]{"A", "B", "C"};

        ExecutorService executorService = Executors.newFixedThreadPool(resources.length);
        CompletionService<ImgResource> completionService = new ExecutorCompletionService<>(executorService);

        for (String resource : resources) {
            completionService.submit(new ImgResourceGrabber(storage, resource));
        }
        executorService.shutdown();

        for (int i = 0; i < resources.length; i++) {
            Future<ImgResource> f = completionService.take();
            ImgResource result = f.get();
            System.out.println(result);
            break;
        }

    }

}
