package org.labs.configuration;

import java.util.concurrent.ThreadLocalRandom;

public class ProgrammerProperties {
    private final int minEatingTime;
    private final int maxEatingTime;
    private final int thinkingTime;
    private final int minChitChatTime;
    private final int maxChitChatTime;

    public ProgrammerProperties(
            int minEatingTime,
            int maxEatingTime,
            int thinkingTime,
            int minChitChatTime,
            int maxChitChatTime
    ) {
        this.minEatingTime = minEatingTime;
        this.maxEatingTime = maxEatingTime;
        this.thinkingTime = thinkingTime;
        this.minChitChatTime = minChitChatTime;
        this.maxChitChatTime = maxChitChatTime;
    }

    public long getEatingTime() {
        return ThreadLocalRandom.current().nextLong(minEatingTime, maxEatingTime);
    }

    public long getThinkingTime() {
        return thinkingTime;
    }

    public long getChitChatTime() {
        return ThreadLocalRandom.current().nextLong(minChitChatTime, maxChitChatTime);
    }
}
