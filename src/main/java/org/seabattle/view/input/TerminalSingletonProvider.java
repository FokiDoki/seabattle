package org.seabattle.view.input;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class TerminalSingletonProvider {
    private static Terminal instance;
    private TerminalSingletonProvider() {
    }
    public static Terminal getInstance() throws IOException {
        if (instance == null) {
            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            instance = defaultTerminalFactory.createTerminal();
        }
        return instance;
    }
}
