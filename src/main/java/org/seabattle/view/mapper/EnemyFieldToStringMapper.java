package org.seabattle.view.mapper;

import org.seabattle.FIeld.Field;

import java.awt.*;

public class EnemyFieldToStringMapper extends FieldToStringMapper{

    @Override
    protected void getCellStatusByCords(Field field, StringBuilder stringBuilder, int x, int y) {
        switch (field.getCellStatus(new Point(x, y))) {
            case EMPTY, SHIP -> stringBuilder.append(EMPTY_CELL);
            case HIT -> stringBuilder.append(HIT_CELL);
            case MISS -> stringBuilder.append(MISS_CELL);
        }
    }
}
