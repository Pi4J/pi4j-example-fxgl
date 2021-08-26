package com.pi4j.fxgl.util;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.mock.platform.MockPlatform;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;

/**
 * @author Dieter Holz
 */
public class Pi4JContext {
    private static final Context INSTANCE = buildNewContext();

    /**
     * Default debounce time in microseconds
     */
    private static final long DEBOUNCE = 10_000L;

    private Pi4JContext() {
    }

    public static DigitalInput createDigitalInput(int bcmPin, String label) {
        return INSTANCE.create(buildDigitalInputConfig(bcmPin, label));
    }

    public static void shutdown(){
        INSTANCE.shutdown();
    }

    private static Context buildNewContext() {
        return runsOnPi() ? createRealContext() : createMockContext();
    }

    private static boolean runsOnPi(){
        return "Linux".equals(System.getProperty("os.name")) && "aarch64".equals(System.getProperty("os.arch"));
    }

    private static Context createMockContext(){
        return Pi4J.newContextBuilder()
                    .add(new MockPlatform())
                    .add(MockDigitalInputProvider.newInstance())
                    .build();
    }

    private static Context createRealContext(){
        // Initialize PiGPIO
        final var piGpio = PiGpio.newNativeInstance();

        // Build Pi4J context with this platform and PiGPIO providers
        return Pi4J.newContextBuilder()
                          .noAutoDetect()
                          .add(new RaspberryPiPlatform(){
                              @Override
                              protected String[] getProviders() {
                                  return new String[]{};
                              }
                          })
                          .add(PiGpioDigitalInputProvider.newInstance(piGpio))  // on our arcade console we just have digitalInput
                          .build();
    }

    private static DigitalInputConfig buildDigitalInputConfig(int address, String label) {
        return DigitalInput.newConfigBuilder(INSTANCE)
                           .id("BCM_" + address)
                           .name(label)
                           .address(address)
                           .debounce(DEBOUNCE)
                           .pull(PullResistance.PULL_UP)
                           .build();
    }
}
