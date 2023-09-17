package org.seabattle.view.menu;

import com.googlecode.lanterna.TextColor;
import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;
import org.seabattle.view.IView;
import org.seabattle.view.ViewLanterna;
import org.seabattle.view.input.AnyKeyReleaseListener;
import org.seabattle.view.input.KeyListener;

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


    KeyListener keyPressListener = new AnyKeyReleaseListener();
    IView redirectView;

    public MainMenuView(IView redirectView) {
        this.redirectView = redirectView;
    }

    @SneakyThrows
    @Override
    public void init() {
        printStaticContent();
        GlobalScreen.addNativeKeyListener(keyPressListener);
        keyPressListener.addListener(() -> {
            redirect(redirectView);
        });
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

    @SneakyThrows
    @Override
    public void destroy() {
        GlobalScreen.removeNativeKeyListener(keyPressListener);
        clear();
    }

}

