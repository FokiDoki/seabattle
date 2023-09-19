package org.seabattle.view.input;

import org.jnativehook.keyboard.NativeKeyListener;

public interface KeyListener extends NativeKeyListener {

    void addListener(Runnable runnable);

}
