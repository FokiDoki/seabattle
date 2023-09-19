package org.seabattle.view.battle;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.Getter;
import org.seabattle.FIeld.Field;
import org.seabattle.hitbot.HitBot;
import org.seabattle.hitbot.RandomHitBot;
import org.seabattle.view.AutoControlsManagementController;

import java.awt.*;

@Getter
public class BattleController extends AutoControlsManagementController {


    private Field playerField;
    private Field enemyField;

    private final HitBot hitBot;

    private final BattleView view;

    public BattleController(Terminal terminal, Field playerField, Field enemyField, BattleView view) {
        super(terminal);
        this.playerField = playerField;
        this.view = view;
        this.enemyField = enemyField;
        this.hitBot= new RandomHitBot(enemyField);
    }

    public void init() {
        configureControls();
    }

    public void configureControls(){
        Point enemyFieldLocation = view.getEnemyFieldLocation();
        cursorField.addAvailableZone(new TerminalPosition(enemyFieldLocation.x+5, enemyFieldLocation.y+2),
                new TerminalPosition(enemyFieldLocation.x + enemyField.getWidth() * 2+4,
                        enemyFieldLocation.y + enemyField.getHeight()+1), "enemyField");
        controlsManager.onKeyPress("Space").addListener(this::hitEnemy);
        cursorField.addArrowListeners(controlsManager);
        controlsManager.applyAll();
        cursorField.reset();
    }

    public void hitEnemy(){
        Point point = cursorField.getPixelCursorPosition("enemyField");
        enemyField.tryHit(point);
        view.printEnemyField();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            logger.error(e);
            System.exit(1);
        }
        hitPlayer(hitBot.getHit());
    }

    public void hitPlayer(Point point){
        playerField.tryHit(point);
        view.printPlayerField();
    }




}
