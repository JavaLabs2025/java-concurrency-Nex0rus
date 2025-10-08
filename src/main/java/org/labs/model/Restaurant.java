package org.labs.model;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Restaurant {
    private final Logger log = LoggerFactory.getLogger(Restaurant.class);
    private final PriorityBlockingQueue<Plate> platesToServe;
    private final AtomicInteger portionsLeft;

    public Restaurant(PriorityBlockingQueue<Plate> platesToServe, int maxPortions) {
        this.platesToServe = platesToServe;
        this.portionsLeft = new AtomicInteger(maxPortions);
    }

    public void makeOrder(Plate plate) {
        if (plate.getState() == PlateState.EMPTY) {
            plate.order();
            log.info("Programmer with id [{}] ordered new portion", plate.getProgrammerId());

            platesToServe.add(plate);
        }
    }

    public boolean hasPlates() {
        return !platesToServe.isEmpty();
    }

    public Plate getPlate() {
        return platesToServe.poll();
    }

    public boolean takeSoup() {
        return portionsLeft.getAndUpdate(portionCount -> portionCount > 0 ? portionCount - 1 : 0) > 0;
    }

    public boolean isServing() {
        return portionsLeft.get() > 0;
    }
}
