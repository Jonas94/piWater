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
	public String getState() {
		FirebaseConnector firebaseConnector = new FirebaseConnector();
		try {
			firebaseConnector.addDataToFirestore(new WaterInput(5, LocalDateTime.now()));
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Pin is on! (MOCKED)";
	}

	@Override
	public String changeState(boolean state) {
		return "State is changed (MOCKED)!";
	}

	@Override
	public GpioPinDigitalOutput getRelayPin() {

		return null;
	}
}
