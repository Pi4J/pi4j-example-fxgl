package com.pi4j.example.piMapping;

import javafx.geometry.Point2D;

public interface ArcadeJoystick {

    public void init();

    public String getStatus();

    public Point2D getCoordinates();

}
