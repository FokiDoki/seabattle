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

    private final String stats = """
            Ships: %s   \t
            Hits: %s   
            Misses: %s   
            """;

    private final String winMessage = """
            ██╗░░░██╗░█████╗░██╗░░░██╗  ░██╗░░░░░░░██╗██╗███╗░░██╗██╗
            ╚██╗░██╔╝██╔══██╗██║░░░██║  ░██║░░██╗░░██║██║████╗░██║██║
            ░╚████╔╝░██║░░██║██║░░░██║  ░╚██╗████╗██╔╝██║██╔██╗██║██║
            ░░╚██╔╝░░██║░░██║██║░░░██║  ░░████╔═████║░██║██║╚████║╚═╝
            ░░░██║░░░╚█████╔╝╚██████╔╝  ░░╚██╔╝░╚██╔╝░██║██║░╚███║██╗
            ░░░╚═╝░░░░╚════╝░░╚═════╝░  ░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚══╝╚═╝
            """;

    private final String loseMessage = """
██╗░░░██╗░█████╗░██╗░░░██╗  ██╗░░░░░░█████╗░░██████╗███████╗  ██╗░░██╗
╚██╗░██╔╝██╔══██╗██║░░░██║  ██║░░░░░██╔══██╗██╔════╝██╔════╝  ╚═╝░██╔╝
░╚████╔╝░██║░░██║██║░░░██║  ██║░░░░░██║░░██║╚█████╗░█████╗░░  ░░░██╔╝░
░░╚██╔╝░░██║░░██║██║░░░██║  ██║░░░░░██║░░██║░╚═══██╗██╔══╝░░  ░░░╚██╗░
░░░██║░░░╚█████╔╝╚██████╔╝  ███████╗╚█████╔╝██████╔╝███████╗  ██╗░╚██╗
░░░╚═╝░░░░╚════╝░░╚═════╝░  ╚══════╝░╚════╝░╚═════╝░╚══════╝  ╚═╝░░╚═╝
            """;



    private final String borderSymbol = "█";

    FieldToStringMapper fieldToStringMapper = new FieldToStringMapper();


    @Getter
    private final Point enemyFieldLocation = new Point(40, 7);

    private final String controls = """
            Controls: 
            Use arrow keys to
             move the cursor; 
             [SPACE] - attack
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
        printControls();
        printPlayerStats(controller.getPlayerField().getShips().size(), 0, 0);
        printEnemyStats(controller.getPlayerField().getShips().size(), 0, 0);
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
        });
    }

    public void printPlayerField(){
        drawFrame(new TerminalPosition(6,7), () -> {
            printStrings(fieldToStringMapper.map(controller.getPlayerField()));
        });
    }

    public void printPlayerStats(int ships, int hits, int misses){
        drawFrame(new TerminalPosition(1, 1), () -> {
            printStrings(stats.formatted(ships, hits, misses));
        });
    }

    public void printEnemyStats(int ships, int hits, int misses){
        drawFrame(new TerminalPosition(42, 1), () -> {
            printStrings(stats.formatted(ships, hits, misses));
        });
    }

    public void printWinMessage(){
        drawFrame(new TerminalPosition(12,9), () -> {
            printStrings(winMessage);
        });
    }

    public void printEnemyField(){
        drawFrame(new TerminalPosition(enemyFieldLocation.x,enemyFieldLocation.y), () -> {
            printStrings(fieldToStringMapper.map(controller.getEnemyField()));
        });
    }

    public void printLoseMessage(){
        drawFrame(new TerminalPosition(5,9), () -> {
            printStrings(loseMessage);
        });
    }

    public void printControls(){
        drawFrame(new TerminalPosition(60, 1), () -> {
            printStrings(controls);
        });
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
