package com.pi4j.fxgl.util;

/**
 * @author Dieter Holz
 */
public enum Resolution {
    PICADE(1024, 600),
    GAME_HAT(490, 320);

    private final int width;
    private final int height;

    Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
