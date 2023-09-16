package org.seabattle.ships;

import java.awt.*;
import java.util.List;

public class ShipThreeDesk extends Ship {
    public ShipThreeDesk(Point location, ShipDirection direction) {
        super(location,
                List.of(
                        new ShipPart(new Point(0, 0)),
                        new ShipPart(new Point(0, 1)),
                        new ShipPart(new Point(0, 2))
                ),
                direction);
    }
}
