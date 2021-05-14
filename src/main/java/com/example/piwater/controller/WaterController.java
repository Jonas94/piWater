package com.example.piwater.controller;

import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.service.*;
import com.pi4j.io.gpio.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WaterController {

	WaterService waterService;

	public WaterController(WaterService waterService) {
		this.waterService = waterService;
	}

	@GetMapping("/state")
	public ResponseEntity<WaterState> getState(){
		return ResponseEntity.ok(waterService.getState());
	}

	@PostMapping("/stop")
	public ResponseEntity<WaterState> setState() {
		return ResponseEntity.ok(waterService.stopWatering());
	}

	@PostMapping("/enableWatering")
	public ResponseEntity<RestResult> enableWatering(WaterInput waterInput) {
		try{
			waterService.enableWateringForDuration(waterInput);
		} catch (IsBusyException e) {
			return ResponseEntity.ok(new RestResult("Water system is busy, please try later!"));
		}

		String result = "Watering scheduled at " + waterInput.getStartDate() + " for " + waterInput.getMinutesToWater() + " minutes";

		return ResponseEntity.ok(new RestResult(result));
	}

	@GetMapping("/getAllWaterings")
	public ResponseEntity<List<Watering>> getAllWaterings(){

		return ResponseEntity.ok(waterService.getAllWaterings());
	}

	@GetMapping("/getFutureWaterings")
	public ResponseEntity<List<Watering>> getFutureWaterings(){

		return ResponseEntity.ok(waterService.getFutureWaterings());
	}

	@GetMapping("/getAllRecurringWaterings")
	public ResponseEntity<List<RecurringWatering>> getAllRecurringWaterings(){

		return ResponseEntity.ok(waterService.getAllRecurringWaterings());
	}
	@GetMapping("/getActiveRecurringWaterings")
	public ResponseEntity<List<RecurringWatering>> getActiveRecurringWaterings(){

		return ResponseEntity.ok(waterService.getActiveRecurringWaterings());
	}

	@GetMapping("/shutdown")
	public ResponseEntity<RestResult> shutdown(){
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED");
		gpio.shutdown();
		gpio.unprovisionPin(pin);
		return ResponseEntity.ok(new RestResult("Shutdown!"));
	}
}
