package org.seabattle.FIeld;

import org.seabattle.ships.IShip;

import java.util.Map;
import java.util.Set;

public class GameRules {
    Map<IShip, Integer> shipsCount;

    public GameRules(Map<IShip, Integer> shipsCount) {
        this.shipsCount = shipsCount;
    }

    public Set<IShip> getAvailableShips(){
        return shipsCount.keySet();
    }

    public int getShipsCount(IShip ship){
        return shipsCount.get(ship);
    }

    public boolean shipCanBePlaced(IShip ship){
        return shipsCount.get(ship.getClass()) > 0;
    }

    public void placeShip(IShip ship){
        if (shipCanBePlaced(ship)){
            shipsCount.put(ship, shipsCount.get(ship.getClass()) - 1);
        } else {
            throw new IllegalArgumentException("Ship can't be placed");
        }
    }

    public void removeShip(IShip ship){
        shipsCount.put(ship, shipsCount.get(ship.getClass()) + 1);
    }
}
