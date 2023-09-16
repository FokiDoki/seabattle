package org.seabattle.view;

public interface IView {
    void init();

    void redirect(IView view);

    default void destroy(){
    };
}
