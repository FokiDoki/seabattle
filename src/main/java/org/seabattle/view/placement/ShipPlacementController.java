package org.seabattle.view.placement;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.seabattle.FIeld.DumbShipRandomizer;
import org.seabattle.FIeld.Field;
import org.seabattle.FIeld.GameRules;
import org.seabattle.FIeld.ShipRandomizer;
import org.seabattle.ships.IShip;
import org.seabattle.ships.ShipDirection;
import org.seabattle.view.battle.BattleView;
import org.seabattle.view.input.ControlsManager;
import org.seabattle.view.input.CursorField;

import java.awt.*;
import java.util.ArrayList;

public class ShipPlacementController {

    Logger logger = LogManager.getLogger(ShipPlacementController.class);


    private final ControlsManager controlsManager = new ControlsManager();

    private final CursorField cursorField;

    @Getter
    private final Field playerField = new Field();

    @Getter
    private ArrayList<Class<? extends IShip>> availableShips = new ArrayList<>(playerField.getGameRules().getAvailableShips());

    @Getter
    private Class<? extends IShip> currentShip = availableShips.get(0);
    private int currentShipIndex = 0;

    @Getter
    private ShipDirection currentShipDirection = ShipDirection.HORIZONTAL;

    private static final String FIELD_ZONE_NAME = "field";

    private final ShipPlacementView view;


    public ShipPlacementController(Terminal terminal, ShipPlacementView view) {
        cursorField = new CursorField(terminal);
        this.view = view;

    }

    public void init() {
        cursorField.addAvailableZone(new TerminalPosition(5, 10), new TerminalPosition(24, 19),
                FIELD_ZONE_NAME);
        cursorField.reset();

        controlsManager
                .onKeyPress("Up").addListener(cursorField::moveUp).and()
                .onKeyPress("Down").addListener(cursorField::moveDown).and()
                .onKeyPress("Left").addListener(cursorField::moveLeft).and()
                .onKeyPress("Right").addListener(cursorField::moveRight).and()
                .onKeyPress("E").addListener(this::nextShip).and()
                .onKeyPress("Space").addListener(this::placeShip).and()
                .onKeyPress("R").addListener(this::rotateShip).and()
                .onKeyPress("Enter").addListener(this::redirect).and()
                .applyAll();
    }

    private void rotateShip() {
        currentShipDirection = currentShipDirection == ShipDirection.HORIZONTAL ? ShipDirection.VERTICAL : ShipDirection.HORIZONTAL;
        view.printRotationStatus();
    }

    @SneakyThrows
    private void placeShip() {
        IShip ship = getCurrentShipInstance();
        playerField.placeShip(getCurrentShipInstance());
        logger.info("Placed ship {}", ship.getPosition());
        view.printField();
        view.printShips();
    }

    private IShip getCurrentShipInstance() {
        Point cursorPosition = cursorField.getRelCursorPosition(FIELD_ZONE_NAME);
        System.out.println(cursorPosition);
        cursorPosition.x = cursorPosition.x/2;
        return GameRules.getShipInstance(cursorPosition, currentShipDirection, currentShip);
    }


    public int getAvailableShipsCount(Class<? extends IShip> ship){
        return playerField.getGameRules().getShipsCount(ship);
    }

    public void redirect() {
        BattleView battleView = new BattleView(playerField, generateEnemyField());
        view.redirect(battleView);
    }

    private Field generateEnemyField(){
        Field field = new Field();
        ShipRandomizer shipRandomizer = new DumbShipRandomizer();
        shipRandomizer.placeAllShips(field);
        return field;
    }

    private void nextShip(){
        currentShipIndex = (currentShipIndex + 1) % availableShips.size();
        currentShip = availableShips.get(currentShipIndex);
        view.printShips();
    }

}
