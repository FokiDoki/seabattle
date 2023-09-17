package org.seabattle.ships;

import lombok.Getter;

import java.awt.*;

@Getter
public class ShipPart {
    private final Point position;
    private boolean isAlive = true;

    ShipPart(Point position) {
        this.position = position;
    }

    public void hit() {
        isAlive = false;
    }


    public void restore() {
        isAlive = true;
    }

    public Point getAbsolutPosition(Point shipPosition) {
        return new Point(position.x + shipPosition.x, position.y + shipPosition.y);
    }


    public boolean isAlive() {
        return isAlive;
    }

    public int getX(){
        return position.x;
    }

    public int getY(){
        return position.y;
    }


}
