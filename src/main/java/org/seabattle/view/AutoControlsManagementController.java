package org.seabattle.view;

import com.googlecode.lanterna.terminal.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.seabattle.view.input.ControlsManager;
import org.seabattle.view.input.CursorField;
import org.seabattle.view.placement.ShipPlacementController;

public class AutoControlsManagementController implements IController{
    protected Logger logger = LogManager.getLogger(ShipPlacementController.class);
    protected final ControlsManager controlsManager = new ControlsManager();

    protected final CursorField cursorField;

    public AutoControlsManagementController(Terminal terminal) {
        cursorField = new CursorField(terminal);
    }

    @Override
    public void destroy() {
        controlsManager.removeAll();
    }
}
