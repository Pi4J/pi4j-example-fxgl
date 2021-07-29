package com.pi4j.fxgl.jfxplain.tools;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javafx.util.Duration;
public class MediaTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        Media media = new Media(getClass().getResource("/Spock_Fascinating.mp4").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Scene scene = new Scene(new StackPane(mediaView), 800, 480);
        primaryStage.setScene(scene);
         primaryStage.show();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(f -> mediaPlayer.play());
        pauseTransition.play();
    }
}