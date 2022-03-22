package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.*;

@Service
@ConditionalOnProperty(name = "gpio.enable", havingValue = "true", matchIfMissing = true)
public class WaterSystemImpl implements WaterSystem {

    private final SimpMessagingTemplate websocket;

    @Autowired
    public WaterSystemImpl(SimpMessagingTemplate websocket) {
        this.websocket = websocket;
    }

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

        WaterState waterState = new WaterState(state);
        this.websocket.convertAndSend("/topic/message", waterState);
        return waterState;
    }

    @Override
    public GpioPinDigitalOutput getRelayPin() {
        final GpioController gpio = GpioFactory.getInstance();
        return (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_01);
    }
}
