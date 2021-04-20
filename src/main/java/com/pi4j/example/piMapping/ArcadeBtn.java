package com.pi4j.example.piMapping;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;

public class ArcadeBtn {

    private DigitalInput button;
    private String btnStatus;
    private boolean isPressed;

    public ArcadeBtn(Context pi4j, int pin) throws Exception {

        var buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                .id("button")
                .name("Press button")
                .address(pin)
                .pull(PullResistance.PULL_DOWN)
                .debounce(3000L)
                .provider("pigpio-digital-input");
        button  = pi4j.create(buttonConfig);
        button.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                isPressed = true;
            } else {
                isPressed = false;
            }
        });

    }

    public String getStatus() {
        return btnStatus;
    }

    public boolean isPressed() {
        return isPressed;
    }
}
