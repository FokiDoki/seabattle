package org.seabattle.hitbot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.seabattle.CellStatus;
import org.seabattle.FIeld.Field;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class SmartRandomHitBot extends RandomHitBot {

    Logger logger = LogManager.getLogger(SmartRandomHitBot.class);
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @Getter
    @RequiredArgsConstructor
    private static class PrioritizedDirection {
        private final Direction direction;

        int priority = 0;

        public int addPriority() {
            this.priority++;
            return this.priority;
        }
    }

    private static class CurrentTarget {
        Point startPoint;
        Direction direction;
        Point currentPoint;

        int destroyedParts = 0;
        boolean isReversed = false;
    }




    private final PriorityQueue<PrioritizedDirection> sidesPriorityDefault =
            new PriorityQueue<>(Comparator.comparing(PrioritizedDirection::getPriority).reversed())
            {{
                    add(new PrioritizedDirection(Direction.UP));
                    add(new PrioritizedDirection(Direction.DOWN));
                    add(new PrioritizedDirection(Direction.LEFT));
                    add(new PrioritizedDirection(Direction.RIGHT));
            }};

    private Queue<PrioritizedDirection> sidesPriority;
    private CurrentTarget currentTarget;

    private final ArrayList<Point> hits = new ArrayList<>();

    Field field;
    public SmartRandomHitBot(Field field) {
        super(field);
        this.field = field;
    }
    @Override
    public Point getHit() {
        Point hit;
        if (isSidesQueueNeedCheck()){
            hit = searchForCorrectSide();
            logger.debug("Side hit %s".formatted(hit));
        } else if (currentTarget != null){
            hit = searchForShipByLine();
            logger.debug("Line hit %s".formatted(hit));
        } else {
            hit = getRandomHit();
            logger.debug("Random hit %s".formatted(hit));
            if (field.getCellStatus(hit) == CellStatus.SHIP) {
                currentTarget = new CurrentTarget();
                currentTarget.startPoint = hit;
                currentTarget.destroyedParts++;
                sidesPriority = getSidesQueue();
            }
        }
        Point hitCopy = new Point(hit);
        hits.add(hitCopy);
        return hitCopy;
    }

    private Point searchForShipByLine() {
        Point hit = currentTarget.currentPoint;
        moveByDirection(currentTarget.direction, hit);
        if (field.getCellStatus(hit) != CellStatus.SHIP) {
            if (currentTarget.isReversed) {
                currentTarget = null;
            } else {
                currentTarget.isReversed = true;
                currentTarget.currentPoint = currentTarget.startPoint;
                currentTarget.direction = getReverseDirection(currentTarget.direction);
            }
        } else {
            currentTarget.destroyedParts++;
        }
        if (currentTarget!=null && currentTarget.destroyedParts >= getMaxSizeOfAliveShip()) {
            currentTarget = null;
        }
        if (!isStrikeFair(hit)){
            return getHit();
        }
        return hit;
    }

    private Direction getReverseDirection(Direction direction) {
        switch (direction){
            case UP -> {return Direction.DOWN;}
            case DOWN -> {return Direction.UP;}
            case LEFT -> {return Direction.RIGHT;}
            case RIGHT -> {return Direction.LEFT;}
            default -> {return null;}
        }
    }

    private Point getRandomHit(){

        Point hit = super.getHit();
        System.out.println("Random hit %s".formatted(hit));
        if (hits.contains(hit)){
            return getRandomHit();
        }
        return hit;
    }

    private boolean isSidesQueueNeedCheck() {
        return sidesPriority != null && !sidesPriority.isEmpty();
    }

    private Point searchForCorrectSide() {

        PrioritizedDirection direction = sidesPriority.poll();
        if (direction==null){
            currentTarget = null;
            return getHit();
        }
        Point hit = new Point(currentTarget.startPoint);
        moveByDirection(direction.getDirection(), hit);
        if (!isStrikeFair(hit)) {
            return searchForCorrectSide();
        }
        if (field.getCellStatus(hit) == CellStatus.SHIP) {
            currentTarget.currentPoint = hit;
            currentTarget.destroyedParts++;
            currentTarget.direction = direction.getDirection();
            direction.addPriority();
            sidesPriority = null;
        }
        if (sidesPriority!=null && sidesPriority.isEmpty()){
            currentTarget = null;
        }

        return hit;
    }

    private boolean isStrikeFair(Point hit) {
        return field.getCellStatus(hit) != CellStatus.MISS &&
                field.getCellStatus(hit) != CellStatus.HIT &&
                isStrikeInField(hit);
    }

    private int getMaxSizeOfAliveShip(){
        return field.getShips().stream().mapToInt(ship -> Math.max(ship.getSizeX(), ship.getSizeY())).max().orElse(0);
    }

    private boolean isStrikeInField(Point hit) {
        return hit.x >= 0 && hit.y >= 0 && hit.x < field.getWidth() && hit.y < field.getHeight();
    }

    private static void moveByDirection(Direction direction, Point hit) {
        switch (direction) {
            case UP -> hit.y++;
            case DOWN -> hit.y--;
            case LEFT -> hit.x--;
            case RIGHT -> hit.x++;
        }
    }

    private Queue<PrioritizedDirection> getSidesQueue(){
        return new PriorityQueue<>(sidesPriorityDefault);
    }
}
