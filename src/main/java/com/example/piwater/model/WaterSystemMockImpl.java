package com.example.piwater.model;

import com.example.piwater.db.*;
import com.example.piwater.service.*;
import com.pi4j.io.gpio.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.time.*;
import java.util.concurrent.*;

@Component
@ConditionalOnProperty(name = "gpio.mock", havingValue = "true")
public class WaterSystemMockImpl implements WaterSystem {

	@Override
	public boolean isBusy() {
		return false;
	}

	@Override
	public WaterState getState() {
		return new WaterState(true);
	}

	@Override
	public WaterState changeState(boolean state) {
		return new WaterState(state);
	}

	@Override
	public GpioPinDigitalOutput getRelayPin() {

		return null;
	}
}
