package org.seabattle.view.input;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TerminalSingletonProvider {
    private static Terminal instance;
    private TerminalSingletonProvider() {
    }
    public static Terminal getInstance() throws IOException {
        if (instance == null) {
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
            defaultTerminalFactory.setTerminalEmulatorTitle("Sea Battle");
            instance = defaultTerminalFactory.createTerminal();
        }
        return instance;
    }
}
