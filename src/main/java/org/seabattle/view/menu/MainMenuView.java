package org.seabattle.view.menu;

import com.googlecode.lanterna.TextColor;
import lombok.SneakyThrows;
import org.seabattle.view.IController;
import org.seabattle.view.IView;
import org.seabattle.view.ViewLanterna;

public class MainMenuView extends ViewLanterna {

    private static final String seaBattleLogo = """
            ░██████╗███████╗░█████╗░░░░░░░██████╗░░█████╗░████████╗████████╗██╗░░░░░███████╗
            ██╔════╝██╔════╝██╔══██╗░░░░░░██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║░░░░░██╔════╝
            ╚█████╗░█████╗░░███████║█████╗██████╦╝███████║░░░██║░░░░░░██║░░░██║░░░░░█████╗░░
            ░╚═══██╗██╔══╝░░██╔══██║╚════╝██╔══██╗██╔══██║░░░██║░░░░░░██║░░░██║░░░░░██╔══╝░░
            ██████╔╝███████╗██║░░██║░░░░░░██████╦╝██║░░██║░░░██║░░░░░░██║░░░███████╗███████╗
            ╚═════╝░╚══════╝╚═╝░░╚═╝░░░░░░╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝""";

    private static final String pressAnyKey = """
            𝗣𝗥𝗘𝗦𝗦 𝗔𝗡𝗬 𝗞𝗘𝗬 𝗧𝗢 𝗦𝗧𝗔𝗥𝗧
            """;

    private static final String ship = """   
                      |\\___..--"/                  \\"--..___//|
               __..--``         /                    \\          ``--..__
              \\_______________/~~~~~~~~~~~~~~~~~~~~~~\\_______________/""";

    IController controller;

    public MainMenuView(IView redirectView) {
        controller = new MainMenuController(terminal, this, redirectView);
    }

    @SneakyThrows
    @Override
    public void init() {
        printStaticContent();
    }

    @SneakyThrows
    private void printStaticContent(){
        skipLines(2);
        printStrings(seaBattleLogo.split("\n"));
        terminal.setForegroundColor(TextColor.ANSI.CYAN);
        skipLines(4);
        printStrings(pressAnyKey.split("\n"));
        skipLines(4);
        terminal.setCursorPosition(0, 19);
        printStrings(ship.split("\n"));
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    @Override
    public void destroy() {
        controller.destroy();
        super.destroy();
    }

}

