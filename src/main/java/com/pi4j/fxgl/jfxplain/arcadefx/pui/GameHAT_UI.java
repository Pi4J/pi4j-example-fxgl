package com.pi4j.fxgl.jfxplain.arcadefx.pui;

import java.util.Arrays;

import com.pi4j.fxgl.jfxplain.arcadefx.model.AppModel;
import com.pi4j.fxgl.util.GameHATButton;

import static com.pi4j.fxgl.util.GameHATButton.JOYSTICK;
import static com.pi4j.fxgl.util.GameHATButton.START;
import static com.pi4j.fxgl.util.GameHATButton.X;
import static com.pi4j.fxgl.util.GameHATButton.Y;

/**
 * GameHAT UI works on the same model 'AppModel' as 'PicadeUI' and 'GUI'
 *
 * It uses the same BaseClass 'PUI_Base' to ensure the same structure in all our PUIs
 * (like 'ViewMixin' does for our GUIs)
 *
 * @author Dieter Holz
 */
public class GameHAT_UI extends PUI_Base<AppModel> {

    public GameHAT_UI(AppModel model) {
        super(model);
    }

    @Override
    public void initialize(AppModel model) {
        GameHATButton.initializeAll();
    }

    @Override
    protected void setupJoyStickEvents(AppModel model) {
        JOYSTICK.xProperty().bindBidirectional(model.dukePositionXProperty());
        JOYSTICK.yProperty().bindBidirectional(model.dukePositionYProperty());

        JOYSTICK.onNorth(() -> withModel(() -> model.setMessage("Joystick N")));
        JOYSTICK.onSouth(() -> withModel(() -> model.setMessage("Joystick S")));
        JOYSTICK.onWest(()  -> withModel(() -> model.setMessage("Joystick W")));
        JOYSTICK.onEast(()  -> withModel(() -> model.setMessage("Joystick E")));

        JOYSTICK.onHome(()  -> withModel(() -> model.setMessage("back to center")));
    }

    @Override
    public void setupButtonEvents(AppModel model) {
        Arrays.stream(GameHATButton.values())
              .filter(button -> !button.getLabel().startsWith("Joy"))
              .forEach(button -> {
                  button.addOnPressed(() -> withModel(() -> model.setMessage("'" + button.getLabel() + "' is pressed")));
                  button.addOnReleased(() -> withModel(() -> model.setMessage("'" + button.getLabel() + "' is released")));
              });

//           ESCAPE.addOnReleased(() -> {
//               if(GameHATButton.TR.isPressed()){
//                   System.exit(0);
//               }
//           });
//
//
//           Button_1.addWhileDown(() -> System.out.println("'X' is pressed"), Duration.ofMillis(500));

        START.addOnReleased(() -> withModel(() -> model.setCounter(0)));

        X.addWhileDown(() -> withModel(model::increaseCounter));
        Y.addWhileDown(() -> withModel(model::decreaseCounter));
    }

}
