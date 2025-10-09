package org.labs.statistics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.labs.model.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulationStatistics {
    private static final Logger logger = LoggerFactory.getLogger(SimulationStatistics.class);

    private final Map<Integer, Integer> programmerPortions;
    private final long totalTime;

    private final int totalRefills;
    private final int minPortions;
    private final int maxPortions;
    private final double fairnessRatio;

    public SimulationStatistics(Long startTime, List<Plate> plates) {
        this.totalTime = System.currentTimeMillis() - startTime;
        this.totalRefills = plates.stream()
                .mapToInt(Plate::getNumOfRefills)
                .sum();

        this.programmerPortions = plates.stream()
                .collect(Collectors.toMap(
                        Plate::getProgrammerId,
                        Plate::getNumOfRefills
                ));
        this.minPortions = programmerPortions.values().stream()
                .min(Integer::compare)
                .orElse(0);
        this.maxPortions = programmerPortions.values().stream()
                .max(Integer::compare)
                .orElse(0);

        this.fairnessRatio = (double) minPortions / maxPortions * 100;
    }

    public void printFinalStatistics(List<Plate> plates) {
        logger.info("=== SIMULATION STATISTICS ===");
        logger.info("Total simulation time: {} ms ({} seconds)", totalTime, totalTime / 1000.0);
        logger.info("Total plate refills: {}", totalRefills);

        logger.info("=== PROGRAMMER STATISTICS ===");

        programmerPortions.forEach((key, value) -> logger.info("Programmer {} ate {} portions", key, value));

        logger.info("=== FAIRNESS ANALYSIS ===");
        logger.info("Total refills from plates: {}", totalRefills);
        logger.info("Min portions eaten: {}", minPortions);
        logger.info("Max portions eaten: {}", maxPortions);
        logger.info("Difference: {} portions", maxPortions - minPortions);
        logger.info(
                "Fairness ratio: {}%",
                minPortions > 0 ? fairnessRatio : 0
        );
    }

    public int getTotalRefills() {
        return totalRefills;
    }

    public double getFairnessRation() {
        return fairnessRatio;
    }
}
