package com.sd.bucket;

import com.sd.utils.Event;
import com.sd.utils.InstantWrapper;
import com.sd.utils.Rpm;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventsBucketImpl implements EventsBucket {
    private final Queue<Event> bucket = new ArrayDeque<>();
    private int removedOld = 0;
    private static final int hourSeconds = 3600;

    private boolean olderThanHour(InstantWrapper saved, InstantWrapper now) {
        return saved.getInstant().isBefore(now.getInstant().minusSeconds(hourSeconds));
    }
    private void cleanNotNeeded(InstantWrapper now) {
        while (!bucket.isEmpty() && olderThanHour(bucket.peek().getTime(), now)) {
            bucket.poll();
            removedOld++;
        }
    }

    @Override
    public void incEvent(InstantWrapper now) {
        cleanNotNeeded(now);
        bucket.add(new Event(now));
    }

    @Override
    public Rpm getRpm(InstantWrapper now) {
        cleanNotNeeded(now);
        return new Rpm(bucket.size());
    }

    @Override
    public Rpm getAll() {
        return new Rpm(bucket.size() + removedOld);
    }
}
