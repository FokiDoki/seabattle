package org.seabattle.view.input;

import org.jnativehook.keyboard.NativeKeyEvent;

public class KeyReleaseListener extends AnyKeyReleaseListener{

    String triggerKey;

    public KeyReleaseListener(String triggerKey){
        this.triggerKey = triggerKey;
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (triggerKey.equals(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()))){
            triggerAll();
        }
    }
}
