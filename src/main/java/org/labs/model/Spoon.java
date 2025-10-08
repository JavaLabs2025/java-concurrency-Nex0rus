package org.labs.model;

import java.util.concurrent.locks.ReentrantLock;

public class Spoon {
    private final int id;
    private final ReentrantLock lock;

    public Spoon(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public void take() {
        lock.lock();
    }

    public void put() {
        lock.unlock();
    }
}
