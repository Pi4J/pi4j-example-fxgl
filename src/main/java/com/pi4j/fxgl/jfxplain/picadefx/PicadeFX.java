package com.pi4j.fxgl.jfxplain.picadefx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.pi4j.fxgl.jfxplain.picadefx.gui.GUI;
import com.pi4j.fxgl.jfxplain.picadefx.model.AppModel;
import com.pi4j.fxgl.jfxplain.picadefx.pui.PicadeUI;
import com.pi4j.fxgl.util.Resolution;

/**
 * @author Dieter Holz
 */
public class PicadeFX extends Application {

    @Override
    public void start(Stage stage)  {
        // model contains all the information that needs to be visualized and updated
        // therefore it must be instantiated first
        AppModel model = new AppModel();

        // the GUI needs to know the information it has to visualize
        Parent gui = new GUI(model);

        Scene scene = new Scene(gui);

        stage.setScene(scene);
        stage.setTitle("PicadeFX");
        stage.setWidth(Resolution.PICADE.getWidth());
        stage.setHeight(Resolution.PICADE.getHeight());

        stage.show();

        new PicadeUI(model);
    }

    public static void main(String[] args) {
   		launch(args);
   	}
}
