package com.example.piwater.model;

import com.pi4j.io.gpio.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.stereotype.*;

@Component
@ConditionalOnProperty(name = "gpio.mock", havingValue = "false", matchIfMissing = true)
public class WaterSystemImpl implements WaterSystem {

	@Override
	public boolean isBusy() {
		final GpioPinDigitalOutput gpioPin = getRelayPin();
		return gpioPin.isHigh();
	}

	@Override
	public String getState() {
		final GpioPinDigitalOutput gpioPin = getRelayPin();

		boolean state = gpioPin.isHigh();
		String result;
		if (state) {
			result = "Pin is on!";
		} else {
			result = "Pin is off :(";
		}
		return result;
	}

	@Override
	public String changeState(boolean state) {
		final GpioPinDigitalOutput gpioPin = getRelayPin();
		if (state) {
			gpioPin.high();
		} else {
			gpioPin.low();
		}
		return getState();
	}

	@Override
	public GpioPinDigitalOutput getRelayPin() {
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_01);
		return gpioPin;
	}
}
