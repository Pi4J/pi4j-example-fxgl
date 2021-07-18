package com.pi4j.fxgl.pi4jplain;

import java.time.Duration;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;

/**
 * @author Dieter Holz
 */
public class PinChecker {
    private static final int[] ALL_DIGITAL_INPUT_BCMS = {4, 5, 6, 12, 13, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};

    public static void main(String[] args) throws Exception {
//        final var console = new Console();
//
//        console.title("<-- The Pi4J Project -->", "Helps to find the pin of attached buttons");

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

        for (int bcm : ALL_DIGITAL_INPUT_BCMS) {
            var buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                                           .id("BCM_" + bcm)
                                           .name("Button_" + bcm)
                                           .address(bcm)
                                           .pull(PullResistance.PULL_UP)
                                           .debounce(10_000L)
                                           .build();
            final var button = pi4j.create(buttonConfig);

            // specify some action, that will be triggered whenever the button's state changed
            button.addListener(e -> {
                if (e.state() == DigitalState.LOW) {
                    System.out.println(button.getId() + " was pressed");
                } else {
                    System.out.println(button.getId() + " was depressed");
                }
            });
        }

        System.out.println("Press the buttons to see them in action!");

        // Wait for 15 seconds while handling events before exiting
        delay(Duration.ofSeconds(30));

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
