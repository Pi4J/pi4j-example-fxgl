package com.pi4j.example;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  FxglExample.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameState;

/**
 * <p>This example fully describes the base usage of Pi4J to create a game</p>
 *
 * @author Frank Delporte (<a href="https://www.webtechie.be">https://www.webtechie.be</a>)
 * @version $Id: $Id
 */
public class FxglExample extends GameApplication {

    // BCM numbers of the connected components
    private static final int PIN_JOYSTICK_UP = 5;
    private static final int PIN_JOYSTICK_DOWN = 6;
    private static final int PIN_JOYSTICK_LEFT = 13;
    private static final int PIN_JOYSTICK_RIGHT = 19;

    private Context pi4j;

    /**
     * Reference to the factory which will defines how all the types must be created.
     */
    private final FxglExampleFactory gameFactory = new FxglExampleFactory();

    /**
     * Player object we are going to use to provide to the factory so it can start a bullet from the player center.
     */
    private Entity player;

    /**
     * Main entry point where the application starts.
     *
     * @param args Start-up arguments
     */
    public static void main(String[] args) {
        // Launch the FXGL game application
        launch(args);
    }

    /**
     * General game settings. For now only the title is set, but a longer list of options is available.
     *
     * @param settings The settings of the game which can be further extended here.
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Viks Shark Game");
        settings.setWidth(300);
        initPi4j();
    }

    /**
     * General game variables. Used to hold the points and lives.
     *
     * @param vars The variables of the game which can be further extended here.
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("lives", 5);
    }

    /**
     * Initialize Pi4J and the connected components
     */
    private void initPi4j() {
        try {
            pi4j = Pi4J.newAutoContext();

            // Create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // Print program title/header
            console.title("<-- The Pi4J Project -->", "FXGL Example project");

            var joystickUpConfig = DigitalInput.newConfigBuilder(pi4j)
                    .id("JoystickUp")
                    .name("Press button")
                    .address(PIN_JOYSTICK_UP)
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(3000L)
                    .provider("pigpio-digital-input");
            var joystickUp = pi4j.create(joystickUpConfig);
            joystickUp.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    System.out.println("Joystick UP");
                    moveUp();
                }
            });

            // TODO all joystick positions + extra buttons + LEDS
        } catch (Exception ex) {
            System.err.println("Error while initializing Pi4J: " + ex.getMessage());
        }
    }

    @Override
    protected void initUI() {
        Text scoreLabel = getUIFactoryService().newText("Score", Color.BLACK, 22);
        Text scoreValue = getUIFactoryService().newText("", Color.BLACK, 22);
        Text livesLabel = getUIFactoryService().newText("Lives", Color.BLACK, 22);
        Text livesValue = getUIFactoryService().newText("", Color.BLACK, 22);

        scoreLabel.setTranslateX(20);
        scoreLabel.setTranslateY(20);

        scoreValue.setTranslateX(90);
        scoreValue.setTranslateY(20);

        livesLabel.setTranslateX(getAppWidth() - 100);
        livesLabel.setTranslateY(20);

        livesValue.setTranslateX(getAppWidth() - 30);
        livesValue.setTranslateY(20);

        scoreValue.textProperty().bind(getGameState().intProperty("score").asString());
        livesValue.textProperty().bind(getGameState().intProperty("lives").asString());

        getGameScene().addUINodes(scoreLabel, scoreValue, livesLabel, livesValue);
    }

    /**
     * Input configuration, here you configure all the input events like key presses, mouse clicks, etc.
     */
    @Override
    protected void initInput() {
        onKey(KeyCode.UP, this::moveUp);
        onKey(KeyCode.DOWN, this::moveDown);
        onKey(KeyCode.LEFT, this::moveLeft);
        onKey(KeyCode.RIGHT, this::moveRight);
        onBtnDown(MouseButton.PRIMARY, () ->
                spawn("bullet", player.getCenter()));
    }

    private void moveUp() {
        if (this.player.getY() > 2) {
            this.player.translateY(-5);
        }
    }

    private void moveDown() {
        if (this.player.getY() < getAppHeight() - 25) {
            this.player.translateY(5);
        }
    }

    private void moveLeft() {
        if (this.player.getX() > 2) {
            this.player.translateX(-5);
        }
    }

    private void moveRight() {
        if (this.player.getX() < getAppWidth() - 25) {
            this.player.translateX(5);
        }
    }

    /**
     * Initialization of the game.
     */
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(gameFactory);

        // Add the player
        this.player = spawn("player", getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);

        // Add a new enemy every second
        run(() -> spawn("enemy"), Duration.seconds(1.0));
    }

    /**
     * Initialization of the physics to detect e.g. collisions.
     */
    @Override
    protected void initPhysics() {
        onCollisionBegin(FxglExampleFactory.EntityType.BULLET, FxglExampleFactory.EntityType.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld();
            enemy.removeFromWorld();
        });

        onCollisionBegin(FxglExampleFactory.EntityType.ENEMY, FxglExampleFactory.EntityType.PLAYER, (enemy, player) -> {
            showMessage("You Died!", () -> {
                getGameController().startNewGame();
            });
        });
    }
}
