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

    }

    public void respawn() {
        entity.setPosition(FXGLMath.random(1, 29)*GRID_SIZE, FXGLMath.random(1, 29)*GRID_SIZE);
        log();
    }

    public void log() {
        System.out.println("Position of food: " + entity.getPosition());
    }
}