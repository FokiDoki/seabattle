package org.seabattle;

import org.seabattle.FIeld.ShipPlacementRules;
import org.seabattle.ships.ShipFourDesk;
import org.seabattle.ships.ShipOneDesk;
import org.seabattle.ships.ShipThreeDesk;
import org.seabattle.ships.ShipTwoDesk;

import java.util.Map;

public class DefaultShipPlacementRules extends ShipPlacementRules {
    public DefaultShipPlacementRules() {
        super(Map.of(
                ShipOneDesk.class, 4,
                ShipTwoDesk.class, 3,
                ShipThreeDesk.class, 2,
                ShipFourDesk.class, 1
        ));
    }
}
