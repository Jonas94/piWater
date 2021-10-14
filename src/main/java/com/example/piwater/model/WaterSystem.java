package com.example.piwater.model;

import com.pi4j.io.gpio.*;

public interface WaterSystem {

	public boolean isBusy();

	public WaterState getState();

	public WaterState changeState(boolean state);

	public GpioPinDigitalOutput getRelayPin();
}
