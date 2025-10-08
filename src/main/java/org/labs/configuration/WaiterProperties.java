package org.labs.configuration;

import java.util.concurrent.ThreadLocalRandom;

public class WaiterProperties {
    private final int minServingTime;
    private final int maxServingTime;

    public WaiterProperties(
            int minServingTime,
            int maxServingTime
    ) {
        this.minServingTime = minServingTime;
        this.maxServingTime = maxServingTime;
    }

    public long getServingTime() {
        return ThreadLocalRandom.current().nextLong(minServingTime, maxServingTime);
    }
}
