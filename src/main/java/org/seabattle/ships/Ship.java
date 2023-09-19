package org.seabattle.ships;

import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public abstract class Ship implements IShip {

    @Getter
    private final int sizeX;

    @Getter
    private final int sizeY;

    @Getter
    private Point position;

    @Getter
    private final ShipDirection direction;

    private final List<ShipPart> parts;

    protected Ship(Point position, List<ShipPart> parts, ShipDirection direction) {
        this.parts = parts;
        this.position = position;
        if (direction==ShipDirection.HORIZONTAL){
            rotate();
        }
        this.direction = direction;
        sizeX = parts.stream().mapToInt(part -> part.getPosition().x).max().orElse(0) + 1;
        sizeY = parts.stream().mapToInt(part -> part.getPosition().y).max().orElse(0) + 1;
    }
    @Override
    public void restore() {
        parts.forEach(ShipPart::restore);
    }
    @Override
    public boolean tryHit(Point point) {
        Optional<ShipPart> part = getPart(point);
        if (part.isPresent()) {
            ShipPart shipPart = part.get();
            shipPart.hit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAlive() {
        return getAlivePartsNumber() > 0;
    }

    private void rotate(){
        parts.forEach(part -> {
            Point position = part.getPosition();
            position.setLocation(position.y, position.x);
        });
    }




    @Override
    public boolean isPartAlive(Point absolutPosition) throws PartNotFoundException{
        return getPart(absolutPosition)
                .orElseThrow(() -> new PartNotFoundException(absolutPosition))
                .isAlive();
    }


    private Optional<ShipPart> getPart(Point absolutPosition) throws IndexOutOfBoundsException {
        return parts.stream()
                .filter(part -> part.getX()+position.x == absolutPosition.x && part.getY()+position.y == absolutPosition.y)
                .findFirst();
    }

    @Override
    public List<ShipPart> getParts(){
        return List.copyOf(parts);
    }


    @Override
    public boolean isTouching(IShip ship) {
        return parts.stream().anyMatch(part -> {
            return ship.getParts().stream().anyMatch(part2 -> {
                Point absolutPosition = part.getAbsolutPosition(position);
                Point absolutPosition2 = part2.getAbsolutPosition(ship.getPosition());
                return Math.abs(absolutPosition.x-absolutPosition2.x)<=1 && Math.abs(absolutPosition.y-absolutPosition2.y)<=1;
            });
        });
    }

    @Override
    public boolean isShipPart(Point point) {
        return getPart(point).isPresent();
    }

    private int getAlivePartsNumber() {
        return (int) parts.stream().filter(ShipPart::isAlive).count();
    }

}
