package org.seabattle.view;

import com.googlecode.lanterna.TerminalPosition;
import lombok.SneakyThrows;
import org.seabattle.FIeld.Field;
import org.seabattle.ships.IShip;
import org.seabattle.view.input.ControlsManager;
import org.seabattle.view.input.CursorField;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

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

    FrameRepository frameRepository = new FrameRepository("src\\main\\resources\\frames\\ships");

    private final Field playerField = new Field();
    @SneakyThrows
    @Override
    public void init() {
        printHeader();
        printField();
        printControls();
        terminal.setCursorPosition(32,9);
        printShips();
        cursorField = new CursorField(terminal);
        cursorField.addAvailableZone(new TerminalPosition(5, 10), new TerminalPosition(24, 19), "field");
        cursorField.reset();
        controlsManager
                .onKeyPress("Up").addListener(cursorField::moveUp).and()
                .onKeyPress("Down").addListener(cursorField::moveDown).and()
                .onKeyPress("Left").addListener(cursorField::moveLeft).and()
                .onKeyPress("Right").addListener(cursorField::moveRight).and()
                .applyAll();

    }

    private void printHeader() {
        skipLines(1);
        printStrings(header_name);
        printStrings("Place your ships on the field");
        skipLines(1);
    }

    private void printShips(){
        Set<Class<? extends IShip>> ships = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
        ships.addAll(playerField.getGameRules().getAvailableShips());
        ships.forEach(ship -> {
            StringBuilder shipStringBuilder = new StringBuilder();
            String shipName = ship.getSimpleName();
            String shipCount = String.valueOf(playerField.getGameRules().getShipsCount(ship));
            String shipFrame = frameRepository.getFrame(shipName);
            shipStringBuilder.append(shipFrame);
            shipStringBuilder.append("Left - ").append(shipCount).append("\n \n");
            printStrings(shipStringBuilder.toString());
        });

    }

    private void printControls() {
        printStrings(controls);
    }

    private void printField() {
        String fieldString = FieldToStringMapper.map(playerField);
        printStrings(fieldString);
    }

    @Override
    public void redirect(IView view) {
    }
}
