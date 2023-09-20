package org.seabattle.view.placement;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.Getter;
import lombok.SneakyThrows;
import org.seabattle.FIeld.DumbShipRandomizer;
import org.seabattle.FIeld.Field;
import org.seabattle.FIeld.ShipPlacementRules;
import org.seabattle.FIeld.ShipRandomizer;
import org.seabattle.ships.IShip;
import org.seabattle.ships.ShipDirection;
import org.seabattle.view.AutoControlsManagementController;
import org.seabattle.view.battle.BattleView;

import java.awt.*;
import java.util.ArrayList;

public class ShipPlacementController extends AutoControlsManagementController {
    private final ShipRandomizer shipRandomizer = new DumbShipRandomizer();

    @Getter
    private final Field playerField = new Field();

    @Getter
    private ArrayList<Class<? extends IShip>> availableShips = new ArrayList<>(playerField.getShipPlacementRules().getAvailableShips());

    @Getter
    private Class<? extends IShip> currentShip = availableShips.get(0);
    private int currentShipIndex = 0;

    @Getter
    private ShipDirection currentShipDirection = ShipDirection.HORIZONTAL;

    private static final String FIELD_ZONE_NAME = "field";

    private final ShipPlacementView view;


    public ShipPlacementController(Terminal terminal, ShipPlacementView view) {
        super(terminal);
        this.view = view;

    }

    public void init() {
        cursorField.addAvailableZone(new TerminalPosition(5, 10), new TerminalPosition(24, 19),
                FIELD_ZONE_NAME);
        cursorField.reset();
        cursorField.addArrowListeners(controlsManager);
        controlsManager
                .onKeyPress("E").addListener(this::nextShip).and()
                .onKeyPress("Space").addListener(this::placeShip).and()
                .onKeyPress("R").addListener(this::rotateShip).and()
                .onKeyPress("A").addListener(this::placeShipsRandomly).and()
                .onKeyPress("Enter").addListener(this::tryRedirect).and()
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
        view.updateShipFields();
    }

    private IShip getCurrentShipInstance() {
        Point cursorPosition = cursorField.getPixelCursorPosition(FIELD_ZONE_NAME);
        return ShipPlacementRules.getShipInstance(cursorPosition, currentShipDirection, currentShip);
    }

    private void placeShipsRandomly(){
        shipRandomizer.placeAllShips(playerField);
        view.updateShipFields();
    }




    public int getAvailableShipsCount(Class<? extends IShip> ship){
        return playerField.getShipPlacementRules().getShipsCount(ship);
    }

    public void tryRedirect(){
        if (playerField.getShipPlacementRules().isAllShipsPlaced()){
            redirect();
        } else {
            view.printError("Not all ships are placed.\n Use [A] to place ships randomly \n or [E] to select next ship");
        }
    }

    public void redirect() {
        BattleView battleView = new BattleView(playerField, generateEnemyField());
        view.redirect(battleView);
    }

    private Field generateEnemyField(){
        Field field = new Field();
        shipRandomizer.placeAllShips(field);
        return field;
    }

    private void nextShip(){
        currentShipIndex = (currentShipIndex + 1) % availableShips.size();
        currentShip = availableShips.get(currentShipIndex);
        view.printShips();
    }
}
