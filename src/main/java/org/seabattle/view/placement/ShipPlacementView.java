package org.seabattle.view.placement;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import lombok.SneakyThrows;
import org.seabattle.FIeld.ShipPlacementRules;
import org.seabattle.ships.IShip;
import org.seabattle.view.ViewLanterna;
import org.seabattle.view.mapper.FieldToStringMapper;
import org.seabattle.view.mapper.ShipDirectionMapper;
import org.seabattle.view.mapper.ShipSelectorMapper;

import java.util.Collections;

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

    private final ShipSelectorMapper shipSelectorMapper = new ShipSelectorMapper();
    private final FieldToStringMapper fieldToStringMapper = new FieldToStringMapper();
    private final ShipDirectionMapper shipDirectionMapper = new ShipDirectionMapper();

    @SneakyThrows
    @Override
    public void init() {
        controller = new ShipPlacementController(terminal, this);
        printHeader();
        printField();
        printControls();
        printShips();
        printRotationStatus();
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
                String shipFrame = shipSelectorMapper.map(Collections.singletonMap(ShipPlacementRules.getShipInstance(ship),
                        String.valueOf(controller.getAvailableShipsCount(ship))));
                colorizeBackground(color, () -> {
                    printStrings(shipFrame);
                });
                skipLines(1);
            });
        });
    }

    public void printRotationStatus(){
        drawFrame(new TerminalPosition(32, 7), () -> {
            String rotationStatus = shipDirectionMapper.map(controller.getCurrentShipDirection());
            printStrings(rotationStatus);
        });
    }

    public void updateShipFields(){
        printField();
        printShips();
    }


    private void printControls() {
        drawFrame(new TerminalPosition(0,21), () -> {
            printStrings(controls);
        });

    }

    public void printField() {
        drawFrame(new TerminalPosition(0,8), () -> {
            String fieldString = fieldToStringMapper.map(controller.getPlayerField());
            printStrings(fieldString);
        });
    }

    public void printError(String error){
        drawFrame(new TerminalPosition(45, 15), () -> {
            colorize(TextColor.ANSI.RED, () -> {
                printStrings("Error: " + error);
            });
        });
    }


    @Override
    public void destroy() {
        controller.destroy();
        super.destroy();
    }

}
