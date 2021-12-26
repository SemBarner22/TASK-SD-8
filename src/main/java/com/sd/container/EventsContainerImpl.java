package com.sd.container;

import com.sd.utils.Event;
import com.sd.utils.InstantWrapper;
import com.sd.utils.Rpm;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventsContainerImpl implements EventsContainer {
    private final Queue<Event> eventQueue = new ArrayDeque<>();
    private int removedOld = 0;
    private static final int hourSeconds = 3600;

    private boolean olderThanHour(InstantWrapper saved, InstantWrapper now) {
        return saved.getInstant().isBefore(now.getInstant().minusSeconds(hourSeconds));
    }

    private void cleanNotNeeded(InstantWrapper now) {
        while (!eventQueue.isEmpty() && olderThanHour(eventQueue.peek().getTime(), now)) {
            eventQueue.poll();
            removedOld++;
        }
    }

    @Override
    public void addOrIncreaseEvent(InstantWrapper now) {
        cleanNotNeeded(now);
        eventQueue.add(new Event(now));
    }

    @Override
    public Rpm getRpm(InstantWrapper now) {
        cleanNotNeeded(now);
        return new Rpm(eventQueue.size());
    }

    @Override
    public Rpm getAmountOfEventsAll() {
        return new Rpm(eventQueue.size() + removedOld);
    }
}
