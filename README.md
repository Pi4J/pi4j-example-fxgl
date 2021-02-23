
 Pi4J V2 :: Java I/O Library for Raspberry Pi :: Example game application with FXGL
===================================================================================

[![Build Status](https://github.com/pi4j/pi4j-example-fxgl/workflows/Maven/badge.svg)](https://github.com/Pi4J/pi4j-example-fxgl/actions/workflows/maven.yml)

This project contains an example application which uses the Pi4J (V2) library and uses 
an Arcade button and joystick kit to control a JavaFX FXGL game. Full description is available on 
[the Pi4J website](https://v2.pi4j.com/getting-started/game-development-with-fxgl/)

## PROJECT OVERVIEW

The goal of the example project is to show how to set up a Pi4J Maven for the Raspberry Pi with JavaFX and some
physical buttons.

## WIRING

This project uses an [Arcade kit](https://www.kiwi-electronics.nl/pim-471?search=arcade&description=true)
in combination with a [Picade X HAT USB-C](https://www.kiwi-electronics.nl/index.php?route=product/product&search=arcade&description=true&product_id=4337)
to easily connect the wires of the buttons and joystick.

Connect the USB power to the hat instead of your Raspberry Pi, and use the power button on the hat to 
start your Raspberry Pi.

![Arcade kit components](assets/arcade_parts_kit.jpg)
![Picade Hat](assets/picade_hat.jpg)

Pimoroni provides a [GitHub project](https://github.com/pimoroni/picade-hat) with software to use
this hat with RetroPie, but this project aims to take full control of the hardware with Java.

The GPIO numbers are defined by the hat and can be found on [pinout.xyz](https://pinout.xyz/pinout/picade_hat)

![Picade Hat pin numbers](assets/picade_hat_pin_numbers.png)

## RUNTIME DEPENDENCIES

This project uses Pi4J V.2 which has the following runtime dependency requirements:
- [**SLF4J (API)**](https://www.slf4j.org/)
- [**SLF4J-SIMPLE**](https://www.slf4j.org/)
- [**PIGPIO Library**](http://abyz.me.uk/rpi/pigpio) (for the Raspberry Pi) - This 
dependency comes pre-installed on recent Raspbian images.  However, you can also 
download and install it yourself using the instructions found 
[here](http://abyz.me.uk/rpi/pigpio/download.html).
  
As this application has a JavaFX user interface, we will also need some extra runtimes. This is
fully described on ["User interface with JavaFX](https://v2.pi4j.com/getting-started/user-interface-with-javafx/).

## BUILD DEPENDENCIES & INSTRUCTIONS

This project can be built with [Apache Maven](https://maven.apache.org/) 3.6 
(or later) and Java 11 OpenJDK (or later). These prerequisites must be installed 
prior to building this project.  The following command can be used to download 
all project dependencies and compile the Java module.  You can build this 
project directly on a Raspberry Pi with Java 11+.  

```
mvn clean package
```

### Compiled application to run on the Raspberry Pi

Once the build is complete and was successful, you can find the compiled 
artifacts in the `target` (Maven) or `build` (Gradle) folder.  Specifically 
all dependency modules (JARs) and a simple `run.sh` bash script will be located in the 
`target/distribution` (Maven) or `build/distribution` (Gradle) folder.  

These are all the required files needed to distribute (copy) to your
Raspberry Pi to run this project.  If you are using the native bindings running 
locally on the Raspberry Pi, then you make have to run the program using `sudo` 
to gain the necessary access permissions to the hardware I/O. 

This is the list of files created by the build process of this example application:

* pi4j-core
* pi4j-example-fxgl
* pi4j-library-pigpio
* pi4j-plugin-pigpio
* pi4j-plugin-raspberrypi
* slf4j-api
* slf4j-simple
* run.sh --> this is the actual start file which will run pi4j-example-fxgl

Make the run script executable and start it like this:

```
chmod +x run.sh
sudo ./run.sh
```

## LICENSE

 Pi4J Version 2.0 and later is licensed under the Apache License,
 Version 2.0 (the "License"); you may not use this file except in
 compliance with the License.  You may obtain a copy of the License at:
      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

