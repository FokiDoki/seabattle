package org.seabattle.view.mapper;

import org.seabattle.ships.IShip;

public class ShipSelectorMapper {

    public static String map(IShip ship, String shipCount){
        StringBuilder shipStringBuilder = new StringBuilder();
        String shipFrame = ShipMapper.map(ship);
        shipStringBuilder.append(shipFrame);
        shipStringBuilder.append("Left - ").append(shipCount);
        return shipStringBuilder.toString();
    }
}
