package com.example.piwater.service;

import com.example.piwater.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.*;

import java.time.*;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
class WaterServiceTest {


	@Mock
	TaskScheduler taskScheduler;

	@Mock
	WaterSystem waterSystem;


	@InjectMocks
	Scheduler scheduler = new Scheduler(taskScheduler);

	@InjectMocks
	@Autowired
	WaterService waterService;


	@Test
	void testScheduleStopWatering() throws IsBusyException {


		WaterInput waterInput = new WaterInput(1, new Date());


		waterService.enableWateringForDuration(waterInput);

	}
}