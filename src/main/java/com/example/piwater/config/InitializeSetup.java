package com.example.piwater.config;

import com.pi4j.io.gpio.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class InitializeSetup implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(InitializeSetup.class);

	@Autowired
	private Environment environment;

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("Will setup GPIO");
		setupGPIO();
		LOG.info("GPIO is now setup!");

	}

	private void setupGPIO(){
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED");
		pin.setShutdownOptions(true, PinState.LOW);
	}
}