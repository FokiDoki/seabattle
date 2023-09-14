package org.seabattle.ships;

import org.seabattle.Strike;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractShip implements Ship{

    private final int size;
    private final Point location;

    private int[] partsHealth;

    private final int defaultHealth;

    public AbstractShip(int size, Point location, int defaultHealth) {
        this.size = size;
        this.location = location;
        this.partsHealth = new int[size];
        this.defaultHealth = defaultHealth;
        restore();
    }

    public AbstractShip(int size, Point location) {
        this(size, location, 100);
    }

    @Override
    public void restore() {
        Arrays.fill(partsHealth, defaultHealth);
    }

    @Override
    public Optional<Integer> tryHit(Point point, Strike strike) {
        return Optional.empty();
    }

    @Override
    public boolean isAlive() {
        return getTotalHealth() > 0;
    }

    private int getTotalHealth() {
        return Arrays.stream(partsHealth).sum();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Point getLocation() {
        return location;
    }


}
