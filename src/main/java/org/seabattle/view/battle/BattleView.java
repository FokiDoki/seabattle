package org.seabattle.view.battle;

import com.googlecode.lanterna.TerminalPosition;
import org.seabattle.FIeld.Field;
import org.seabattle.view.ViewLanterna;
import org.seabattle.view.mapper.FieldToStringMapper;

public class BattleView extends ViewLanterna {


    private final String youHeader = """
            ░█──░█ ░█▀▀▀█ ░█─░█
            ░█▄▄▄█ ░█──░█ ░█─░█
            ──░█── ░█▄▄▄█ ─▀▄▄▀
            """;
    private final String enemyHeader = """
            ▒█▀▀▀ ▒█▄░▒█ ▒█▀▀▀ ▒█▀▄▀█ ▒█░░▒█
            ▒█▀▀▀ ▒█▒█▒█ ▒█▀▀▀ ▒█▒█▒█ ▒█▄▄▄█
            ▒█▄▄▄ ▒█░░▀█ ▒█▄▄▄ ▒█░░▒█ ░░▒█░░
            """;

    private final String borderSymbol = "█";

    private final String controls = """
            Controls: Use arrow keys to move the cursor; [SPACE] - attack
            """;

    BattleController controller;
    public BattleView(Field playerField, Field enemyField) {
        controller = new BattleController(terminal, playerField, enemyField);
    }
    @Override
    public void init() {
        printBorder();
        printPlayerField();
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


    @Override
    public void destroy() {
        super.destroy();
    }
}
