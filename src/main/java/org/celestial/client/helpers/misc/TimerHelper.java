package org.celestial.client.helpers.misc;

import org.celestial.client.helpers.Helper;

public class TimerHelper implements Helper {

    private long ms = getCurrentMS();

    private long getCurrentMS() {
        return System.currentTimeMillis();
    }

    public boolean hasReached(double milliseconds) {
        return (double)(this.getCurrentMS() - this.ms) > milliseconds;
    }

    public void reset() {
        ms = getCurrentMS();
    }

    public long getTime() {
        return getCurrentMS() - ms;
    }
}
