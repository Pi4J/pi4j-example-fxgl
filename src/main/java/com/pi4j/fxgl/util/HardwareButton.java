package com.pi4j.fxgl.util;

import java.time.Duration;

import javafx.scene.input.KeyCode;

/**
 * @author Dieter Holz
 */
public interface HardwareButton {
    /**
     * Default time used in whileDown events in millseconds to trigger the next event
     */
    Duration DEFAULT_REFRESH_RATE = Duration.ofMillis(80);

    HardwareButton getButtonDelegate();

    /**
     * do all the necessary initialization
     * mainly: create the Pi4J-DigitalInput to get access to the physical button
     */
    default void initialize() {
        getButtonDelegate().initialize();
    }


    default void addOnPressed(Runnable action) {
        getButtonDelegate().addOnPressed(action);
    }

    default void addOnReleased(Runnable action) {
        getButtonDelegate().addOnReleased(action);
    }

    default void addWhileDown(Runnable action, HardwareButton... alsoPressedButton) {
        addWhileDown(action, DEFAULT_REFRESH_RATE, alsoPressedButton);
    }

    default void addWhileDown(Runnable action, Duration pulse, HardwareButton... alsoPressedButton) {
        getButtonDelegate().addWhileDown(action, pulse, alsoPressedButton);
    }

    default boolean isPressed() {
        return getButtonDelegate().isPressed();
    }

    default boolean isDepressed() {
        return getButtonDelegate().isDepressed();
    }

    default String getLabel() {
        return getButtonDelegate().getLabel();
    }


}
