package org.seabattle.view.mapper;

import org.seabattle.ships.IShip;

import java.util.Map;

public class ShipSelectorMapper implements Mapper<Map<IShip, String>>{

    ShipMapper shipMapper = new ShipMapper();

    public String map(Map<IShip, String> shipModel){
        StringBuilder shipStringBuilder = new StringBuilder();
        String shipFrame = shipMapper.map(shipModel.keySet().iterator().next());
        shipStringBuilder.append(shipFrame);
        shipStringBuilder.append("Left - ").append(shipModel.values().iterator().next());
        return shipStringBuilder.toString();
    }
}
