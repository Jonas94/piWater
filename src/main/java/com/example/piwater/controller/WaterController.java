package com.example.piwater.controller;

import org.springframework.web.bind.annotation.*;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import javax.ws.rs.*;

@RestController
public class WaterController {
	private GpioController gpio ;

	@GetMapping("/state")
	public RestResult getStatus(){
		return new RestResult(200, "It's on!");
	}

	@PostMapping("/state")
	public void setStatus(boolean state) {
		//GpioController gpioController; //Put it in singleton

		// create gpio controller
		final GpioController gpio = GpioFactory.getInstance();

		// provision gpio pin #01 as an output pin and turn on
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED");

		// set shutdown state for this pin
		//pin.setShutdownOptions(true, PinState.LOW);

		if(state){
			pin.high();
		}
		else
		{
			pin.low();
		}

		// stop all GPIO activity/threads by shutting down the GPIO controller
		// (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
		gpio.shutdown();
		gpio.unprovisionPin(pin);
		}


}
