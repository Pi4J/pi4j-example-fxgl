package com.pi4j.fxgl.jfxplain.arcadefx.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import com.pi4j.fxgl.jfxplain.arcadefx.model.AppModel;

/**
 * ViewMixin is used to enforce the same structure in all our views of all our apps.
 *
 * @author Dieter Holz
 */
public class GUI extends VBox implements ViewMixin {

    private final AppModel model;

    private Label     counterLabel;
    private Label     messageLabel;
    private Label     mousePositionLabel;
    private ImageView duke;
    private Button    increaseButton;
    private Button    decreaseButton;
    private int dukeSize = 200;

    public GUI(AppModel model) {
        this.model = model;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("/jfx/styles.css");
    }

    @Override
    public void initializeControls() {
        counterLabel = new Label();
        messageLabel = new Label();
        mousePositionLabel = new Label();

        duke = new ImageView(new Image(GUI.class.getResourceAsStream("/jfx/openduke.png")));
        duke.setFitHeight(dukeSize);
        duke.setPreserveRatio(true);
        duke.setManaged(false);

        increaseButton = new Button("+");
        decreaseButton = new Button("-");
    }

    @Override
    public void layoutControls() {
        setAlignment(Pos.CENTER);
        setSpacing(30);
        getChildren().addAll(duke, counterLabel, messageLabel, mousePositionLabel, increaseButton, decreaseButton);
    }

    @Override
    public void setupEventHandlers() {
        increaseButton.setOnAction(actionEvent -> model.increaseCounter());
        decreaseButton.setOnAction(actionEvent -> model.decreaseCounter());

        setOnMouseMoved(mouseEvent -> {
            model.setDukePositionX((int) mouseEvent.getX());
            model.setDukePositionY((int) mouseEvent.getY());
        });
    }

    @Override
    public void setupBindings() {
        messageLabel.textProperty().bind(model.messageProperty());
        counterLabel.textProperty().bind(model.counterProperty().asString());
        mousePositionLabel.textProperty().bind(model.dukePositionXProperty().asString().concat(", ").concat(model.dukePositionYProperty()));

        duke.xProperty().bind(model.dukePositionXProperty().subtract(dukeSize * 0.5));
        duke.yProperty().bind(model.dukePositionYProperty().subtract(dukeSize * 0.5));
    }

}
