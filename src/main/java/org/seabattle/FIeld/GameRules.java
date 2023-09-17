package org.seabattle.FIeld;

import org.seabattle.ships.IShip;

import java.util.Map;
import java.util.Set;

public class GameRules {
    Map<Class<? extends IShip>, Integer> shipsCount;

    public GameRules(Map<Class<? extends IShip>, Integer> shipsCount) {
        this.shipsCount = shipsCount;
    }

    public Set<Class<? extends IShip>> getAvailableShips(){
        return shipsCount.keySet();
    }

    public int getShipsCount(Class<? extends IShip> shipClass){
        return shipsCount.get(shipClass);
    }

    public boolean shipCanBePlaced(IShip ship){
        return shipsCount.get(ship.getClass()) > 0;
    }

    public void placeShip(IShip ship){
        if (shipCanBePlaced(ship)){
            shipsCount.put(ship.getClass(), shipsCount.get(ship.getClass()) - 1);
        } else {
            throw new IllegalArgumentException("Ship can't be placed");
        }
    }

    public void removeShip(IShip ship){
        shipsCount.put(ship.getClass(), shipsCount.get(ship.getClass()) + 1);
    }
}
