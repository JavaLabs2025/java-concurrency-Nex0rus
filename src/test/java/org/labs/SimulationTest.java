package org.labs;

import org.junit.jupiter.api.Test;
import org.labs.configuration.ConfigLoader;
import org.labs.configuration.SimulationConfig;
import org.labs.model.Plate;
import org.labs.simulation.Simulation;
import org.labs.statistics.SimulationStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.labs.simulation.SimulationUtils.createSimulation;
import static org.labs.simulation.SimulationUtils.runSimulation;

class SimulationTest {

    @Test
    void testTotalPortionsEaten() throws Exception {
        SimulationConfig config = ConfigLoader.loadConfig("test-config.json");

        Simulation simulation = createSimulation(config);

        SimulationStatistics statistics = runSimulation(simulation, config);

        assertEquals(config.totalFoodPortions(), statistics.getTotalRefills(),
                "Total refills should equal total food portions");

        for (Plate plate : simulation.plates()) {
            assertTrue(plate.getNumOfRefills() > 0,
                    "Each programmer should have eaten at least one portion");
        }


        assertTrue(
                statistics.getFairnessRation() >= 0.95d,
                String.format(
                        "Fairness check failed: fairness ratio should be >= 0.95, but was {%s}",
                        statistics.getFairnessRation()
                )
        );
    }
}

