package org.seabattle.view.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;
import java.util.List;


public class AnyKeyReleaseListener implements KeyListener {

    Logger logger = LogManager.getLogger(AnyKeyReleaseListener.class);
    List<Runnable> listeners = new ArrayList<>();


    public AnyKeyReleaseListener() {
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

    }

    protected void triggerAll(){
        logger.debug("Triggering {} triggers", listeners.size());
        listeners.forEach(Runnable::run);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        triggerAll();
    }

    @Override
    public void addListener(Runnable runnable) {
        listeners.add(runnable);
    }
}
