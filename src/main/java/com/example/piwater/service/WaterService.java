package com.example.piwater.service;

import com.pi4j.io.gpio.*;
import org.springframework.stereotype.*;

@Service
public class WaterService {


	public String changeState(boolean state) {
		final GpioPinDigitalOutput gpioPin = getRelayPin();
		if (state) {
			gpioPin.high();
		} else {
			gpioPin.low();
		}
		return getState();
	}
	private GpioPinDigitalOutput getRelayPin(){
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_01);
		return gpioPin;
	}

	public String getState() {
		final GpioPinDigitalOutput gpioPin = getRelayPin();

		boolean state = gpioPin.isHigh();
		String result;
		if(state){
			result = "Pin is on!";
		}
		else{
			result = "Pin is off :(";
		}
		return result;
	}
}
