package org.labs.model;

import org.labs.configuration.ProgrammerProperties;

public class Programmer implements Runnable {
    private final ProgrammerProperties properties;
    private final Restaurant restaurant;
    private final Plate plate;
    private final Spoon smallerSpoon;
    private final Spoon biggerSpoon;

    public Programmer(
            ProgrammerProperties properties,
            Restaurant restaurant,
            Plate plate,
            Spoon smallerSpoon,
            Spoon biggerSpoon
    ) {
        this.properties = properties;
        this.restaurant = restaurant;
        this.plate = plate;
        this.smallerSpoon = smallerSpoon;
        this.biggerSpoon = biggerSpoon;
    }

    @Override
    public void run() {
        try {
            haveDinner();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void haveDinner() throws InterruptedException {
        while (restaurant.isServing() || plate.getState() == PlateState.FULL) {
            restaurant.makeOrder(plate);

            if (plate.getState() != PlateState.FULL) {
                continue;
            }

            eat();
            chitChat();
        }
    }

    public void eat() throws InterruptedException {
        try {
            smallerSpoon.take();
            biggerSpoon.take();
            Thread.sleep(properties.getEatingTime());
            plate.finish();
        } finally {
            biggerSpoon.put();
            smallerSpoon.put();
        }
    }

    public void chitChat() throws InterruptedException {
        Thread.sleep(properties.getChitChatTime());
    }
}
