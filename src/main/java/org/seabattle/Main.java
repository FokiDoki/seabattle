package org.seabattle;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.seabattle.view.MainMenuView;
import org.seabattle.view.ShipPlacementView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main implements ActionListener {


    @SneakyThrows
    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.exit(1);
        }
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal;


        ShipPlacementView shipPlacementView = new ShipPlacementView();
        MainMenuView mainMenuView = new MainMenuView(shipPlacementView);
        mainMenuView.init();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
    }
}