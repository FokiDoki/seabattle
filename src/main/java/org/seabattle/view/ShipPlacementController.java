package org.seabattle.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.Getter;
import org.seabattle.FIeld.Field;
import org.seabattle.ships.IShip;
import org.seabattle.view.input.ControlsManager;
import org.seabattle.view.input.CursorField;

import java.util.ArrayList;

public class ShipPlacementController {

    private final ControlsManager controlsManager = new ControlsManager();

    private final CursorField cursorField;

    @Getter
    private final Field playerField = new Field();

    @Getter
    private ArrayList<Class<? extends IShip>> availableShips = new ArrayList<>(playerField.getGameRules().getAvailableShips());

    @Getter
    private Class<? extends IShip> currentShip = availableShips.get(0);


    public ShipPlacementController(Terminal terminal) {
        cursorField = new CursorField(terminal);
    }

    private void setUpControls() {
        cursorField.addAvailableZone(new TerminalPosition(5, 10), new TerminalPosition(24, 19), "field");
        cursorField.reset();

        controlsManager
                .onKeyPress("Up").addListener(cursorField::moveUp).and()
                .onKeyPress("Down").addListener(cursorField::moveDown).and()
                .onKeyPress("Left").addListener(cursorField::moveLeft).and()
                .onKeyPress("Right").addListener(cursorField::moveRight).and()
                .applyAll();
    }

    public int getAvailableShipsCount(Class<? extends IShip> shipClass){
        return playerField.getGameRules().getShipsCount(shipClass);
    }

}
