package com.sd.utils;

import java.time.Instant;

// It's better to wrap such values if we need any more methods
public class InstantWrapper {
    private final Instant instant;

    public InstantWrapper(Instant instant) {
        this.instant = instant;
    }

    public Instant getInstant() {
        return instant;
    }
}
