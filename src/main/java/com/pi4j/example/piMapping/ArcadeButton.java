package com.pi4j.example.piMapping;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;

public interface ArcadeButton {
    //public void init();

    public String getStatus();

    public boolean isPressed();

}
