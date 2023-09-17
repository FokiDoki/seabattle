package org.seabattle.view.input;

import org.jnativehook.keyboard.NativeKeyEvent;

public class KeyPressListener extends AnyKeyReleaseListener{

    String triggerKey;

    public KeyPressListener(String triggerKey){
        this.triggerKey = triggerKey;
    }
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (triggerKey.equals(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()))){
            triggerAll();
        }
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

}
