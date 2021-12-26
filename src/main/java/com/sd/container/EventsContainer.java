package com.sd.container;

import com.sd.utils.InstantWrapper;
import com.sd.utils.Rpm;

public interface EventsContainer {
    void addOrIncreaseEvent(InstantWrapper now);
    Rpm getRpm(InstantWrapper now);
    Rpm getAmountOfEventsAll();
}
