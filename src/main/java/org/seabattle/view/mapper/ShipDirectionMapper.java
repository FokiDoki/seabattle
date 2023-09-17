package org.seabattle.view.mapper;

import org.seabattle.ships.ShipDirection;

public class ShipDirectionMapper {


    public static String map(ShipDirection shipDirection) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("direction: ");
        if (shipDirection == ShipDirection.HORIZONTAL) {
            stringBuilder.append("==►");
        } else {
            stringBuilder.append("▲  ");
        }
        return stringBuilder.toString();
    }
}
