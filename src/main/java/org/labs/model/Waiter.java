package org.labs.model;

import org.labs.configuration.WaiterProperties;

public class Waiter implements Runnable {
    private final WaiterProperties properties;
    private final Restaurant restaurant;

    public Waiter(
            WaiterProperties properties,
            Restaurant restaurant
    ) {
        this.properties = properties;
        this.restaurant = restaurant;
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
            }
        }
    }
}
