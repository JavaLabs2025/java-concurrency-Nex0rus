package org.labs;

import org.labs.configuration.ConfigLoader;
import org.labs.configuration.SimulationConfig;
import org.labs.simulation.Simulation;
import org.labs.statistics.SimulationStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.labs.simulation.SimulationUtils.createSimulation;
import static org.labs.simulation.SimulationUtils.runSimulation;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting Dining Programmers Problem Simulation");

        try {
            SimulationConfig config = ConfigLoader.loadDefaultConfig();
            logger.info("Loaded configuration: {}", config);

            Simulation simulation = createSimulation(config);

            SimulationStatistics statistics = runSimulation(simulation, config);
            statistics.printFinalStatistics(simulation.plates());
        } catch (Exception e) {
            logger.error("Simulation failed", e);
            System.exit(1);
        }
    }
}