package com.sd.clock;

import com.sd.utils.InstantWrapper;

import java.time.Instant;


public class EventsClockImpl implements EventsClock {
    @Override
    public InstantWrapper now() {
        return new InstantWrapper(Instant.now());
    }
}
