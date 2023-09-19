package org.seabattle.ships;


import java.awt.*;
import java.util.List;

public interface IShip extends Cloneable{

    int getSizeX();

    int getSizeY();
    Point getPosition();

    void restore();

    boolean tryHit(Point point);

    boolean isTouching(IShip ship);

    boolean isShipPart(Point point);

    boolean isAlive();

    boolean isPartAlive(Point absolutPosition);

    List<ShipPart> getParts();

    ShipDirection getDirection();

}
