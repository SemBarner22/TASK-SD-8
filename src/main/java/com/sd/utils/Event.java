package com.sd.utils;

public class Event {
    private final InstantWrapper time;

    public Event(InstantWrapper time) {
        this.time = time;
    }

    public InstantWrapper getTime() {
        return time;
    }
}
