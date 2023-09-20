package org.seabattle.view.mapper;

import org.seabattle.ships.IShip;
import org.seabattle.ships.ShipPart;

import java.util.List;

public class ShipMapper implements Mapper<IShip>{


    public String map(IShip ship){
        List<ShipPart> parts = ship.getParts();
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y<ship.getSizeY(); y++){
            for (int x = 0; x<ship.getSizeX(); x++){
                int finalX = x;
                int finalY = y;
                parts.stream().filter(part -> part.getX()== finalX && part.getY()== finalY).findFirst().ifPresentOrElse(
                        part -> stringBuilder.append(FieldToStringMapper.SHIP_CELL),
                        () -> stringBuilder.append(FieldToStringMapper.EMPTY_CELL)
                );
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
