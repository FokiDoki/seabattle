package org.seabattle.FIeld;

import org.seabattle.CellStatus;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class HitsManager {

    HashMap<CellStatus, Integer> hitsCount = new HashMap<>();
    private final HashSet<Point> hits = new HashSet<>();

    public void addHit(Point point, CellStatus status){
        hits.add(point);
        hitsCount.putIfAbsent(status, 0);
        hitsCount.put(status, hitsCount.get(status)+1);
    }

    public int getHitsCount(CellStatus status){
        hitsCount.putIfAbsent(status, 0);
        return hitsCount.get(status);
    }

    public boolean isHit(Point point){
        return hits.contains(point);
    }
}
