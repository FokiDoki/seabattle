package org.seabattle.ships;

import java.awt.*;
import java.util.List;

public class ShipOneDesk extends Ship {
    public ShipOneDesk(Point location, ShipDirection direction) {
        super(location,
                List.of(
                        new ShipPart(new Point(0, 0))
                ),
                direction);
    }

    public ShipOneDesk(){
        this(new Point(0,0), ShipDirection.HORIZONTAL);
    }
}
