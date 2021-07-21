package com.pi4j.fxgl.jfxplain.arcadefx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Dieter Holz
 */
public class AppModel {
    private final StringProperty          message      = new SimpleStringProperty("JavaFX " + System.getProperty("javafx.version") + ", running on Java " + System.getProperty("java.version") + ".");
    private final IntegerProperty         counter      = new SimpleIntegerProperty(0);

    private final IntegerProperty dukePositionX = new SimpleIntegerProperty();
    private final IntegerProperty dukePositionY = new SimpleIntegerProperty();

    // all getter and setter methods (nothing special)
    public void increaseCounter() {
        counter.setValue(counter.get() + 1);
    }

    public void decreaseCounter() {
        counter.setValue(counter.get() - 1);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public int getCounter() {
        return counter.get();
    }

    public IntegerProperty counterProperty() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter.set(counter);
    }

    public int getDukePositionX() {
        return dukePositionX.get();
    }

    public IntegerProperty dukePositionXProperty() {
        return dukePositionX;
    }

    public void setDukePositionX(int dukePositionX) {
        this.dukePositionX.set(dukePositionX);
    }

    public int getDukePositionY() {
        return dukePositionY.get();
    }

    public IntegerProperty dukePositionYProperty() {
        return dukePositionY;
    }

    public void setDukePositionY(int dukePositionY) {
        this.dukePositionY.set(dukePositionY);
    }
}


