package org.seabattle.ships;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;


public class ShipTest {


    @Test
    public void tryHitUp() {
        ShipTwoDesk ship = new ShipTwoDesk(new Point(0, 0), ShipDirection.VERTICAL);
        ship.tryHit(new Point(0, 1));
        assertFalse(ship.isPartAlive(new Point(0, 1)));
    }

    @Test
    public void tryHitWrongRight() {
        ShipTwoDesk ship = new ShipTwoDesk(new Point(0, 0), ShipDirection.HORIZONTAL);
        ship.tryHit(new Point(-1, 0));
        assertTrue(ship.isPartAlive(new Point(1, 0)));
    }

    @Test
    public void tryHitRight() {
        ShipTwoDesk ship = new ShipTwoDesk(new Point(0, 0), ShipDirection.HORIZONTAL);
        ship.tryHit(new Point(1, 0));
        assertFalse(ship.isPartAlive(new Point(1, 0)));
    }

    @Test
    public void tryHitWrongDown() {
        ShipTwoDesk ship = new ShipTwoDesk(new Point(0, 0), ShipDirection.VERTICAL);
        ship.tryHit(new Point(0, -1));
        assertTrue(ship.isPartAlive(new Point(0, 1)));
    }

    @Test
    public void isAlive() {
        IShip ship = new ShipFourDesk(new Point(0, 0), ShipDirection.HORIZONTAL);
        assertTrue(ship.isAlive());
    }
    @Test
    public void isAliveFalse() {
        IShip ship = new ShipOneDesk(new Point(0, 0), ShipDirection.HORIZONTAL);
        ship.tryHit(new Point(0, 0));
        assertFalse(ship.isAlive());
    }

    @Test
    public void testRestore() {
        IShip ship = new ShipFourDesk(new Point(0, 0), ShipDirection.HORIZONTAL);
        ship.tryHit(new Point(0, 0));
        ship.restore();
        assertTrue(ship.isAlive());
    }
}