package com.pi4j.example;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.pi4j.example.component.SnakeHeadComponent;

import java.util.List;

/**
 * The factory which defines how each entity looks like
 */
public class FxglExampleFactory implements EntityFactory {

    public static final int GRID_SIZE = 32;

    /**
     * Types of objects we are going to use in our game.
     */
    public enum EntityType {
        SNAKE_HEAD, SNAKE_BODY
    }

    /**
     * List of images to select for random body elements
     */
    private List<String> textureNames = List.of(
            "angry.png",
            "cool.png",
            "crying.png",
            "dead.png",
            "emoji.png",
            "greed.png",
            "happy.png",
            "hypnotized.png",
            "in-love.png",
            "laughing.png",
            "pressure.png",
            "smile.png",
            "wink.png"
    );

    @Spawns("snakeHead")
    public Entity newSnakeHead(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.SNAKE_HEAD)
                .viewWithBBox(texture(FXGLMath.random(textureNames).get(), GRID_SIZE, GRID_SIZE))
                .collidable()
                .with(new AutoRotationComponent())
                .with(new SnakeHeadComponent())
                .build();
    }

    @Spawns("snakeBody")
    public Entity newSnakeBody(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.SNAKE_BODY)
                .viewWithBBox(texture(FXGLMath.random(textureNames).get(), GRID_SIZE, GRID_SIZE))
                .collidable()
                .with(new AutoRotationComponent())
                .build();
    }
}
