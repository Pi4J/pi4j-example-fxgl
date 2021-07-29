package com.pi4j.fxgl.jfxplain.arcadefx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.fxgl.jfxplain.arcadefx.gui.GUI;
import com.pi4j.fxgl.jfxplain.arcadefx.model.AppModel;
import com.pi4j.fxgl.jfxplain.arcadefx.pui.PicadeUI;
import com.pi4j.fxgl.util.ArcadeConsoles;

/**
 * @author Dieter Holz
 */
public class PicadeFX extends Application {
    static Logger logger = LoggerFactory.getLogger(PicadeFX.class);

    @Override
    public void start(Stage stage)  {
        // model contains all the information that needs to be visualized and updated
        // therefore it must be instantiated first
        logger.info("Start App");
        AppModel model = new AppModel();

        // the GUI needs to know the information it has to visualize
        Parent gui = new GUI(model);

        Scene scene = new Scene(gui);

        stage.setScene(scene);
        stage.setTitle("PicadeFX");
        stage.setWidth(ArcadeConsoles.PICADE.getWidth());
        stage.setHeight(ArcadeConsoles.PICADE.getHeight());

        stage.show();

        new PicadeUI(model);
    }

    public static void main(String[] args) {
        launch(args);
   	}
}
