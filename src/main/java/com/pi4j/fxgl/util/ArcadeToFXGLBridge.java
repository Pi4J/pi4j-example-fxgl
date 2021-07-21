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
        button.addOnReleased(() -> getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(keyCode)));
        button.addOnPressed(() -> getExecutor().startAsyncFX(() -> getInput().mockKeyPress(keyCode)));
        button.addWhileDown(() -> getExecutor().startAsyncFX(() -> getInput().mockKeyPress(keyCode)));
    }

    public static void bridge(Map<PicadeButton, KeyCode> allMappings) {
        allMappings.forEach(ArcadeToFXGLBridge::mapButtonToKeyCode);
    }
}
