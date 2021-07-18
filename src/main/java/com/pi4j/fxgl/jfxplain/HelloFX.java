package com.pi4j.fxgl.jfxplain;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Initially copied from https://github.com/openjfx/samples/blob/master/CommandLine/Modular/CLI/hellofx/src/hellofx/HelloFX.java
 *
 * @author Dieter Holz
 */
public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion   = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        Label lbl = new Label("JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        Button btn = new Button("Say Hello");
        btn.setOnAction(event -> lbl.setText("Hello"));

        ImageView imgView = new ImageView(new Image(HelloFX.class.getResourceAsStream("/jfx/openduke.png")));
        imgView.setFitHeight(200);
        imgView.setPreserveRatio(true);

        VBox rootPane = new VBox(50, imgView, lbl, btn);
        rootPane.setAlignment(Pos.CENTER);

        rootPane.getStylesheets().add(HelloFX.class.getResource("/jfx/styles.css").toExternalForm());

        Scene scene = new Scene(rootPane, 640, 480);
        stage.setTitle("Plain JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}