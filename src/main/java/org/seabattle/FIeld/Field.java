package org.seabattle.FIeld;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.seabattle.CellStatus;
import org.seabattle.DefaultGameRules;
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
    private final GameRules gameRules;
    private final List<IShip> ships = new ArrayList<>();
    private final HitsManager hitsManager = new HitsManager();
    private final Logger logger = LogManager.getLogger(Field.class);

    public Field() {
        this(10, 10, new DefaultGameRules());
    }

    public Field(int width, int height, GameRules gameRules) {
        this.width = width;
        this.height = height;
        this.gameRules = gameRules;
    }

    public void placeShip(IShip ship) {
        if (!gameRules.isLimitReached(ship)){
            Optional<IShip> touchingShip = getShipTouching(ship);
            if (touchingShip.isEmpty()){
                gameRules.placeShip(ship);
                ships.add(ship);

            } else {
                throw new ShipAlreadyExistsException(touchingShip.get());
            }

        } else {
            throw new IllegalArgumentException("Ship can't be placed: limit reached");
        }
    }

    public void tryHit(Point point){
        Optional<IShip> ship = getShip(point);
        if (ship.isPresent()){
            logger.debug("Strike to {}: hit", point);
            ship.get().tryHit(point);
        } else {
            logger.debug("Strike to {}: missed", point);
        }
        hitsManager.addHit(point);
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


}
