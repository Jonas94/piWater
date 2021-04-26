package com.example.piwater.controller;

import com.example.piwater.service.*;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class WaterController {

	@Autowired
	WaterService waterService;


	@GetMapping("/state")
	public RestResult getStatus(){
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED");
		boolean state = pin.isHigh();
		String result;
		if(state){
			result = "Pin is on!";
		}
		else{
			result = "Pin is off :(";
		}
		return new RestResult(200, result);
	}

	@PostMapping("/state")
	public void setStatus(boolean state) {

		final GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_01);

		if(state){
			gpioPin.high();
		}
		else
		{
			gpioPin.low();
		}
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
