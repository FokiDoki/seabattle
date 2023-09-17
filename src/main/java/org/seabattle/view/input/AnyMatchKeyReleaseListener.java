package org.seabattle.view.input;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.Collection;

public class AnyMatchKeyReleaseListener extends AnyKeyReleaseListener{

    Collection<String> triggerKeys;

    public AnyMatchKeyReleaseListener(Collection<String> triggerKeys){
        this.triggerKeys = triggerKeys;
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (triggerKeys.contains(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()))){
            triggerAll();
        }
    }
}
