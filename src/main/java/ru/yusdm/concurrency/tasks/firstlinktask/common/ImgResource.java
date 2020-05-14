package ru.yusdm.concurrency.tasks.firstlinktask.common;

public final class ImgResource {

    private String name;
    private int loadTime;

    public ImgResource(String name, int loadTime) {
        this.name = name;
        this.loadTime = loadTime;
    }

    public String getName() {
        return name;
    }

    public int getLoadTime() {
        return loadTime;
    }

    @Override
    public String toString() {
        return "Resource '" + name + "'";
    }


}
