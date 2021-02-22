package com.pi4j.example.util;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;

public class Pi4JHelper {

    private Pi4JHelper() {
        // NOP Hide constructor
    }

    public static DigitalInput getInput(Context pi4j, String id, int bcm) throws Exception {
        return pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id(id)
                .address(bcm)
                .pull(PullResistance.PULL_DOWN)
                .debounce(3000L)
                .provider("pigpio-digital-input"));

    }
}
