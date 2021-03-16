package com.pi4j.example;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.pi4j.example.component.SnakeFoodComponent;
import com.pi4j.example.component.SnakeHeadComponent;

import java.awt.*;
import java.util.List;

/**
 * The factory which defines how each entity looks like
 */
public class FxglExampleFactory implements EntityFactory {

    public static final int GRID_SIZE = 32;

    /**
     * List of images to select for random body elements
     */
    private List<String> snakeBodyTextureNames = List.of(
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

    private List<String> snakeFoodTextureNames = List.of(
            "apple.png"
    );

    @Spawns("snakeHead")
    public Entity newSnakeHead(SpawnData data) {
        return entityBuilder(data)
                .type(SnakeType.SNAKE_HEAD)
                .viewWithBBox(texture(FXGLMath.random(snakeBodyTextureNames).get(), GRID_SIZE, GRID_SIZE))
                .bbox(new HitBox(BoundingShape.box(GRID_SIZE, GRID_SIZE)))
                .with(new CollidableComponent(true))
                .with(new AutoRotationComponent())
                .with(new SnakeHeadComponent())
                .build();
    }

    @Spawns("snakeBody")
    public Entity newSnakeBody(SpawnData data) {
        return entityBuilder(data)
                .type(SnakeType.SNAKE_BODY)
                .viewWithBBox(texture(FXGLMath.random(snakeBodyTextureNames).get(), GRID_SIZE, GRID_SIZE))
                .with(new AutoRotationComponent())
                .build();
    }

    @Spawns("snakeFood")
        public Entity newSnakeFood(SpawnData data) {
            return entityBuilder(data)
                    .type(SnakeType.SNAKE_FOOD)
                    .viewWithBBox(texture(FXGLMath.random(snakeFoodTextureNames).get(), GRID_SIZE, GRID_SIZE))
                    .bbox(new HitBox(BoundingShape.box(GRID_SIZE, GRID_SIZE)))
                    .with(new SnakeFoodComponent())
                    .with(new CollidableComponent(true))
                    .build();
    }
}
