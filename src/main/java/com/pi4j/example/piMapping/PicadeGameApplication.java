package com.pi4j.example.piMapping;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getExecutor;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

/**
 * <p>This file maps the inputs of a picade to a certain functionality used to implement games with FXGL</p>
 *
 * @author Cyril Lindenmann
 * @version $Id: $Id
 */

public class PicadeGameApplication extends GameApplication {

    private final Console console;
    private Context pi4j;

    private Map<String, DigitalInput> inputGpios;


    public PicadeGameApplication() {
        console = new Console();
        inputGpios = new HashMap();

        try {
            pi4j = Pi4J.newAutoContext();
            //initInputGpios();
        } catch (Exception ex) {
            console.println("Error while initializing Pi4J: {}", ex.getMessage());
        }
    }

    @Override
    protected void initSettings(GameSettings gameSettings) { }


    /**
     * This method binds the action to the specific keyCode and the input on a raspberryPi
     *
     * @param pin: enum pin where bcm are mapped
     * @param keyCode: keyboard key code
     * @param action
     */
    public final void onKeyDown(Pin pin, KeyCode keyCode, Runnable action) {
        com.almasb.fxgl.dsl.FXGL.onKeyDown(keyCode, action);
        try {
            initInputGpio(pi4j, pin.name(), pin.getBcm(), keyCode);
        } catch (Exception ex) {
            console.println("Error while initializing Pi4J: " + ex.getMessage());
        }
    }

    /**
     * OnKeyDown of FXGL
     *
     * @param keyCode
     * @param action
     */
    public final void onKeyDown(KeyCode keyCode, Runnable action) {
        com.almasb.fxgl.dsl.FXGL.onKeyDown(keyCode, action);
    }

    private void initInputGpio(Context pi4j, String id, int bcm, KeyCode keyCode) throws Exception {
       var input = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id(id)
                .address(bcm)
                .pull(PullResistance.PULL_UP)
                .debounce(3000L)
                .provider("pigpio-digital-input"));
        input.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                console.println("Input change for " + id);
                getExecutor().startAsyncFX(() -> getInput().mockKeyPress(keyCode));
            } else {
                getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(keyCode));
            }
        });
        inputGpios.put(id, input);

    }

    public Map<String, DigitalInput> getInputGpios() {
        return inputGpios;
    }

    public Console getConsole() {
        return console;
    }
}
