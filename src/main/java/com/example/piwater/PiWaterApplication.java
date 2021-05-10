package com.example.piwater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.*;

@EnableScheduling
@SpringBootApplication
public class PiWaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiWaterApplication.class, args);
	}

}
