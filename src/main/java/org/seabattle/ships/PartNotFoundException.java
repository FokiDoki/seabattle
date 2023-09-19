package org.seabattle.ships;

import java.awt.*;

public class PartNotFoundException extends RuntimeException{
    public PartNotFoundException(Point position) {
        super("Part not found at position " + position.toString());
    }
}
