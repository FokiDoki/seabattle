package org.seabattle.view.battle;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.Getter;
import org.seabattle.FIeld.Field;
import org.seabattle.view.AutoControlsManagementController;

@Getter
public class BattleController extends AutoControlsManagementController {


    private Field playerField;

    private Field enemyField;

    public BattleController(Terminal terminal, Field playerField, Field enemyField) {
        super(terminal);
        this.playerField = playerField;
        this.enemyField = enemyField;
        configureControls();
    }

    public void configureControls(){

        cursorField.addAvailableZone(new TerminalPosition(0, 0),
                new TerminalPosition(999, 999), "text");
        cursorField.addArrowListeners(controlsManager);
        controlsManager.applyAll();
    }




}
