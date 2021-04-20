package com.pi4j.example.piMapping;

public enum Pin {
    PIN_JOYSTICK_UP(12),
    PIN_JOYSTICK_DOWN(6),
    PIN_JOYSTICK_LEFT(20),
    PIN_JOYSTICK_RIGHT(16),
    PIN_BUTTON_1(5);

    private int bcm;

    private Pin(int bcm) {
        this.bcm = bcm;
    }

    public int getBcm() {
        return bcm;
    }
}
