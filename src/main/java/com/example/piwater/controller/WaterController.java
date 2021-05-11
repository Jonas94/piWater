package com.example.piwater.controller;

import com.example.piwater.exception.*;
import com.example.piwater.service.*;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class WaterController {

	@Autowired
	WaterService waterService;

	@GetMapping("/state")
	public RestResult getState(){
		return new RestResult(200, waterService.getState());
	}

	@PostMapping("/stop")
	public RestResult setState() {
		return new RestResult(200, waterService.stopWatering());
	}

	@PostMapping("/enableWatering")
	public ResponseEntity<String> enableWatering(WaterInput waterInput) {
		try{
			waterService.enableWateringForDuration(waterInput);
		} catch (IsBusyException e) {
			return new ResponseEntity("Water system is busy, please try later!", HttpStatus.OK); //TODO: Make this json
		}

		String result = "Watering scheduled at " + waterInput.getStartDate() + " for " + waterInput.getMinutesToWater() + " minutes";

		return new ResponseEntity(result, HttpStatus.OK); //TODO: Make this json
	}

	@GetMapping("/getAllWaterings")
	public RestResult getAllWaterings(){

		waterService.getAllWaterings();

		return new RestResult(200, "Found all waterings!");
	}

	@GetMapping("/getFutureWaterings")
	public RestResult getFutureWaterings(){

		waterService.getFutureWaterings();

		return new RestResult(200, "Found future waterings!");
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
