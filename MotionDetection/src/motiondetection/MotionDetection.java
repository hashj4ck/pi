/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motiondetection;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class MotionDetection {

    //Motion Sensor registrieren
    private static GpioController GPIO_CONTROLLER;
    private static GpioPinDigitalInput _sensor;
    private static GpioPinDigitalOutput _led;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initialisierePins();
        registriereListener();
        run();

    }

    private static void initialisierePins() {

        //Controller initialisieren
        System.out.println("Initialisere Pins");
        GPIO_CONTROLLER = GpioFactory.getInstance();
        _sensor = GPIO_CONTROLLER.provisionDigitalInputPin(RaspiPin.GPIO_15, PinPullResistance.PULL_DOWN);
        _led = GPIO_CONTROLLER.provisionDigitalOutputPin(RaspiPin.GPIO_08);
    }

    private static void registriereListener() {

        //Listener setzen
        _sensor.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                if (event.getState().isHigh()) {
                    System.out.println("Bewegung erkannt!");
                    _led.setState(PinState.HIGH);
                } else if (event.getState().isLow()) {
                    _led.setState(PinState.LOW);
                }

            }
        });

    }

    private static void run() {
        boolean aktiv = true;

        while (aktiv) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(MotionDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
