package com.example.piwater.config;

import com.example.piwater.db.*;
import com.example.piwater.state.*;
import com.pi4j.io.gpio.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;


@Component
@ConfigurationProperties(prefix = "gpio") //TODO: Needed?
public class InitializeSetup implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(InitializeSetup.class);
	@Value("${gpio.enable}")
	private boolean gpioEnabled;

	private RecurringCheckState recurringCheckState;

	public InitializeSetup(RecurringCheckState recurringCheckState) {
		this.recurringCheckState = recurringCheckState;
	}

	@Override
	public void afterPropertiesSet() throws IOException {

		if(gpioEnabled) {
			LOG.info("Will setup GPIO");
			setupGPIO();
			LOG.info("GPIO is now setup!");
		}
		else {
			LOG.info("Gpio not set up, property disabled");
		}

		recurringCheckState.setLatestCheckTime(new Date().getTime());

		FirebaseConnector.initializeFireStore();
	}

	private void setupGPIO(){
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "relayPin");
		pin.setShutdownOptions(true, PinState.LOW);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer pspc() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}