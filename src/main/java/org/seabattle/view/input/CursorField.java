package org.seabattle.view.input;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CursorField {

    private final Terminal terminal;
    private final Logger logger = LogManager.getLogger(CursorField.class);

    private final List<Zone> availableZones = new ArrayList<>();


    @SneakyThrows
    public CursorField(Terminal terminal) {
        this.terminal = terminal;
    }

    public CursorField addAvailableZone(TerminalPosition start, TerminalPosition end, String name){
        availableZones.add(new Zone(start, end, name));
        return this;
    }


    public void addArrowListeners(ControlsManager controlsManager){
        controlsManager
                .onKeyPress("Up").addListener(this::moveUp).and()
                .onKeyPress("Down").addListener(this::moveDown).and()
                .onKeyPress("Left").addListener(this::moveLeft).and()
                .onKeyPress("Right").addListener(this::moveRight);
    }

    private Zone getZoneByName(String name){
        System.out.println(availableZones);
        return availableZones.stream()
                .filter(zone -> zone.name.equals(name))
                .findFirst().orElse(null);
    }

    @SneakyThrows
    public void moveCursor(int x, int y){
        TerminalPosition currentPosition = terminal.getCursorPosition();
        TerminalPosition newPosition = new TerminalPosition(currentPosition.getColumn() + x, currentPosition.getRow() + y);
        logger.debug("Moving cursor from {} to {}", currentPosition, newPosition);
        if (availableZones.stream().noneMatch(zone -> zone.contains(newPosition))){
            logger.debug("Cursor can't be moved to {}", newPosition);
            return;
        }
        terminal.setCursorPosition(newPosition);
        terminal.flush();
    }

    @SneakyThrows
    public void reset(){
        logger.debug("Resetting cursor position to {}", availableZones.get(0).start);
        terminal.setCursorPosition(availableZones.get(0).start);
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

    @SneakyThrows
    public Point getRelCursorPosition(String zoneName){
        Zone zone = getZoneByName(zoneName);
        TerminalPosition cursorPosition = terminal.getCursorPosition();
        TerminalPosition zoneStartPosition = zone.start;
         Point relativePos = new Point(cursorPosition.getColumn() - zoneStartPosition.getColumn(),
                cursorPosition.getRow() - zoneStartPosition.getRow());
        logger.debug("Relative position of cursor in zone {} is {}", zoneName, relativePos);
        return relativePos;
    }

    public Point getPixelCursorPosition(String zoneName){
        Point relativePos = getRelCursorPosition(zoneName);
        relativePos.x = relativePos.x/2;
        return relativePos;
    }

    private static class Zone{
        private final TerminalPosition start;
        private final TerminalPosition end;

        private final String name;

        public Zone(TerminalPosition start, TerminalPosition end, String name) {
            this.start = start;
            this.end = end;
            this.name = name;
        }

        public Zone(String name){
            this.start = null;
            this.end = null;
            this.name = name;
        }



        public boolean contains(TerminalPosition position){
            return position.getColumn() >= start.getColumn() && position.getColumn() <= end.getColumn()
                    && position.getRow() >= start.getRow() && position.getRow() <= end.getRow();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Zone zone = (Zone) o;
            return Objects.equals(name, zone.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

}
