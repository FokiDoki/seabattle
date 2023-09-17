package org.seabattle.view;

import org.seabattle.FIeld.Field;
import org.seabattle.view.input.ControlsManager;
import org.seabattle.view.input.CursorField;

public class ShipPlacementView extends ViewLanterna{

    private final String header_name = """
            ███████ ███████ ████████ ██    ██ ██████
            ██      ██         ██    ██    ██ ██   ██
            ███████ █████      ██    ██    ██ ██████
                 ██ ██         ██    ██    ██ ██ 
            ███████ ███████    ██     ██████  ██     
            """;

    private final String controls = """
            Controls: Use arrow keys to move the cursor; [ENTER] - start the game
                      [R] - rotate [SPACE] - place [A] to place all randomly
            """;


    CursorField cursorField;
    ControlsManager controlsManager = new ControlsManager();

    private final Field playerField = new Field();
    @Override
    public void init() {
        printHeader();
        printField();
        printControls();
        cursorField = new CursorField(terminal);
        controlsManager
                .onKeyRelease("Up").addListener(cursorField::moveUp).and()
                .onKeyRelease("Down").addListener(cursorField::moveDown).and()
                .onKeyRelease("Left").addListener(cursorField::moveLeft).and()
                .onKeyRelease("Right").addListener(cursorField::moveRight).and()
                .applyAll();
    }

    private void printHeader() {
        skipLines(1);
        printStrings(header_name.split("\n"));
        printStrings("Place your ships on the field".split("\n"));
        skipLines(1);
    }

    private void printControls() {
        printStrings(controls.split("\n"));
    }

    private void printField() {
        String fieldString = FieldToStringMapper.map(playerField);
        printStrings(fieldString.split("\n"));
    }

    @Override
    public void redirect(IView view) {
    }
}
