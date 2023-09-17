package org.seabattle.view;

import com.googlecode.lanterna.TerminalPosition;

public interface IView {
    void init();

    void redirect(IView view);

    void drawFrame(TerminalPosition position, Runnable drawer);

    default void destroy(){
    };
}
