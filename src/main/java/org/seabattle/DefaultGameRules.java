package org.seabattle;

import org.seabattle.FIeld.GameRules;
import org.seabattle.ships.ShipFourDesk;
import org.seabattle.ships.ShipOneDesk;
import org.seabattle.ships.ShipThreeDesk;
import org.seabattle.ships.ShipTwoDesk;

import java.util.Map;

public class DefaultGameRules extends GameRules {
    public DefaultGameRules() {
        super(Map.of(
                new ShipOneDesk(), 4,
                new ShipTwoDesk(), 3,
                new ShipThreeDesk(), 2,
                new ShipFourDesk(), 1
        ));
    }
}
