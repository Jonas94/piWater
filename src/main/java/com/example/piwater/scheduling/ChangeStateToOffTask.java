package com.example.piwater.scheduling;

import com.pi4j.io.gpio.*;
import org.slf4j.*;

public class ChangeStateToOffTask implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(ChangeStateToOffTask.class);

	@Override
	public void run() {
		GpioPinDigitalOutput relayPin = getRelayPin();
		relayPin.low();
		LOG.info("Task has been executed!");

	}


	private GpioPinDigitalOutput getRelayPin(){
		//TODO: pit this into a singleton or similar

		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_01);
		return gpioPin;
	}
}
