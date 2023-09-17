package org.seabattle.view;

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
    protected void printStrings(String... strings){
        for (String string : strings) {
            terminal.putString(string);
            terminal.putCharacter('\n');
        }
        terminal.bell();
    }

    @SneakyThrows
    protected void skipLines(int lines){
        for (int i = 0; i < lines; i++) {

            printStrings(" ".repeat(terminal.getTerminalSize().getColumns()));
        }
    }

    @SneakyThrows
    protected void clear(){
        terminal.clearScreen();
        terminal.bell();
    }
}