package org.seabattle.FIeld;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class HitsManager {

    private final HashMap<? extends Player, HashSet<Point>> hits = new HashMap<>();

    public void addHit(Point point, Player player){
        hits.get(player).add(point);
    }

    public boolean isHit(Point point){
        return hits.values().stream().anyMatch(points -> points.contains(point));
    }
}
