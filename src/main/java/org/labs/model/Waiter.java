package org.labs.model;

import org.labs.configuration.WaiterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Waiter implements Runnable {
    private final Logger log = LoggerFactory.getLogger(Waiter.class);
    private final WaiterProperties properties;
    private final Restaurant restaurant;
    private final int id;

    public Waiter(
            WaiterProperties properties,
            Restaurant restaurant,
            int id
    ) {
        this.properties = properties;
        this.restaurant = restaurant;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            serveProgrammers();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void serveProgrammers() throws InterruptedException {
        while (restaurant.isServing() || restaurant.hasPlates()) {
            Plate plate = restaurant.getPlate();
            if (plate == null) {
                continue;
            }

            Thread.sleep(properties.getServingTime());

            if (restaurant.takeSoup()) {
                plate.refill();
                log.info(
                        "Waiter with id [{}] refilled plate for programmer with id [{}]",
                        id,
                        plate.getProgrammerId()
                );
            }
        }
    }
}
