package com.example.piwater.service;

import com.example.piwater.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class WaterService {

	WaterSystem waterSystem;
	Scheduler scheduler;

	@Autowired
	public WaterService(WaterSystem waterSystem, Scheduler scheduler) {
		this.waterSystem = waterSystem;
		this.scheduler = scheduler;
	}

	public String changeState(boolean state) {
		final GpioPinDigitalOutput gpioPin = getRelayPin();
		if (state) {
			gpioPin.high();
		} else {
			gpioPin.low();
		}
		return getState();
	}

	public void enableWateringForDuration(WaterInput waterInput) throws IsBusyException {
		if(waterSystem.isBusy()){
			throw new IsBusyException("The watering system is busy! Try again later.");
		}

		//TODO: Set start date and end date in a better way
		scheduler.scheduleActivity(new ChangeStateToOnTask(), 0);

		scheduler.scheduleActivity(new ChangeStateToOffTask(), waterInput.getMinutesToWater());


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

	private GpioPinDigitalOutput getRelayPin(){
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_01);
		return gpioPin;
	}
}
