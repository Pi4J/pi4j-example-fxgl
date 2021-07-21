package com.pi4j.fxgl.jfxplain.arcadefx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.pi4j.fxgl.jfxplain.arcadefx.gui.GUI;
import com.pi4j.fxgl.jfxplain.arcadefx.model.AppModel;
import com.pi4j.fxgl.jfxplain.arcadefx.pui.PicadeUI;
import com.pi4j.fxgl.util.ArcadeConsoles;

/**
 * Shows how to attach the GameHAT to an existing JavaFX-application.
 *
 * Implements the concept of 'PresentationModel'.
 *
 * The presentation model 'AppModel' is shared by the GUI and by the PUI
 *
 * In other words: the PUI is just another view the the same model
 *
 * @author Dieter Holz
 */
public class GameHAT_FX extends Application {

    @Override
    public void start(Stage stage)  {
        // Implementing the Presentation Model Concetp implies that
        // the model contains all the information that needs to be visualized and updated
        // therefore it must be instantiated first
        AppModel model = new AppModel();

        // the GUI needs to know the information it has to visualize
        Parent gui = new GUI(model);

        Scene scene = new Scene(gui);

        stage.setScene(scene);
        stage.setTitle("GameHAT-FX");
        stage.setWidth(ArcadeConsoles.GAME_HAT.getWidth());
        stage.setHeight(ArcadeConsoles.GAME_HAT.getHeight());

        stage.show();

        // Up to this point we have a simple JavaFX application. Nothing special about it.
        // Now we add a second View, the PUI.

        new PicadeUI(model);
    }

    public static void main(String[] args) {
   		launch(args);
   	}
}
