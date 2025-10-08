package org.labs.simulation;

import java.util.List;

import org.labs.model.Plate;
import org.labs.model.Programmer;
import org.labs.model.Restaurant;
import org.labs.model.Waiter;

public record Simulation(
        Restaurant restaurant,
        List<Programmer> programmers,
        List<Waiter> waiters,
        List<Plate> plates
) {
}