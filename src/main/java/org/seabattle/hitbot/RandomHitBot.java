package org.seabattle.hitbot;

import org.seabattle.FIeld.Field;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class RandomHitBot implements HitBot{

    Iterator<Point> hits;




    public RandomHitBot(Field field) {
        ArrayList<Point> hits = new ArrayList<>();
        for (int i = 0; i < field.getHeight()*field.getWidth(); i++) {
            hits.add(new Point(i%field.getWidth(), i/field.getWidth()));
        }
        Random random = new Random(System.currentTimeMillis());
        Collections.shuffle(hits, random);
        this.hits = hits.iterator();
    }


    @Override
    public Point getHit() {
        return hits.next();
    }
}
