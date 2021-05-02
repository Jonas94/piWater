package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import org.springframework.stereotype.*;

@Component
public interface WaterSystem {

	public boolean isBusy();

	public String getState();

	public String changeState(boolean state);

		public GpioPinDigitalOutput getRelayPin();
}
