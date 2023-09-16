package org.seabattle.ships;


import java.awt.*;

public interface IShip {
    Point getPosition();

    void restore();

    boolean tryHit(Point point);

    boolean isTouching(Point point);

    boolean isAlive();

    boolean isPartAlive(Point absolutPosition);


}
