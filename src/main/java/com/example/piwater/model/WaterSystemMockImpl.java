package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.stereotype.*;

@Service
@ConditionalOnProperty(name = "gpio.enable", havingValue = "false")
public class WaterSystemMockImpl implements WaterSystem {

	boolean state = false;

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
		return new WaterState(state);
	}

	@Override
	public GpioPinDigitalOutput getRelayPin() {

		return null;
	}
}
