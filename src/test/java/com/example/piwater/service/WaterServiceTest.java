package com.example.piwater.service;

import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WaterServiceTest {

	@Mock
	private WaterSystem waterSystem;


	@Mock
	Scheduler scheduler;


	@InjectMocks
	@Autowired
	WaterService waterService;


	@Test
	public void testScheduleWatering() throws IsBusyException {


		WaterInput waterInput = new WaterInput(1, LocalDateTime.now());


		waterService.enableWateringForDuration(waterInput);
		verify(scheduler, times(2)).scheduleActivityWithDate(any(), any());
	}
}