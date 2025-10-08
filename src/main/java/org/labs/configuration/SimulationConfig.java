package org.labs.configuration;

public record SimulationConfig(
        int numberOfProgrammers,
        int numberOfWaiters,
        int totalFoodPortions,
        ProgrammerProperties programmerProperties,
        WaiterProperties waiterProperties
) {
}
