package org.seabattle.FIeld;

import java.awt.*;
import java.util.HashSet;

public class HitsManager {

    private final HashSet<Point> hits = new HashSet<>();

    public void addHit(Point point){
        hits.add(point);
    }

    public boolean isHit(Point point){
        return hits.contains(point);
    }
}
