import com.sd.clock.EventsClock;
import com.sd.utils.InstantWrapper;

public class TestClock implements EventsClock {
        private InstantWrapper now;

        public TestClock(InstantWrapper now) {
            this.now = now;
        }

        public void setNow(InstantWrapper now) {
            this.now = now;
        }

        @Override
        public InstantWrapper now() {
            return now;
        }
};
