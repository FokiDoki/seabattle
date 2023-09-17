package org.seabattle.view.input;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.SneakyThrows;

public class CursorField {

    Terminal terminal;
    TerminalPosition startPosition;



    @SneakyThrows
    public CursorField(Terminal terminal) {
        this.terminal = terminal;
        this.startPosition = terminal.getCursorPosition();
    }

    @SneakyThrows
    public void moveCursor(int x, int y){
        TerminalPosition currentPosition = terminal.getCursorPosition();
        terminal.setCursorPosition(currentPosition.getColumn() + x, currentPosition.getRow() + y);
        terminal.flush();
    }

    public void moveUp(){
        moveCursor(0, -1);
    }

    public void moveDown(){
        moveCursor(0, 1);
    }

    public void moveLeft(){
        moveCursor(-1, 0);
    }

    public void moveRight(){
        moveCursor(1, 0);
    }

}
