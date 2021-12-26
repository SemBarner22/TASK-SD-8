package com.sd.statistic;

import com.sd.container.EventsContainer;
import com.sd.container.EventsContainerImpl;
import com.sd.clock.EventsClock;
import com.sd.utils.InstantWrapper;
import com.sd.utils.Rpm;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EventsStatisticImpl implements EventsStatistic {
    private final EventsClock clock;
    private final PrintWriter writer;
    private final Map<String, EventsContainer> eventsContainerMap = new HashMap();

    public EventsStatisticImpl(EventsClock clock, PrintWriter writer) {
        this.clock = clock;
        this.writer = writer;
    }

    @Override
    public void incEvent(String name) {
        InstantWrapper now = clock.now();
        if (!eventsContainerMap.containsKey(name)) {
            eventsContainerMap.put(name, new EventsContainerImpl());
        }
        eventsContainerMap.get(name).addOrIncreaseEvent(now);
    }

    @Override
    public void printStatistic() {
        for (var entry : eventsContainerMap.entrySet()) {
            writer.write("Event with name " +
                    entry.getKey() +
                    " has happened " +
                    entry.getValue().getAmountOfEventsAll().getIntValue() +
                    " times");
        }
    }

    @Override
    public Rpm getAllEventStatistic() {
        InstantWrapper now = clock.now();
        AtomicInteger answer = new AtomicInteger();
        for (var entry : eventsContainerMap.entrySet()) {
            answer.addAndGet(entry.getValue().getRpm(now).getIntValue());
        }
        return new Rpm(answer.get());

    }

    @Override
    public Rpm getEventStatisticByName(String name) {
        InstantWrapper now = clock.now();
        AtomicInteger answer = new AtomicInteger();
        EventsContainer event = eventsContainerMap.get(name);
        if (event != null) {
            answer.addAndGet(event.getRpm(now).getIntValue());
        }
        return new Rpm(answer.get());
    }
}
