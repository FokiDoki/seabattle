package org.seabattle.ships;

import java.awt.*;
import java.util.List;

public class ShipTwoDesk extends Ship {
    public ShipTwoDesk(Point location, ShipDirection direction) {
        super(location,
                List.of(
                        new ShipPart(new Point(0, 0)),
                        new ShipPart(new Point(0, 1))
                ),
                direction);
    }

}

