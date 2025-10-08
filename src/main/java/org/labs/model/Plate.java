package org.labs.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Plate implements Comparable<Plate> {
    private final AtomicReference<PlateState> plateState;
    private final AtomicInteger numOfRefills;

    public Plate() {
        this.plateState = new AtomicReference<>(PlateState.EMPTY);
        this.numOfRefills = new AtomicInteger(0);
    }

    public PlateState getState() {
        return plateState.get();
    }

    public void refill() {
        plateState.set(PlateState.FULL);
        numOfRefills.incrementAndGet();
    }

    public void finish() {
        plateState.set(PlateState.EMPTY);
    }

    public void order() {
        plateState.set(PlateState.ORDERED);
    }

    @Override
    public int compareTo(Plate o) {
        return Integer.compare(this.numOfRefills.get(), o.numOfRefills.get());
    }
}
