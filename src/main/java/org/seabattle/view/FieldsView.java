package org.seabattle.view;

import org.seabattle.FIeld.Field;

public class FieldsView {

    private Field playerField;
    private Field enemyField;

    public FieldsView(Field playerField, Field enemyField) {
        this.playerField = playerField;
        this.enemyField = enemyField;
    }

    public void update() {
        throw new UnsupportedOperationException("Not implemented");
    }


}
