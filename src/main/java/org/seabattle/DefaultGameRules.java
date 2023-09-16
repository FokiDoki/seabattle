package org.seabattle;

import org.seabattle.ships.*;

import java.util.Map;

public class DefaultGameRules extends GameRules{
    public DefaultGameRules() {
        super(Map.of(
                ShipOneDesk.class, 4,
                ShipTwoDesk.class, 3,
                ShipThreeDesk.class, 2,
                ShipFourDesk.class, 1
        ));
    }
}
