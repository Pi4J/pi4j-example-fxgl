package com.pi4j.fxgl.jfxplain.arcadefx.pui;

import java.util.Arrays;

import com.pi4j.fxgl.jfxplain.arcadefx.model.AppModel;
import com.pi4j.fxgl.util.PicadeButton;

import static com.pi4j.fxgl.util.PicadeButton.Button_1;
import static com.pi4j.fxgl.util.PicadeButton.Button_2;
import static com.pi4j.fxgl.util.PicadeButton.JOYSTICK;
import static com.pi4j.fxgl.util.PicadeButton.START;

/**
 * @author Dieter Holz
 */
public class PicadeUI extends PUI_Base<AppModel> {

    public PicadeUI(AppModel model) {
        super(model);
    }

    @Override
    public void initialize(AppModel model) {
        PicadeButton.initializeAll();
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
        Arrays.stream(PicadeButton.values())
              .filter(button -> !button.getLabel().startsWith("Joy"))
              .forEach(button -> {
                  button.addOnPressed(()  -> withModel(() -> model.setMessage("'" + button.getLabel() + "' is pressed")));
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

        Button_1.addWhileDown(() -> withModel(model::increaseCounter));
        Button_2.addWhileDown(() -> withModel(model::decreaseCounter));
    }

}
