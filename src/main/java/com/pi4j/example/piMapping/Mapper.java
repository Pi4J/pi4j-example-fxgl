package com.pi4j.example.piMapping;

/*
 * This File is based on the minimal example from PI4J: https://pi4j.com/
 */

import com.pi4j.Pi4J;
import com.pi4j.example.PrintInfo;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;
import javafx.geometry.Point2D;

/**
 * <p>This file maps the inputs of a picade to a certain functionallity used to implement games with FXGL</p>
 *
 * @author Cyril Lindenmann, Frank Delporte (<a href="https://www.webtechie.be">https://www.webtechie.be</a>)
 * @version $Id: $Id
 */

public class Mapper {

    private static final int PIN_BUTTON_1 = 24; // PIN 18 = BCM 24
    private static final int PIN_LED = 22; // PIN 15 = BCM 22

    private static int pressCount = 0;

    public static void main(String[] args) throws Exception {
        // Create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // Print program title/header
        console.title("<-- The Pi4J Project -->", "Minimal Example project");


        // Initialize new runtime context
        var pi4j = Pi4J.newAutoContext();

        ArcadeBtn btn1 = new ArcadeBtn(pi4j, PIN_BUTTON_1);

        btn1.isPressed();



        // Shutdown Pi4J
        pi4j.shutdown();
    }
}
