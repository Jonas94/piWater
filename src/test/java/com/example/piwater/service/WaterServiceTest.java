package com.example.piwater.service;

import com.example.piwater.db.*;
import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import org.junit.*;
import org.junit.jupiter.api.extension.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.time.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WaterServiceTest {

	@Mock
	private WaterSystem waterSystem;


	@Mock
	WaterScheduler waterScheduler;

	@Mock
	FirebaseConnector firebaseConnector;


	@InjectMocks
	@Autowired
	WaterService waterService;


	@Test
	public void testScheduleWatering() throws IsBusyException {


		WaterInput waterInput = new WaterInput(1, LocalDateTime.now());


		waterService.enableWateringForDuration(waterInput);
		verify(waterScheduler, times(2)).scheduleActivityWithDate(any(), any());
	}
}