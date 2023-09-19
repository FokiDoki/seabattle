package org.seabattle.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.SneakyThrows;
import org.seabattle.view.input.TerminalSingletonProvider;

import java.io.IOException;

public abstract class ViewLanterna implements IView{

    protected Terminal terminal;
    public ViewLanterna(){
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
        terminal.flush();
    }

    @SneakyThrows
    @Override
    public void drawFrame(TerminalPosition position, Runnable drawer){
        TerminalPosition sourceTerminalPosition = terminal.getCursorPosition();
        terminal.setCursorPosition(position);
        drawer.run();
        terminal.setCursorPosition(sourceTerminalPosition);
        terminal.flush();
    }

    @SneakyThrows
    protected void skipLines(int lines){
        TerminalPosition terminalPosition = terminal.getCursorPosition();
        terminal.setCursorPosition(terminalPosition.getColumn(), terminalPosition.getRow() + lines);
    }

    @SneakyThrows
    protected void colorize(TextColor color, Runnable runnable){
        terminal.setForegroundColor(color);
        runnable.run();
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    @SneakyThrows
    protected void colorizeBackground(TextColor color, Runnable runnable){
        terminal.setBackgroundColor(color);
        runnable.run();
        terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }

    @SneakyThrows
    protected void clear(){
        terminal.clearScreen();
        terminal.bell();
    }

    @Override
    public void destroy() {
        clear();
    }
}
