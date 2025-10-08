package org.labs.configuration;

import java.util.concurrent.ThreadLocalRandom;

public record ProgrammerProperties(
        int minEatingTime,
        int maxEatingTime,
        int minChitChatTime,
        int maxChitChatTime
) {
    public long getEatingTime() {
        return ThreadLocalRandom.current().nextLong(minEatingTime, maxEatingTime);
    }

    public long getChitChatTime() {
        return ThreadLocalRandom.current().nextLong(minChitChatTime, maxChitChatTime);
    }
}
