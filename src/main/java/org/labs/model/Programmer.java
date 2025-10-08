package org.labs.model;

import org.labs.configuration.ProgrammerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Programmer implements Runnable {
    private final Logger log = LoggerFactory.getLogger(Programmer.class);
    private final ProgrammerProperties properties;
    private final Restaurant restaurant;
    private final Plate plate;
    private final Spoon smallerSpoon;
    private final Spoon biggerSpoon;
    private final Integer id;

    public Programmer(
            ProgrammerProperties properties,
            Restaurant restaurant,
            Plate plate,
            Spoon smallerSpoon,
            Spoon biggerSpoon,
            Integer id
    ) {
        this.properties = properties;
        this.restaurant = restaurant;
        this.plate = plate;
        this.smallerSpoon = smallerSpoon;
        this.biggerSpoon = biggerSpoon;
        this.id = id;
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
            log.info("Programmer with id [{}] acquired small spoon with id [{}]", id, smallerSpoon.getId());

            biggerSpoon.take();
            log.info("Programmer with id [{}] acquired bigger spoon with id [{}]", id, biggerSpoon.getId());

            Thread.sleep(properties.getEatingTime());
            plate.finish();
            log.info("Programmer with id [{}] finished plate number [{}]", id, plate.getNumOfRefills());
        } finally {
            biggerSpoon.put();
            log.info("Programmer with id [{}] put bigger spoon with id [{}]", id, biggerSpoon.getId());

            smallerSpoon.put();
            log.info("Programmer with id [{}] put smaller spoon with id [{}]", id, smallerSpoon.getId());
        }
    }

    public void chitChat() throws InterruptedException {
        Thread.sleep(properties.getChitChatTime());
    }

    public Plate getPlate() {
        return plate;
    }
}
