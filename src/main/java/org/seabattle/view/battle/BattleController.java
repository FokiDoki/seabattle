package org.seabattle.view.battle;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.Getter;
import org.seabattle.CellStatus;
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
        if (enemyField.getCellStatus(point) == CellStatus.MISS || enemyField.getCellStatus(point) == CellStatus.HIT) {
            return;
        }
        boolean isHitSuccess = enemyField.tryHit(point);
        view.printEnemyField();
        if (!isHitSuccess) {
            hitPlayer();
        }
        checkWinner();
        updateStats();
    }

    private void onWin() {
        view.printWinMessage();
        controlsManager.removeAll();
    }

    private void onLose() {
        controlsManager.removeAll();
        view.printLoseMessage();
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error(e);
            System.exit(1);
        }
    }

    private void updateStats(){
        view.printPlayerStats(playerField.getAliveShipsCount(),
                playerField.getHitsManager().getHitsCount(CellStatus.HIT), playerField.getHitsManager().getHitsCount(CellStatus.MISS));
        view.printEnemyStats(enemyField.getAliveShipsCount(),
                enemyField.getHitsManager().getHitsCount(CellStatus.HIT), enemyField.getHitsManager().getHitsCount(CellStatus.MISS));
    }




    public void hitPlayer(){
        boolean isHitSuccess;
        do {
            isHitSuccess = playerField.tryHit(hitBot.getHit());
            sleep(1);
            view.printPlayerField();
            checkWinner();
            updateStats();
        } while (isHitSuccess);
    }

    private void checkWinner(){
        if (playerField.isAllShipsDestroyed()){
            onLose();
        } else if (enemyField.isAllShipsDestroyed()){
            onWin();
        }
    }

}
