package org.seabattle.view.placement;

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
    private ArrayList<IShip> availableShips = new ArrayList<>(playerField.getGameRules().getAvailableShips());

    @Getter
    private IShip currentShip = availableShips.get(0);
    private int currentShipIndex = 0;

    private final ShipPlacementView view;


    public ShipPlacementController(Terminal terminal, ShipPlacementView view) {
        cursorField = new CursorField(terminal);
        this.view = view;

    }

    public void init() {
        cursorField.addAvailableZone(new TerminalPosition(5, 10), new TerminalPosition(24, 19), "field");
        cursorField.reset();

        controlsManager
                .onKeyPress("Up").addListener(cursorField::moveUp).and()
                .onKeyPress("Down").addListener(cursorField::moveDown).and()
                .onKeyPress("Left").addListener(cursorField::moveLeft).and()
                .onKeyPress("Right").addListener(cursorField::moveRight).and()
                .onKeyPress("E").addListener(this::nextShip).and()
                .applyAll();
    }

    public int getAvailableShipsCount(IShip ship){
        return playerField.getGameRules().getShipsCount(ship);
    }

    private void nextShip(){
        currentShipIndex = (currentShipIndex + 1) % availableShips.size();
        currentShip = availableShips.get(currentShipIndex);
        view.printShips();
    }

}
