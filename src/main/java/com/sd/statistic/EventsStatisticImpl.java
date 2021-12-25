package com.sd.statistic;

import com.sd.bucket.EventsBucket;
import com.sd.bucket.EventsBucketImpl;
import com.sd.clock.EventsClock;
import com.sd.utils.InstantWrapper;
import com.sd.utils.Rpm;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class EventsStatisticImpl implements EventsStatistic {
    private final EventsClock clock;
    private final PrintWriter writer;
    private final Map<String, EventsBucket> eventsBucket = new HashMap();

    public EventsStatisticImpl(EventsClock clock, PrintWriter writer) {
        this.clock = clock;
        this.writer = writer;
    }

    @Override
    public void incEvent(String name) {
        InstantWrapper now = clock.now();
        if (!eventsBucket.containsKey(name)) {
            eventsBucket.put(name, new EventsBucketImpl());
        }
        eventsBucket.get(name).incEvent(now);
    }

    @Override
    public void printStatistic() {
        eventsBucket.forEach((key, value) -> writer.write(
                "Event " +
                        key +
                        " has happened " +
                        value.getAll().getIntValue() +
                        " times")
        );
    }

    @Override
    public Rpm getAllEventStatistic() {
        InstantWrapper now = clock.now();
        AtomicInteger answer = new AtomicInteger();
        eventsBucket.forEach((s, bucket) -> answer.addAndGet(
                bucket.getRpm(now).getIntValue()
        ));
        return new Rpm(answer.get());

    }

    @Override
    public Rpm getEventStatisticByName(String name) {
        InstantWrapper now = clock.now();
        AtomicInteger answer = new AtomicInteger();
        Optional.ofNullable(eventsBucket.get(name)).ifPresent(bucket -> answer.addAndGet(
                bucket.getRpm(now).getIntValue()
        ));
        return new Rpm(answer.get());
    }
}
