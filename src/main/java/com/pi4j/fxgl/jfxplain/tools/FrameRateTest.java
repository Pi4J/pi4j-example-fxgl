package com.pi4j.fxgl.jfxplain.tools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * Just a little tool to measure the frame rate of JavaFX (usually should be 60)
 *
 * Use it if your game lags to assure that it's not a performance problem of JavaFX itself.
 *
 * It's not meant to be any kind of template to start your development.
 *
 * @author Dieter Holz
 */
public class FrameRateTest extends Application {

    @Override
    public void start(Stage stage) {
        ImageView imgView = new ImageView(new Image(FrameRateTest.class.getResourceAsStream("/jfx/openduke.png")));
        imgView.setFitHeight(100);
        imgView.setPreserveRatio(true);

        Parent rootPane = new StackPane(imgView);

        rootPane.getStylesheets().add(FrameRateTest.class.getResource("/jfx/styles.css").toExternalForm());

        Scene scene = new Scene(rootPane, 640, 480);
        stage.setTitle("Plain JavaFX App");
        stage.setScene(scene);
        stage.show();

        final int[] counter = {0};
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                imgView.setTranslateX(imgView.getTranslateX() > 200 ? -200 : imgView.getTranslateX() + 2);
                counter[0]++;
            }
        };
        timer.start();

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            System.out.println("Framerate: " + counter[0]);
            counter[0] = 0;
        }, 0, 1, TimeUnit.SECONDS);

    }

    public static void main(String[] args) {
        launch();
    }

}