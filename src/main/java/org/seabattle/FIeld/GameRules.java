package org.seabattle.FIeld;

import lombok.SneakyThrows;
import org.seabattle.ships.IShip;
import org.seabattle.ships.ShipDirection;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameRules {
    Map<Class<? extends IShip>, Integer> shipsCount;

    public GameRules(Map<Class<? extends IShip>, Integer> shipsCount) {
        this.shipsCount = new HashMap<>(shipsCount);
    }

    public Set<Class<? extends IShip>> getAvailableShips(){
        return shipsCount.keySet();
    }

    public int getShipsCount(Class<? extends IShip> ship){
        return shipsCount.get(ship);
    }


    public boolean isLimitReached(IShip ship){
        return shipsCount.get(ship.getClass()) <= 0;
    }

    public void placeShip(IShip ship){
        if (!isLimitReached(ship)){
            shipsCount.put(ship.getClass(), shipsCount.get(ship.getClass()) - 1);
        } else {
            throw new IllegalArgumentException("Ship can't be placed");
        }
    }
    @SneakyThrows
    public static IShip getShipInstance(Class<? extends IShip> ship){
        return ship.getConstructor()
                .newInstance();
    }

    @SneakyThrows
    public static IShip getShipInstance(Point position, ShipDirection direction, Class<? extends IShip> ship){
        return ship.getConstructor(Point.class, ShipDirection.class)
                .newInstance(position, direction);
    }



    public void removeShip(IShip ship){
        shipsCount.put(ship.getClass(), shipsCount.get(ship) + 1);
    }
}
