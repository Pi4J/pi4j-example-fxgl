package com.pi4j.example.util;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getExecutor;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Pi4JFactory {

    // BCM numbers of the connected components
    // Full list can be found on https://pinout.xyz/pinout/picade_hat
    private static final int PIN_JOYSTICK_UP = 12;
    private static final int PIN_JOYSTICK_DOWN = 6;
    private static final int PIN_JOYSTICK_LEFT = 20;
    private static final int PIN_JOYSTICK_RIGHT = 16;
    private static final int PIN_BUTTON_1 = 5;

    private final Console console;
    private Context pi4j;

    public Pi4JFactory() {
        console = new Console();

        try {
            pi4j = Pi4J.newAutoContext();
            initInputGpios();
        } catch (Exception ex) {
            console.println("Error while initializing Pi4J: {}", ex.getMessage());
        }
    }

    public Console getConsole() {
        return console;
    }

    /**
     * Initialize Pi4J and the connected components
     */
    private void initInputGpios() {
        try {
            // Print program title/header
            console.title("<-- The Pi4J Project -->", "FXGL Example project");

            /*
            TODO
            Current listeners call the move function once.
            To be changed to continuously move the player as long as the joystick is pushed.
            */

            var joystickUp = initInputGpio(pi4j, "JoystickUp", PIN_JOYSTICK_UP);
            joystickUp.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    console.println("Joystick UP");
                    getExecutor().startAsyncFX(() -> getInput().mockKeyPress(KeyCode.UP));
                } else {
                    getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(KeyCode.UP));
                }
            });
            var joystickDown = initInputGpio(pi4j, "JoystickDown", PIN_JOYSTICK_DOWN);
            joystickDown.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    console.println("Joystick DOWN");
                    getExecutor().startAsyncFX(() -> getInput().mockKeyPress(KeyCode.DOWN));
                } else {
                    getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(KeyCode.DOWN));
                }
            });
            var joystickLeft = initInputGpio(pi4j, "JoystickLeft", PIN_JOYSTICK_LEFT);
            joystickLeft.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    console.println("Joystick LEFT");
                    getExecutor().startAsyncFX(() -> getInput().mockKeyPress(KeyCode.LEFT));
                } else {
                    getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(KeyCode.LEFT));
                }
            });
            var joystickRight = initInputGpio(pi4j, "JoystickRight", PIN_JOYSTICK_RIGHT);
            joystickRight.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    console.println("Joystick RIGHT");
                    getExecutor().startAsyncFX(() -> getInput().mockKeyPress(KeyCode.RIGHT));
                } else {
                    getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(KeyCode.RIGHT));
                }
            });
            var buttonFire = initInputGpio(pi4j, "ButtonFood", PIN_BUTTON_1);
            buttonFire.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    console.println("Button FOOD");
                    getExecutor().startAsyncFX(() -> getInput().mockKeyPress(KeyCode.F));
                } else {
                    getExecutor().startAsyncFX(() -> getInput().mockKeyRelease(KeyCode.F));
                }
            });
        } catch (Exception ex) {
            console.println("Error while initializing Pi4J: " + ex.getMessage());
        }
    }

    private DigitalInput initInputGpio(Context pi4j, String id, int bcm) throws Exception {
        return pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id(id)
                .address(bcm)
                .pull(PullResistance.PULL_UP)
                .debounce(3000L)
                .provider("pigpio-digital-input"));

    }
}
