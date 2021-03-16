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
 * Licensed under the Apache License, Version 2.0 (the "License"),
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
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.physics.CollisionHandler;
import com.pi4j.example.component.SnakeFoodComponent;
import com.pi4j.example.component.SnakeHeadComponent;
import com.pi4j.example.util.Pi4JFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameState;
import static com.pi4j.example.FxglExampleFactory.GRID_SIZE;

/**
 * <p>This example fully describes the base usage of Pi4J to create a game</p>
 */
public class FxglExample extends GameApplication {

    /**
     * Reference to the FXGL factory which will defines how all the types must be created.
     */
    private final FxglExampleFactory gameFactory = new FxglExampleFactory();

    /**
     * Reference to the Pi4J factory which manages the GPIOs.
     */
    private final Pi4JFactory pi4JFactory = new Pi4JFactory();

    /**
     * Player object we are going to use to provide to the factory so it can start a bullet from the player center.
     */
    private Entity player;


    /**
     * food object is spawned at random on the map
     */
    private Entity food;

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
        settings.setWidth(GRID_SIZE * 30);
        settings.setHeight(GRID_SIZE * 30);
        settings.setTitle("FXGL Snake Game");
        settings.setTicksPerSecond(10);
        pi4JFactory.getConsole().println("Init game settings done");
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
        pi4JFactory.getConsole().println("Init game vars done");
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
        pi4JFactory.getConsole().println("Init game UI done");
    }



    /**
     * Input configuration, here you configure all the input events like key presses, mouse clicks, etc.
     */
    @Override
    protected void initInput() {
        onKeyDown(KeyCode.LEFT, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                SnakeHeadComponent::left));
        onKeyDown(KeyCode.RIGHT, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                SnakeHeadComponent::right));
        onKeyDown(KeyCode.UP, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                SnakeHeadComponent::up));
        onKeyDown(KeyCode.DOWN, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                SnakeHeadComponent::down));
        onKeyDown(KeyCode.F, () -> player.getComponent(SnakeHeadComponent.class).grow());
        onKeyDown(KeyCode.G, () -> player.getComponent(SnakeHeadComponent.class).log());
        pi4JFactory.getConsole().println("Init game inputs done");
    }

    /**
     * Initialization of the game by providing the {@link EntityFactory}.
     */
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(gameFactory);
        // Add the player
        player = spawn("snakeHead", 0, 0);
//        food = spawn("snakeFood", getAppWidth()/2, getAppHeight()/2);
        food = spawn("snakeFood", 0, 0);
        pi4JFactory.getConsole().println("Init game done");
    }

    @Override
    protected void initPhysics() {

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(SnakeType.SNAKE_HEAD, SnakeType.SNAKE_FOOD) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity food) {
                food.getComponentOptional(SnakeFoodComponent.class).ifPresent(SnakeFoodComponent::respawn);
                player.getComponent(SnakeHeadComponent.class).grow();
            }
        });
        pi4JFactory.getConsole().println("Init physics done");
    }
}