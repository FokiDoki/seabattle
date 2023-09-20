package org.seabattle.FIeld;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.seabattle.CellStatus;
import org.seabattle.DefaultShipPlacementRules;
import org.seabattle.ShipAlreadyExistsException;
import org.seabattle.ships.IShip;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Field {

    private final int width;
    private final int height;
    private final ShipPlacementRules shipPlacementRules;
    private final List<IShip> ships = new ArrayList<>();
    private final HitsManager hitsManager = new HitsManager();
    private final Logger logger = LogManager.getLogger(Field.class);

    public Field() {
        this(10, 10, new DefaultShipPlacementRules());
    }

    public Field(int width, int height, ShipPlacementRules shipPlacementRules) {
        this.width = width;
        this.height = height;
        this.shipPlacementRules = shipPlacementRules;
    }

    public void placeShip(IShip ship) {
        if (!isShipInField(ship)){
            throw new IllegalArgumentException("Ship can't be placed: out of field");
        }
        if (!shipPlacementRules.isLimitReached(ship)){
            Optional<IShip> touchingShip = getShipTouching(ship);
            if (touchingShip.isEmpty()){
                shipPlacementRules.placeShip(ship);
                ships.add(ship);

            } else {
                throw new ShipAlreadyExistsException(touchingShip.get());
            }

        } else {
            throw new IllegalArgumentException("Ship can't be placed: limit reached");
        }
    }

    private boolean isShipInField(IShip ship){
        Point shipPosition = ship.getPosition();
        return shipPosition.x >= 0 && shipPosition.y >= 0
                && shipPosition.x + ship.getSizeX() <= width && shipPosition.y + ship.getSizeY() <= height;
    }

    public boolean isShipCanBePlaced(IShip ship){
        return isShipInField(ship) && getShipTouching(ship).isEmpty();
    }

    public boolean tryHit(Point point){
        Optional<IShip> ship = getShip(point);

        if (ship.isPresent()){
            logger.debug("Strike to {}: hit", point);
            hitsManager.addHit(point, CellStatus.HIT);
            ship.get().tryHit(point);
            return true;
        } else {
            logger.debug("Strike to {}: missed", point);
            hitsManager.addHit(point, CellStatus.MISS);
            return false;
        }

    }

    public Optional<IShip> getShipTouching(IShip ship){
        return ships.stream().filter(ship2 ->
                ship2.isTouching(ship)
        ).findFirst();
    }

    public Optional<IShip> getShip(Point point) {
        return ships.stream().filter(ship -> ship.isShipPart(point)).findFirst();
    }

    public CellStatus getCellStatus(Point point) {
        Optional<IShip> ship = getShip(point);
        if (ship.isPresent()) {
            return ship.get().isPartAlive(point) ? CellStatus.SHIP : CellStatus.HIT;
        } else if (hitsManager.isHit(point)){
            return CellStatus.MISS;
        } else {
            return CellStatus.EMPTY;
        }
    }

    public boolean isAllShipsDestroyed(){
        return ships.stream().noneMatch(IShip::isAlive);
    }

    public int getAliveShipsCount(){
        return (int) ships.stream().filter(IShip::isAlive).count();
    }


}
