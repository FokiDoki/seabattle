package org.seabattle.view.menu;

import com.googlecode.lanterna.terminal.Terminal;
import org.seabattle.view.AutoControlsManagementController;
import org.seabattle.view.IView;

public class MainMenuController extends AutoControlsManagementController {


    public MainMenuController(Terminal terminal, MainMenuView mainMenuView, IView redirectView) {
        super(terminal);
        controlsManager
                .onAnyKeyPress().addListener( () -> mainMenuView.redirect(redirectView))
                .and()
                .applyAll();
    }
}
