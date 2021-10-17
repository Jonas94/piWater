package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.stereotype.*;

@Service
@ConditionalOnProperty(name = "gpio.enable", havingValue = "true", matchIfMissing = true)
public class WaterSystemImpl implements WaterSystem {

    @Override
    public boolean isBusy() {
        final GpioPinDigitalOutput gpioPin = getRelayPin();
        return gpioPin.isHigh();
    }

    @Override
    public WaterState getState() {
        final GpioPinDigitalOutput gpioPin = getRelayPin();

        return new WaterState(gpioPin.isHigh());
    }

    @Override
    public WaterState changeState(boolean state) {
        final GpioPinDigitalOutput gpioPin = getRelayPin();
        if (state) {
            gpioPin.high();
        } else {
            gpioPin.low();
        }
        return new WaterState(state);
    }

    @Override
    public GpioPinDigitalOutput getRelayPin() {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_01);
        return gpioPin;
    }
}
