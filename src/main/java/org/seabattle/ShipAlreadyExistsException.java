package org.seabattle;

import org.seabattle.ships.IShip;

public class ShipAlreadyExistsException extends RuntimeException{
    public ShipAlreadyExistsException(IShip ExistingShip) {
        super("Ship at " + ExistingShip.getPosition() + " already exists");
    }
}
