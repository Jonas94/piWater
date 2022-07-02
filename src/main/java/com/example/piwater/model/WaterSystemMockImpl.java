package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
@Profile("local")
public class WaterSystemMockImpl implements WaterSystem {

    boolean state = false;

    private final SimpMessagingTemplate websocket;

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public WaterState getState() {
        return new WaterState(state);
    }

    @Override
    public WaterState changeState(boolean state) {
        this.state = state;
        WaterState waterState = new WaterState(state);
        this.websocket.convertAndSend("/topic/message", waterState);
        return waterState;

    }

    @Override
    public GpioPinDigitalOutput getRelayPin() {

        return null;
    }
}
