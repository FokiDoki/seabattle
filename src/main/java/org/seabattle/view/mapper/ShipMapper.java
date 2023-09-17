package org.seabattle.view.mapper;

import org.seabattle.ships.Ship;
import org.seabattle.ships.ShipDirection;
import org.seabattle.ships.ShipPart;

import java.util.List;

public class ShipMapper {


    public static String map(Ship ship){
        List<ShipPart> parts = ship.getParts();
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x<ship.getSizeX(); x++){
            for (int y = 0; y<ship.getSizeY(); y++){
                for (ShipPart part: parts){
                    if (part.getX()==x && part.getY()==y){
                        stringBuilder.append(FieldToStringMapper.SHIP_CELL.repeat(2));
                    } else {
                        stringBuilder.append(FieldToStringMapper.EMPTY_CELL.repeat(2));
                    }
                }
            }
            stringBuilder.append("\n");
        }
    }
}
