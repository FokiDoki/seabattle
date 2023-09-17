package org.seabattle.view.input;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CursorField {

    private final Terminal terminal;
    private final TerminalPosition startPosition;
    Logger logger = LogManager.getLogger(CursorField.class);

    List<Zone> availableZones = new ArrayList<>();

    Map<Zone, List<Runnable>> zoneTriggers = new HashMap<>();



    @SneakyThrows
    public CursorField(Terminal terminal) {
        this.terminal = terminal;
        this.startPosition = terminal.getCursorPosition();
    }

    public CursorField addAvailableZone(TerminalPosition start, TerminalPosition end, String name){
        availableZones.add(new Zone(start, end, name));
        return this;
    }

    public CursorField removeAvailableZone(String name){
        availableZones.remove(new Zone(name));
        return this;
    }


    private Zone getZoneByName(String name){
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
