package org.seabattle.FIeld;

import lombok.Getter;
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

    public Field() {
        this(10, 10, new DefaultGameRules());
    }

    public Field(int width, int height, GameRules gameRules) {
        this.width = width;
        this.height = height;
        this.gameRules = gameRules;
    }

    public void placeShip(IShip ship) {
        if (gameRules.shipCanBePlaced(ship)){
            Optional<IShip> touchingShip = getShipTouching(ship.getPosition());
            if (touchingShip.isEmpty()){
                gameRules.placeShip(ship);
                ships.add(ship);
            } else {
                throw new ShipAlreadyExistsException(touchingShip.get());
            }

        } else {
            throw new IllegalArgumentException("Ship can't be placed");
        }
    }

    public void tryHit(Point point, Player player){
        Optional<IShip> ship = getShip(point);
        if (ship.isPresent()){
            ship.get().tryHit(point);
            hitsManager.addHit(point, player);
        }
    }

    public Optional<IShip> getShipTouching(Point point){
        return ships.stream().filter(ship -> ship.isTouching(point)).findFirst();
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
