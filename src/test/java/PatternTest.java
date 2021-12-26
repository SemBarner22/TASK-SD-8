import com.sd.statistic.EventsStatistic;
import com.sd.statistic.EventsStatisticImpl;
import com.sd.utils.InstantWrapper;
import com.sd.utils.Rpm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternTest {
    private TestClock clock;
    private InstantWrapper now;
    private EventsStatistic eventsStatistic;
    private static final Rpm rpmOne = new Rpm(1);
    private static final Rpm rpmZero = new Rpm(0);
    private static final Rpm rpmHour = new Rpm(3600);

    private void addSeconds(int sec) {
        now = new InstantWrapper(now.getInstant().plusSeconds(sec));
        clock.setNow(now);
    }

    private void subtractSeconds(int sec) {
        now = new InstantWrapper(now.getInstant().minusSeconds(sec));
        clock.setNow(now);
    }

    @BeforeEach
    public void setUp() {
        now = new InstantWrapper(Instant.EPOCH);
        clock = new TestClock(now);
        eventsStatistic = new EventsStatisticImpl(clock, null);
    }

    @Test
    public void oneEvent() {
        eventsStatistic.incEvent("event0");
        assertEquals(1, eventsStatistic.getEventStatisticByName("event0").getIntValue());
    }

    @Test
    public void manyEventsInHour() {
        for (int i = 0; i < 3600; i++) {
            eventsStatistic.incEvent("event");
            addSeconds(1);
        }
        assertEquals(rpmHour, eventsStatistic.getEventStatisticByName("event"));
        assertEquals(rpmZero, eventsStatistic.getEventStatisticByName("event1"));
        subtractSeconds(3600);
        assertEquals(rpmHour, eventsStatistic.getEventStatisticByName("event"));
    }

    @Test
    public void anotherManyEventsInHour() {
        for (int i = 0; i < 3600; i++) {
            eventsStatistic.incEvent("event");
            addSeconds(1);
        }
        assertEquals(rpmHour, eventsStatistic.getEventStatisticByName("event"));
        addSeconds(3600);
        assertEquals(rpmZero, eventsStatistic.getEventStatisticByName("event"));
    }

    @Test
    public void manyManyDifferentEventsInHour() {
        for (int i = 0; i < 3600; i++) {
            eventsStatistic.incEvent("event" + i);
            addSeconds(1);
        }
        assertEquals(rpmOne, eventsStatistic.getEventStatisticByName("event1"));
        assertEquals(rpmHour, eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void strangeTime() {
        for (int i = 0; i < 3; i++) {
            eventsStatistic.incEvent("event");
            addSeconds(60 * 40);
        }
        assertEquals(rpmOne, eventsStatistic.getEventStatisticByName("event"));
        subtractSeconds(60 * 40);
        assertEquals(rpmOne, eventsStatistic.getEventStatisticByName("event"));
    }


}
