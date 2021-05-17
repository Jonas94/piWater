package com.example.piwater.scheduling;

import com.example.piwater.db.*;
import com.example.piwater.exception.*;
import com.example.piwater.service.*;
import com.example.piwater.state.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class PollFirestoreTest {


	@Mock
	RecurringCheckState recurringCheckState;

	@Mock
	FirebaseConnector firebaseConnector;

	@InjectMocks
	PollFirestore pollFirestore;


	@Test
	public void shouldGiveOneRecurringWateringToDo() {

		List<RecurringWatering> recurringWateringList = new ArrayList<>();
		RecurringWatering recurringWatering = new RecurringWatering();

		List<String> times = new ArrayList<>();
		times.add("16:00");
		recurringWatering.setTime(times);

		recurringWateringList.add(recurringWatering);

		Date date = new Date(1621259880000L); //Monday, 17 May 2021 15:58:00 GMT+02:00

		Date nowDate = new Date(1621260001000L); // Monday, 17 May 2021 16:00:01 GMT+02:00

		Assert.assertEquals(1,pollFirestore.checkIfAnyRecurringWateringShouldBeDone(recurringWateringList, date, nowDate).size());


	}

	@Test
	public void shouldGiveOneRecurringWateringToDoWhenRecurringDateIsNotYetInThePast() {

		List<RecurringWatering> recurringWateringList = new ArrayList<>();
		RecurringWatering recurringWatering = new RecurringWatering();

		List<String> times = new ArrayList<>();
		times.add("16:00");
		recurringWatering.setTime(times);

		recurringWateringList.add(recurringWatering);

		Date date = new Date(1621259880000L); //Monday, 17 May 2021 15:58:00 GMT+02:00

		Date nowDate = new Date(1621259999000L); //Monday, 17 May 2021 15:59:59 GMT+02:00

		Assert.assertEquals(0,pollFirestore.checkIfAnyRecurringWateringShouldBeDone(recurringWateringList, date, nowDate).size());


	}

}