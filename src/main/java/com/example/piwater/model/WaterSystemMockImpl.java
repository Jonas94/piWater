package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.*;

@Service
@ConditionalOnProperty(name = "gpio.enable", havingValue = "false")
public class WaterSystemMockImpl implements WaterSystem {

	boolean state = false;

	private final SimpMessagingTemplate websocket;

	@Autowired
	public WaterSystemMockImpl(SimpMessagingTemplate websocket) {
		this.websocket = websocket;
	}


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
