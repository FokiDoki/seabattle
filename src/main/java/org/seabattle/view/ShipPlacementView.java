package org.seabattle.view;

import org.jnativehook.GlobalScreen;
import org.seabattle.Field;
import org.seabattle.view.input.AnyKeyReleaseListener;
import org.seabattle.view.input.KeyListener;

public class ShipPlacementView implements IView{

    private Field playerField;

    @Override
    public void init() {
        System.out.println("ShipPlacementView");
        KeyListener keyPressListener = new AnyKeyReleaseListener();
        GlobalScreen.addNativeKeyListener(keyPressListener);
        keyPressListener.addListener(this::printField);
    }

    private void printField() {

    }

    @Override
    public void redirect(IView view) {
    }
}
