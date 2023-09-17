package org.seabattle.view;

import org.seabattle.FIeld.Field;

public class ShipPlacementView extends ViewLanterna{

    private final Field playerField = new Field();
    @Override
    public void init() {
        printField();
    }

    private void printField() {
        String fieldString = FieldToStringMapper.map(playerField);
        printStrings(fieldString.split("\n"));
    }

    @Override
    public void redirect(IView view) {
    }
}
