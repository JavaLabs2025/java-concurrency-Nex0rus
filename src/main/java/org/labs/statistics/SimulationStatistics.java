package org.labs.statistics;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.labs.model.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulationStatistics {
    private static final Logger logger = LoggerFactory.getLogger(SimulationStatistics.class);

    private final Map<Integer, Integer> programmerPortions = new HashMap<>();
    private final long startTime;

    public SimulationStatistics() {
        this.startTime = System.currentTimeMillis();
    }

    public void printFinalStatistics(List<Plate> plates) {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        logger.info("=== SIMULATION STATISTICS ===");
        logger.info("Total simulation time: {} ms ({} seconds)", totalTime, totalTime / 1000.0);

        int totalRefills = plates.stream()
                .mapToInt(Plate::getNumOfRefills)
                .sum();
        logger.info("Total plate refills: {}", totalRefills);

        logger.info("=== PROGRAMMER STATISTICS ===");
        plates.stream()
                .sorted(Comparator.comparing(Plate::getProgrammerId))
                .forEach(plate -> {
                    int programmerId = plate.getProgrammerId();
                    int portionsEaten = plate.getNumOfRefills();
                    logger.info("Programmer {} ate {} portions", programmerId, portionsEaten);
                    programmerPortions.put(programmerId, portionsEaten);
                });

        int minPortions = programmerPortions.values().stream()
                .min(Integer::compare)
                .orElse(0);
        int maxPortions = programmerPortions.values().stream()
                .max(Integer::compare)
                .orElse(0);

        int difference = maxPortions - minPortions;

        logger.info("=== FAIRNESS ANALYSIS ===");
        logger.info("Min portions eaten: {}", minPortions);
        logger.info("Max portions eaten: {}", maxPortions);
        logger.info("Difference: {} portions", difference);
        logger.info(
                "Fairness ratio: {:.2f}%",
                minPortions > 0 ? (double) minPortions / maxPortions * 100 : 0
        );
    }
}
