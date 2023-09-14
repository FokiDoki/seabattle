package org.seabattle.ships;

import org.seabattle.Strike;

import java.awt.*;
import java.util.Optional;

public interface IShip {
    int getSize();
    Point getLocation();

    void restore();

    Optional<Integer> tryHit(Point point, Strike strike);

    boolean isAlive();

}
