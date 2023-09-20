package org.seabattle;

import lombok.SneakyThrows;
import org.seabattle.view.menu.MainMenuView;
import org.seabattle.view.placement.ShipPlacementView;

public class Main  {


    @SneakyThrows
    public static void main(String[] args) {
        ShipPlacementView shipPlacementView = new ShipPlacementView();
        MainMenuView mainMenuView = new MainMenuView(shipPlacementView);
        mainMenuView.init();

    }
}