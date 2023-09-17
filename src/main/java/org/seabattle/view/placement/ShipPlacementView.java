package org.seabattle.view.placement;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import lombok.SneakyThrows;
import org.seabattle.ships.IShip;
import org.seabattle.view.IView;
import org.seabattle.view.ViewLanterna;
import org.seabattle.view.mapper.FieldToStringMapper;
import org.seabattle.view.mapper.ShipSelectorMapper;

public class ShipPlacementView extends ViewLanterna {

    private final String header_name = """
            ███████ ███████ ████████ ██    ██ ██████
            ██      ██         ██    ██    ██ ██   ██
            ███████ █████      ██    ██    ██ ██████
                 ██ ██         ██    ██    ██ ██ 
            ███████ ███████    ██     ██████  ██     
            """;
    private final String controls = """
            Controls: Use arrow keys to move the cursor; [ENTER] - start the game
                      [R] - rotate [SPACE] - place [A] random [e] next ship
            """;






    private ShipPlacementController controller;


    @SneakyThrows
    @Override
    public void init() {
        controller = new ShipPlacementController(terminal, this);
        printHeader();
        printField();
        printControls();
        printShips();
        controller.init();
    }

    private void printHeader() {
        skipLines(1);
        printStrings(header_name);
        printStrings("Place your ships on the field");
        skipLines(1);
    }

    @SneakyThrows
    public void printShips(){
        drawFrame(new TerminalPosition(32,9), () ->{
            Class<? extends IShip> currentShip = controller.getCurrentShip();
            controller.getAvailableShips().forEach(ship -> {
                TextColor color = ship.equals(currentShip) ? TextColor.ANSI.BLACK_BRIGHT : TextColor.ANSI.DEFAULT;
                String shipFrame = ShipSelectorMapper.map(controller.getShipInstance(ship),
                        String.valueOf(controller.getAvailableShipsCount(ship)));
                colorizeBackground(color, () -> {
                    printStrings(shipFrame);
                });
                skipLines(1);
            });
        });
    }

    @SneakyThrows
    private void colorize(TextColor color, Runnable runnable){
        terminal.setForegroundColor(color);
        runnable.run();
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    @SneakyThrows
    private void colorizeBackground(TextColor color, Runnable runnable){
        terminal.setBackgroundColor(color);
        runnable.run();
        terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }

    private void printControls() {
        drawFrame(new TerminalPosition(0,21), () -> {
            printStrings(controls);
        });

    }

    public void printField() {
        drawFrame(new TerminalPosition(0,8), () -> {
            String fieldString = FieldToStringMapper.map(controller.getPlayerField());
            printStrings(fieldString);
        });
    }

    @Override
    public void redirect(IView view) {
    }
}
