package org.seabattle.FIeld;

import org.seabattle.ships.IShip;
import org.seabattle.ships.ShipDirection;

import java.awt.*;
import java.util.Random;

public class DumbShipRandomizer implements ShipRandomizer{

    private Field field;
    private final Random random = new Random(System.currentTimeMillis());
    private Point GetRandomPointOnField(){
        return new Point(random.nextInt(field.getWidth()),
                random.nextInt(field.getHeight()));
    }

    private ShipDirection GetRandomDirection(){
        return random.nextBoolean() ? ShipDirection.HORIZONTAL : ShipDirection.VERTICAL;
    }

    private void placeShip(Class<? extends IShip> shipClass){
        IShip ship;
        do {
            Point point = GetRandomPointOnField();
            ShipDirection direction = GetRandomDirection();
            ship = GameRules.getShipInstance(point, direction, shipClass);
        } while (!field.isShipCanBePlaced(ship));
        field.placeShip(ship);
    }


    @Override
    public void placeAllShips(Field field) {
        this.field = field;
        GameRules gameRules = field.getGameRules();
        gameRules.getAvailableShips().forEach(ship -> {
            int shipsToPlace = gameRules.getShipsCount(ship);
            for (int i = 0; i < shipsToPlace; i++) {
                placeShip(ship);
            }
        });
    }
}
