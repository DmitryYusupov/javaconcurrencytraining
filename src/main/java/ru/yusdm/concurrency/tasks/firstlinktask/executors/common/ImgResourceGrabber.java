package ru.yusdm.concurrency.tasks.firstlinktask.executors.common;

import ru.yusdm.concurrency.tasks.firstlinktask.common.ImgResource;
import ru.yusdm.concurrency.tasks.firstlinktask.common.Storage;

import java.util.concurrent.Callable;

public class ImgResourceGrabber implements Callable<ImgResource> {

    private Storage storage;
    private String resource;

    public ImgResourceGrabber(Storage storage, String resource) {
        this.storage = storage;
        this.resource = resource;
    }

    @Override
    public ImgResource call() throws Exception {
        ImgResource result = storage.getResourcesMap().get(resource);
        System.out.println(Thread.currentThread().getName() + " has started! Try to get resource '" + resource + "' with resp time " + result.getLoadTime());
        Thread.sleep(result.getLoadTime());
        return result;
    }
}
