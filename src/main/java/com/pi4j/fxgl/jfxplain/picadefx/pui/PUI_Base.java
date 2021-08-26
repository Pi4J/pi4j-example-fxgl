package com.pi4j.fxgl.jfxplain.picadefx.pui;

import java.util.function.Consumer;

import javafx.application.Platform;

/**
 * @author Dieter Holz
 */
public abstract class PUI_Base<T> {
    private final T model;

    public PUI_Base(T model) {
        this.model = model;
        initialize(model);
        setupButtonEvents(model);
        setupJoyStickEvents(model);
        setupModelChangeListener(model);
    }

    protected void withModel(Runnable action) {
        Platform.runLater(() -> action.run());
    }


    protected abstract void initialize(T model);

    protected abstract void setupJoyStickEvents(T model);

    protected abstract void setupButtonEvents(T model);

    protected void setupModelChangeListener(T model) {
        // Arcade hats typically don't have any visualization elements like a LED or a LCD display.
        // override this method if you need to update the PUI due to status changes of the model
    }

}

