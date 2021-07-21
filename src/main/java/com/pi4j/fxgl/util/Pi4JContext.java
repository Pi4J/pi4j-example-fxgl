package com.pi4j.fxgl.util;

import java.time.Duration;

import java.util.List;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.mock.platform.MockPlatform;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.plugin.mock.provider.i2c.MockI2CProvider;
import com.pi4j.plugin.mock.provider.pwm.MockPwmProvider;
import com.pi4j.plugin.mock.provider.serial.MockSerialProvider;
import com.pi4j.plugin.mock.provider.spi.MockSpiProvider;
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
        return runsOnPi() ? createCleanContext() : createMockContext();
    }

    private static boolean runsOnPi(){
        String vendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");

        List<String> piArchs = List.of("aarch64", "arm");

        return "Raspbian".equals(vendor) ||
               ("Linux".equals(osName) && piArchs.contains(osArch));
    }

    private static Context createMockContext(){
        return Pi4J.newContextBuilder()
                   .add(new MockPlatform())
                   .add(MockAnalogInputProvider.newInstance(),
                        MockAnalogOutputProvider.newInstance(),
                        MockSpiProvider.newInstance(),
                        MockPwmProvider.newInstance(),
                        MockSerialProvider.newInstance(),
                        MockI2CProvider.newInstance(),
                        MockDigitalInputProvider.newInstance(),
                        MockDigitalOutputProvider.newInstance())
                   .build();
    }

    private static Context createCleanContext(){
        Context oldContext = createContext();
        oldContext.shutdown();

        delay(Duration.ofMillis(500));

        return createContext();
    }

    private static Context createContext(){
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

    private static void delay(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
