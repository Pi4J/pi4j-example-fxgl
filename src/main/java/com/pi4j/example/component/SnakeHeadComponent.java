package com.pi4j.example.component;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.pi4j.example.FxglExampleFactory.GRID_SIZE;

public class SnakeHeadComponent extends Component {

    private Point2D direction = new Point2D(1, 0);

    // head - body - ...
    private List<Entity> bodyParts = new ArrayList<>();

    @Override
    public void onAdded() {
        bodyParts.add(entity);

        entity.setProperty("prevPos", entity.getPosition());
    }

    @Override
    public void onUpdate(double tpf) {
        entity.setProperty("prevPos", entity.getPosition());
        entity.translate(direction.multiply(GRID_SIZE));

        checkForBounds();

        for (int i = 1; i < bodyParts.size(); i++) {
            var prevPart = bodyParts.get(i - 1);
            var part = bodyParts.get(i);

            Point2D prevPos = prevPart.getObject("prevPos");

            part.setProperty("prevPos", part.getPosition());
            part.setPosition(prevPos);
        }
    }

    private void checkForBounds() {
        if (entity.getX() < 0)
            die();

        if (entity.getX() >= getAppWidth())
            die();

        if (entity.getY() < 0)
            die();

        if (entity.getY() >= getAppHeight())
            die();
    }

    public void die() {
        inc("lives", -1);

        if (geti("lives") <= 0) {
            getDialogService().showMessageBox("Game Over", 
            () -> getGameController().startNewGame());
            return;
        }

        // clean up body parts, apart from head
        bodyParts.stream()
                .skip(1)
                .forEach(Entity::removeFromWorld);

        bodyParts.clear();
        bodyParts.add(entity);

        entity.setPosition(0, 0);
        right();
    }

    public void up() {
        direction = new Point2D(0, -1);
    }

    public void down() {
        direction = new Point2D(0, 1);
    }

    public void left() {
        direction = new Point2D(-1, 0);
    }

    public void right() {
        direction = new Point2D(1, 0);
    }

    public void grow() {
        inc("score", +1);

        var lastBodyPart = bodyParts.get(bodyParts.size() - 1);

        Point2D pos = lastBodyPart.getObject("prevPos");

        var body = spawn("snakeBody", pos);
        body.setProperty("prevPos", pos);
        bodyParts.add(body);
    }

    public void log() {
        bodyParts.forEach(part -> {
            System.out.println(part.getPosition());
            System.out.println(part.getObject("prevPos").toString());
        });
    }
}