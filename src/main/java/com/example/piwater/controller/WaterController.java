package com.example.piwater.controller;

import com.example.piwater.*;
import com.example.piwater.service.*;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class WaterController {

	@Autowired
	WaterService waterService;


	@GetMapping("/state")
	public RestResult getState(){
		return new RestResult(200, waterService.getState());
	}

	@PostMapping("/state")
	public RestResult setState(boolean state) {
		return new RestResult(200, waterService.changeState(state));
	}

	@PostMapping("/enableWatering")
	public RestResult enableWatering(WaterInput waterInput) {
		try{
			waterService.enableWateringForDuration(waterInput);
		} catch (IsBusyException e) {
			return new RestResult(400, "Water system is busy, please try later!"); //TODO: Fix proper code

		}
		return new RestResult(200, "Watering!"); //TODO: replace with a response about scheduling
	}

	@GetMapping("/shutdown")
	public RestResult shutdown(){
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED");
		gpio.shutdown();
		gpio.unprovisionPin(pin);
		return new RestResult(200, "Shutdown!");
	}
}
