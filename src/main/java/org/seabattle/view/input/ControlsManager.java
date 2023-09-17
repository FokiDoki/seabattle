package org.seabattle.view.input;

import org.jnativehook.GlobalScreen;

import java.util.ArrayList;
import java.util.List;

public class ControlsManager {

    List<KeyListener> listeners = new ArrayList<>();

    public KeyListenerNavigable onKeyRelease(String key){
        KeyReleaseListener listener = new KeyReleaseListener(key);
        listeners.add(listener);
        return new KeyListenerNavigable(listener);
    }

    public void applyAll(){
        listeners.forEach(GlobalScreen::addNativeKeyListener);
    }

    public void removeAll(){
        listeners.forEach(GlobalScreen::removeNativeKeyListener);
    }

    public class KeyListenerNavigable{
        KeyListener keyListener;



        protected KeyListenerNavigable(KeyListener keyListener) {
            this.keyListener = keyListener;
        }

        public KeyListenerNavigable addListener(Runnable runnable){
            keyListener.addListener(runnable);
            return this;
        }

        public ControlsManager and(){
            return ControlsManager.this;
        }
    }
}
