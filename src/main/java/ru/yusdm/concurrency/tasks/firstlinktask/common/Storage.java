package ru.yusdm.concurrency.tasks.firstlinktask.common;

import java.util.HashMap;
import java.util.Map;

import static ru.yusdm.concurrency.tasks.firstlinktask.common.ResponseTimegenerator.getRandomResponseTime;

public class Storage {
    private Map<String, ImgResource> resourcesMap;

    public Storage() {
        initResourceResponseTimeMap();
    }

    private void initResourceResponseTimeMap() {
        resourcesMap = new HashMap<>();
        String resourceName = "A";
        resourcesMap.put(resourceName, new ImgResource(resourceName, getRandomResponseTime()));
        resourceName = "B";
        resourcesMap.put(resourceName, new ImgResource(resourceName, getRandomResponseTime()));
        resourceName = "C";
        resourcesMap.put(resourceName, new ImgResource(resourceName, getRandomResponseTime()));
    }

    public void printStorage() {
        if (resourcesMap != null) {
            resourcesMap.forEach((key, value) -> System.out.println("'" + key + "' responseTime " + value.getLoadTime()));
        }
    }

    public Map<String, ImgResource> getResourcesMap() {
        return resourcesMap;
    }
}
