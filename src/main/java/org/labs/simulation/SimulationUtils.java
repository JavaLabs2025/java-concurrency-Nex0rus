package org.labs.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.labs.configuration.SimulationConfig;
import org.labs.model.Plate;
import org.labs.model.Programmer;
import org.labs.model.Restaurant;
import org.labs.model.Spoon;
import org.labs.model.Waiter;
import org.labs.statistics.SimulationStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulationUtils {
    private final static Logger log = LoggerFactory.getLogger(SimulationUtils.class);

    public static Simulation createSimulation(SimulationConfig config) {
        PriorityBlockingQueue<Plate> platesToServe = new PriorityBlockingQueue<>();

        Restaurant restaurant = new Restaurant(platesToServe, config.totalFoodPortions());

        List<Spoon> spoons = new ArrayList<>();
        for (int i = 0; i < config.numberOfProgrammers(); i++) {
            spoons.add(new Spoon(i));
        }

        List<Plate> plates = new ArrayList<>();
        List<Programmer> programmers = new ArrayList<>();

        for (int i = 0; i < config.numberOfProgrammers(); i++) {
            Plate plate = new Plate(i);
            plates.add(plate);

            Spoon leftSpoon = spoons.get(i);
            Spoon rightSpoon = spoons.get((i + 1) % config.numberOfProgrammers());
            Spoon smallerSpoon = leftSpoon.getId() < rightSpoon.getId() ? leftSpoon : rightSpoon;
            Spoon biggerSpoon = leftSpoon.getId() > rightSpoon.getId() ? leftSpoon : rightSpoon;

            Programmer programmer = new Programmer(
                    config.programmerProperties(),
                    restaurant,
                    plate,
                    smallerSpoon,
                    biggerSpoon,
                    i
            );
            programmers.add(programmer);
        }

        List<Waiter> waiters = new ArrayList<>();
        for (int i = 0; i < config.numberOfWaiters(); i++) {
            Waiter waiter = new Waiter(config.waiterProperties(), restaurant, i);
            waiters.add(waiter);
        }

        return new Simulation(restaurant, programmers, waiters, plates);
    }

    public static SimulationStatistics runSimulation(
            Simulation simulation,
            SimulationConfig config
    ) throws InterruptedException {
        try (
                ExecutorService programmerPool = Executors.newVirtualThreadPerTaskExecutor();
                ExecutorService waiterPool = Executors.newVirtualThreadPerTaskExecutor();
//                ExecutorService programmerPool = Executors.newFixedThreadPool(config.numberOfProgrammers());
//                ExecutorService waiterPool = Executors.newFixedThreadPool(config.numberOfProgrammers())
        ) {
            long startTime = System.currentTimeMillis();

            log.info(
                    "Starting simulation with [{}] programmers and [{}] waiters",
                    config.numberOfProgrammers(),
                    config.numberOfWaiters()
            );

            try {
                for (Programmer programmer : simulation.programmers()) {
                    programmerPool.submit(programmer);
                }
                for (Waiter waiter : simulation.waiters()) {
                    waiterPool.submit(waiter);
                }
            } finally {
                programmerPool.shutdown();
                waiterPool.shutdown();

                while (!programmerPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.warn("Waiting for programmers pool to terminate");
                }

                while (!waiterPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.warn("Waiting for waiters pool to terminate");
                }
            }

            return new SimulationStatistics(startTime, simulation.plates());
        }
    }
}
