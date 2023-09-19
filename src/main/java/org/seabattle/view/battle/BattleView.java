package org.seabattle.view.battle;

import com.googlecode.lanterna.TerminalPosition;
import lombok.Getter;
import org.seabattle.FIeld.Field;
import org.seabattle.view.ViewLanterna;
import org.seabattle.view.mapper.FieldToStringMapper;

import java.awt.*;

public class BattleView extends ViewLanterna {


    private final String youHeader = """
            ░█──░█ ░█▀▀▀█ ░█─░█
            ░█▄▄▄█ ░█──░█ ░█─░█
            ──░█── ░█▄▄▄█ ─▀▄▄▀
            """;
    private final String enemyHeader = """
            ░█▀▀█ ░█▀▀▀█ ░█▀▀█
            ░█▀▀▄ ░█──░█ ░█▀▀▄
            ░█▄▄█ ░█▄▄▄█ ░█▄▄█
            """;

    private final String borderSymbol = "█";

    @Getter
    private final Point enemyFieldLocation = new Point(40, 7);

    private final String controls = """
            Controls: Use arrow keys to move the cursor; [SPACE] - attack
            """;

    BattleController controller;
    public BattleView(Field playerField, Field enemyField) {
        controller = new BattleController(terminal, playerField, enemyField, this);
    }
    @Override
    public void init() {
        controller.init();
        printBorder();
        printPlayerField();
        printEnemyField();
        printPlayerSubTitle();
        printEnemySubTitle();
    }

    public void printPlayerSubTitle(){
            drawFrame(new TerminalPosition(11, 20), () -> {
            printStrings(youHeader);
        });
    }

    public void printEnemySubTitle(){
        drawFrame(new TerminalPosition(45, 20), () -> {
            printStrings(enemyHeader);

        });
    }

    public void printBorder(){
        drawFrame(new TerminalPosition(38,5), () -> {
            printStrings((borderSymbol+"\n").repeat(20));
            skipLines(1);
        });
    }

    public void printPlayerField(){
        drawFrame(new TerminalPosition(6,7), () -> {
            printStrings(FieldToStringMapper.map(controller.getPlayerField()));
        });
    }

    public void printEnemyField(){
        drawFrame(new TerminalPosition(enemyFieldLocation.x,enemyFieldLocation.y), () -> {
            printStrings(FieldToStringMapper.map(controller.getEnemyField()));
        });
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
