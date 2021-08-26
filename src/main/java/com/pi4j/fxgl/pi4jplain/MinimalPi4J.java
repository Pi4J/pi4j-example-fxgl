package com.pi4j.fxgl.pi4jplain;

import java.time.Duration;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import com.pi4j.util.Console;

/**
 * @author Dieter Holz
 */
public class MinimalPi4J {
    private static final int PIN_BUTTON = 24; // PIN 18 = BCM 24

    private static int pressCount = 0;

    public static void main(String[] args) throws Exception {
        // Create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // Print program title/header
        console.title("<-- The Pi4J Project -->", "Minimal Example for accessing arcade consoles");

        // ------------------------------------------------------------
        // Initialize the Pi4J Runtime Context
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // The 'Pi4J' static class includes a few helper context
        // creators for the most common use cases.  The 'newAutoContext()'
        // method will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'

        // to get the default context you can use:
        // var pi4j = Pi4J.newAutoContext();

        // In our context of gaining access to the physical buttons and joystick of arcade consoles
        // we only need access to the digital input. There's no output, no I2C, no ...

        final var piGpio = PiGpio.newNativeInstance();

        // Build Pi4J context with this platform and PiGPIO providers
        final var pi4j = Pi4J.newContextBuilder()
                             .noAutoDetect()
                             .add(new RaspberryPiPlatform() {
                                 @Override
                                 protected String[] getProviders() {
                                     return new String[]{};
                                 }
                             })
                             .add(PiGpioDigitalInputProvider.newInstance(piGpio))  // on our arcade console we just have digitalInput
                             .build();

        // Here we will create I/O interfaces for a (GPIO) digital input pin.

        var buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                                       .id("BCM_" + PIN_BUTTON)
                                       .name("Button")
                                       .address(PIN_BUTTON)
                                       .pull(PullResistance.PULL_DOWN)
                                       .debounce(10_000L)
                                       .provider("pigpio-digital-input");
        final var button = pi4j.create(buttonConfig);

        // specify some action, that will be triggered whenever the button's state changed
        button.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                pressCount++;
                console.println("Button was pressed for the " + pressCount + "th time");
            } else {
                console.println("Button was depressed");
            }
        });

        console.println("Press the button to see it in action!");

        // Wait for 15 seconds while handling events before exiting
        delay(Duration.ofSeconds(15));

        // ------------------------------------------------------------
        // Terminate the Pi4J library
        // ------------------------------------------------------------
        // We we are all done and want to exit our application, we must
        // call the 'shutdown()' function on the Pi4J static helper class.
        // This will ensure that all I/O instances are properly shutdown,
        // released by the the system and shutdown in the appropriate
        // manner. Terminate will also ensure that any background
        // threads/processes are cleanly shutdown and any used memory
        // is returned to the system.

        // Shutdown Pi4J
        pi4j.shutdown();
    }

    private static void delay(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
