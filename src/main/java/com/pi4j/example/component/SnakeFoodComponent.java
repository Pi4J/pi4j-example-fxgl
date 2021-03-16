package com.pi4j.example.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.pi4j.example.FxglExampleFactory.GRID_SIZE;

public class SnakeFoodComponent extends Component {


    @Override
    public void onUpdate(double tpf) {
        entity.setProperty("prevPos", entity.getPosition());
    }

    public void respawn() {
        System.out.println("RESPAWN");
        entity.removeFromWorld();
        spawn("snakeFood", FXGLMath.random(0, getAppWidth()), FXGLMath.random(0, getAppHeight()));
    }

    public void log() {
        System.out.println("Position of food: " + entity.getPosition());
    }
}