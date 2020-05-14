package ru.yusdm.concurrency.tasks.firstlinktask.completablefuture;

import ru.yusdm.concurrency.tasks.firstlinktask.common.FirstLinkTaskService;
import ru.yusdm.concurrency.tasks.firstlinktask.common.ImgResource;
import ru.yusdm.concurrency.tasks.firstlinktask.common.Storage;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.anyOf;

public class CompletableFutureFirstLinkTaskService implements FirstLinkTaskService {

    private boolean simpleHandleTask = false;

    public CompletableFutureFirstLinkTaskService(boolean simpleHandleTask) {
        this.simpleHandleTask = simpleHandleTask;
    }

    @Override
    public void handleTask() throws Exception {
        if (simpleHandleTask) {
            simpleHandleTask();
        } else {
            complexHandleTask();
        }
    }

    private void complexHandleTask() throws Exception {
        Storage storage = new Storage();
        storage.printStorage();

        String[] resources = new String[]{"A", "B", "C"};



                Stream.of(resources)
                        .map(resourceName -> {
                                    return CompletableFuture.supplyAsync(() -> getResource(storage, resourceName))
                                            .thenAccept(imgResource -> System.out.println("Img resource accepted " + imgResource.getName()));

                                }
                        ).collect(Collectors.toList());


        System.out.println("End 33");

    }

    private void simpleHandleTask() throws Exception {
        Storage storage = new Storage();
        storage.printStorage();

        String[] resources = new String[]{"A", "B", "C"};

        List<CompletableFuture<ImgResource>>
                futures =
                Stream.of(resources)
                        .map(resourceName ->
                                CompletableFuture.supplyAsync(() -> getResource(storage, resourceName))).collect(Collectors.toList());


        CompletableFuture result = anyOf(futures.toArray(new CompletableFuture[0]));
        System.out.println("Result is " + result.get());
        System.out.println("End");
    }

    private ImgResource getResource(Storage storage, String resourceName) {
        ImgResource imgResource = storage.getResourcesMap().get(resourceName);
        System.out.println(Thread.currentThread().getName() + " has started! Try to get resource '" + resourceName + "' with resp time " + imgResource.getLoadTime());
        try {
            Thread.sleep(imgResource.getLoadTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return imgResource;
    }

}
