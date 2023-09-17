package org.seabattle.view.placement;

import com.googlecode.lanterna.TextColor;
import lombok.SneakyThrows;
import org.seabattle.ships.IShip;
import org.seabattle.view.IView;
import org.seabattle.view.ViewLanterna;
import org.seabattle.view.mapper.FieldToStringMapper;

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
                      [R] - rotate [SPACE] - place [A] to place all randomly
            """;






    private ShipPlacementController controller;


    @SneakyThrows
    @Override
    public void init() {
        controller = new ShipPlacementController(terminal);
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
    private void printShips(){
        terminal.setCursorPosition(32,9);
        Class<? extends IShip> currentShip = controller.getCurrentShip();
        controller.getAvailableShips().forEach(ship -> {
            TextColor color = ship.equals(currentShip) ? TextColor.ANSI.BLACK_BRIGHT : TextColor.ANSI.DEFAULT;
            StringBuilder shipStringBuilder = new StringBuilder();
            String shipName = ship.getSimpleName();
            String shipCount = String.valueOf(controller.getAvailableShipsCount(ship));
            String shipFrame = frameRepository.getFrame(shipName);
            shipStringBuilder.append(shipFrame);
            shipStringBuilder.append("Left - ").append(shipCount);
            colorizeBackground(color, () -> {
                printStrings(shipStringBuilder.toString());
            });
            skipLines(1);
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
        printStrings(controls);
    }

    private void printField() {
        String fieldString = FieldToStringMapper.map(controller.getPlayerField());
        printStrings(fieldString);
    }

    @Override
    public void redirect(IView view) {
    }
}
