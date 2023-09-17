package org.seabattle.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.SneakyThrows;
import org.seabattle.view.input.TerminalSingletonProvider;

import java.io.IOException;

public abstract class ViewLanterna implements IView{

    protected Terminal terminal;
    ViewLanterna(){
        try {
            this.terminal = TerminalSingletonProvider.getInstance();
        } catch (IOException e) {
            throw new RuntimeException("TerminalSingletonProvider.getInstance() failed", e);
        }
    }

    @Override
    public void redirect(IView view) {
        destroy();
        view.init();
    }

    @SneakyThrows
    protected void printStrings(String strings){
        printStrings(strings.split("\n"));
    }
    @SneakyThrows
    protected void printStrings(String... strings){
        for (String string : strings) {
            TerminalPosition terminalPosition = terminal.getCursorPosition();
            terminal.putString(string);
            terminal.setCursorPosition(terminalPosition.getColumn(), terminalPosition.getRow() + 1);
        }
        terminal.bell();
    }

    @SneakyThrows
    protected void skipLines(int lines){
        for (int i = 0; i < lines; i++) {
            terminal.putCharacter('\n');
        }
    }

    @SneakyThrows
    protected void clear(){
        terminal.clearScreen();
        terminal.bell();
    }
}
