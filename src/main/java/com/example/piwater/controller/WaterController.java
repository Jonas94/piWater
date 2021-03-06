package com.example.piwater.controller;

import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.service.watering.RecurringWatering;
import com.example.piwater.service.watering.WaterInput;
import com.example.piwater.service.watering.WaterService;
import com.example.piwater.service.watering.Watering;
import com.pi4j.io.gpio.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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

	@GetMapping("/getCurrentWatering")
	public ResponseEntity<Watering> getCurrentWatering() {
		return ResponseEntity.ok(waterService.getCurrentWatering());
	}

	@PostMapping("/stop")
	public ResponseEntity<WaterState> setState() {
		return ResponseEntity.ok(waterService.stopWatering());
	}

	@PostMapping(path = "enableWatering",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> enableWatering(@RequestBody WaterInput waterInput) {
		try{
			waterService.enableWateringForDuration(waterInput);
		} catch (IsBusyException e) {
			return ResponseEntity.ok(new RestResult("Water system is busy, please try later!"));
		}

		return ResponseEntity.ok(new EnableWateringResponse(waterInput.getStartDateAsLong(), waterInput.getMinutesToWater()));
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

	@PostMapping("/saveRecurringWatering")
	public ResponseEntity<String> saveRecurringWaterings(RecurringWatering recurringWatering){
		waterService.saveRecurringWater(recurringWatering);
		return ResponseEntity.ok("Ok!");
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
