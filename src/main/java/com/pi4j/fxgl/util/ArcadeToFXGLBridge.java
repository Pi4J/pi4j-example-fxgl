package com.pi4j.fxgl.util;

import java.util.Map;

import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getExecutor;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

/**
 * @author Dieter Holz
 */
public class ArcadeToFXGLBridge {

    /**
     *
     * @param button
     * @param keyCode
     */
    public static void mapButtonToKeyCode(HardwareButton button, KeyCode keyCode){
        button.addOnPressed(()  -> getExecutor().startAsyncFX(() -> getInput().mockKeyPress(keyCode)));
        button.addOnReleased(() -> getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(keyCode)));
    }

    public static void bridge(Map<PicadeButton, KeyCode> allMappings) {
        allMappings.forEach(ArcadeToFXGLBridge::mapButtonToKeyCode);
    }

    public static void mapButtonToAction(PicadeButton button, Runnable action) {
        button.addOnPressed(() -> getExecutor().startAsyncFX(action));
    }
}
