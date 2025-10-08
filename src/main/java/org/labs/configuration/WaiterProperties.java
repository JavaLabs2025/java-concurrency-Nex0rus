package org.labs.configuration;

import java.util.concurrent.ThreadLocalRandom;

public record WaiterProperties(
        int minServingTime,
        int maxServingTime
) {
    public long getServingTime() {
        return ThreadLocalRandom.current().nextLong(minServingTime, maxServingTime);
    }
}
