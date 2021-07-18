package com.pi4j.fxgl.util;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;

/**
 * @author Dieter Holz
 */
class HardwareButtonImpl implements HardwareButton {

    /**
     * Pi4J digital input instance used by this component
     */
    private DigitalInput digitalInput;

    /**
     * GPIO pin the button is attached
     */
    private final int bcmPin;

    /**
     * button's name
     */
    private final String label;

    // needed for whileDown handling
    private final ScheduledExecutorService scheduler;
    private       ScheduledFuture<?>       scheduledFuture;

    HardwareButtonImpl(int bcmPin, String label) {
        this.bcmPin = bcmPin;
        this.label = label;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void initialize() {
        this.digitalInput = Pi4JContext.createDigitalInput(bcmPin, label);
    }

    /**
     * Handler for simple event while button is pressed
     */
    private final List<Runnable> whileDownListener = new ArrayList<>();

    /**
     * Adds the action to a list of things that are done whenever the button is pressed
     *
     * The action gets triggered whenever the button is pressed.
     *
     * @param action whatever needs to be done
     */
    @Override
    public void addOnPressed(Runnable action) {
        digitalInput.addListener(event -> {
            if (isPressed()) {
                action.run();
            }
        });
    }

    /**
     * Adds the action to a list of things that are done whenever the button is depressed.
     *
     * The action gets triggered whenever the button is depressed.
     *
     * @param action whatever needs to be done
     */
    @Override
    public void addOnReleased(Runnable action) {
        digitalInput.addListener(event -> {
            if (isDepressed()) {
                action.run();
            }
        });
    }

    /**
     * Adds the action to a list of things that are done while the button and all of the 'alsoPressedButtons' are pressed .
     *
     * The action gets triggered periodically with the given 'pulse'.
     *
     * @param action whatever needs to be done
     */
    @Override
    public void addWhileDown(Runnable action, Duration pulse, HardwareButton... alsoPressedButton) {
        if (whileDownListener.isEmpty()) {
            digitalInput.addListener(event -> {
                if (isPressed() && Arrays.stream(alsoPressedButton).noneMatch(HardwareButton::isDepressed)) {
                    startWhileDown(pulse);
                } else {
                    stopWhileDown();
                }
            });
        }
        whileDownListener.add(action);
    }

    /**
     * Checks if button is currently pressed
     *
     * @return True if button is pressed
     */
    @Override
    public boolean isPressed() {
        return digitalInput.state() == DigitalState.LOW;
    }

    /**
     * Checks if button is currently depressed (= NOT pressed)
     *
     * @return True if button is depressed
     */
    @Override
    public boolean isDepressed() {
        return !isPressed();
    }

    /**
     *
     * @return the button's name
     */
    @Override
    public String getLabel() {
        return label;
    }

    private void startWhileDown(Duration pulse) {
        if (!whileDownListener.isEmpty()) {
            stopWhileDown();
            scheduledFuture = scheduler.scheduleAtFixedRate(
                    () -> whileDownListener.forEach(Runnable::run),
                    0,
                    pulse.toMillis(),
                    TimeUnit.MILLISECONDS);
        }
    }

    private void stopWhileDown() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
        }
    }

    @Override
    public HardwareButton getButtonDelegate() {
        throw new IllegalCallerException("should never be called");
    }

}
