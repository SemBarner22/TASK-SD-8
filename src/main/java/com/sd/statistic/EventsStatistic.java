package com.sd.statistic;

import com.sd.utils.Rpm;

public interface EventsStatistic {
    void incEvent(String name);
    void printStatistic();
    Rpm getAllEventStatistic();
    Rpm getEventStatisticByName(String name);
}
