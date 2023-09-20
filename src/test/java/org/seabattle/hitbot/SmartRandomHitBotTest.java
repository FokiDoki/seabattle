package org.seabattle.hitbot;

import org.junit.Before;
import org.junit.Test;
import org.seabattle.FIeld.DumbShipRandomizer;
import org.seabattle.FIeld.Field;
import org.seabattle.ships.IShip;
import org.seabattle.ships.ShipDirection;
import org.seabattle.ships.ShipFourDesk;
import org.seabattle.ships.ShipThreeDesk;
import org.seabattle.view.mapper.FieldToStringMapper;

import java.awt.*;
import java.util.ArrayList;

public class SmartRandomHitBotTest {
    Field field;
    SmartRandomHitBot smartRandomHitBot;
    FieldToStringMapper fieldToStringMapper = new FieldToStringMapper();
    @Before
    public void setUp()  {
        field = new Field();
        field.placeShip(new ShipFourDesk(new Point(5,5), ShipDirection.HORIZONTAL));
        DumbShipRandomizer dumbShipRandomizer = new DumbShipRandomizer();
        dumbShipRandomizer.placeAllShips(field);
        smartRandomHitBot = new SmartRandomHitBot(field);
    }


    @Test
    public void testGetHit() throws Exception {
        ArrayList<Point> hits = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Point hit = smartRandomHitBot.getHit();
            if (hits.contains(hit)){
                throw new Exception("Hit %s was already made".formatted(hit));
            }
            hits.add(new Point(hit));
            field.tryHit(hit);
            System.out.println(fieldToStringMapper.map(field));
        }
    }

    @Test
    public void testComplexity(){
        int hitsCount = 0;
        for (int i = 0; i < 100; i++) {
            Point hit = smartRandomHitBot.getHit();
            hitsCount++;
            hitAndPrint(hit);
            if (field.getCellStatus(hit) == org.seabattle.CellStatus.HIT) {
                IShip ship = field.getShip(hit).get();
                int maxComplexity = ship.getParts().size()+4;
                for (int j = 0; j <maxComplexity; j++) {
                    hit = smartRandomHitBot.getHit();
                    hitAndPrint(hit);
                    if (!ship.isAlive()){
                        System.out.printf("Ship was destroy, Complexity: %d/%d \n\n", j, maxComplexity);
                        break;
                    }
                }

                if (ship.isAlive()){
                    throw new RuntimeException("Ship is alive after %d hits".formatted(maxComplexity));
                }
            }
            System.out.println(fieldToStringMapper.map(field));
            if (field.isAllShipsDestroyed()){
                break;
            }
        }
        System.out.println("Hits count: " + hitsCount);
    }

    private void hitAndPrint(Point hit){
        field.tryHit(hit);
        System.out.println(fieldToStringMapper.map(field));
    }

    @Test
    public void HorizontalHitTest(){
        field = new Field();
        field.placeShip(new ShipThreeDesk(new Point(5,4), ShipDirection.HORIZONTAL));
        smartRandomHitBot = new SmartRandomHitBot(field);
        Point hit = smartRandomHitBot.getHit();
        hitAndPrint(hit);
    }


}