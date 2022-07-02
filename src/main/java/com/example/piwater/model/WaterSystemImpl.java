package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
@Profile("!local")
public class WaterSystemImpl implements WaterSystem {

    private final SimpMessagingTemplate websocket;

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
