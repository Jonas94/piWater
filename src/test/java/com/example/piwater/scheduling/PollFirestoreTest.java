package com.example.piwater.scheduling;

import com.example.piwater.db.*;
import com.example.piwater.service.watering.RecurringWatering;
import com.example.piwater.state.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PollFirestoreTest {

    @Mock
    RecurringCheckState recurringCheckState;

    @Mock
    FirebaseConnector firebaseConnector;

    @InjectMocks
    PollFirestore pollFirestore;


    @Test
    void shouldGiveOneRecurringWateringToDo() {

        List<RecurringWatering> recurringWateringList = new ArrayList<>();
        RecurringWatering recurringWatering = new RecurringWatering();

        List<String> days = new ArrayList<>();
        days.add("mon");
        days.add("wed");

        recurringWatering.setDays(days);

        List<String> times = new ArrayList<>();
        times.add("16:00");

        recurringWatering.setTime("16:00");

        recurringWateringList.add(recurringWatering);

        Date latestCheckDate = new Date(1621259880000L); //Monday, 17 May 2021 15:58:00 GMT+02:00

        Date nowDate = new Date(1621260001000L); // Monday, 17 May 2021 16:00:01 GMT+02:00

        assertEquals(1, pollFirestore.checkIfAnyRecurringWateringShouldBeDone(recurringWateringList, latestCheckDate, nowDate).size());
    }

    @Test
    void shouldGiveEmptyListWhenRecurringDateIsNotYetInThePast() {
        List<RecurringWatering> recurringWateringList = new ArrayList<>();
        RecurringWatering recurringWatering = new RecurringWatering();

        List<String> days = new ArrayList<>();
        days.add("mon");

        recurringWatering.setDays(days);

        recurringWatering.setTime("16:00");

        recurringWateringList.add(recurringWatering);

        Date latestCheckDate = new Date(1621259880000L); //Monday, 17 May 2021 15:58:00 GMT+02:00

        Date nowDate = new Date(1621259999000L); //Monday, 17 May 2021 15:59:59 GMT+02:00

        assertEquals(0, pollFirestore.checkIfAnyRecurringWateringShouldBeDone(recurringWateringList, latestCheckDate, nowDate).size());
    }

    @Test
    public void shouldNotGiveRecurringWateringWhenDayIsWrong() {
        List<RecurringWatering> recurringWateringList = new ArrayList<>();
        RecurringWatering recurringWatering = new RecurringWatering();

        List<String> days = new ArrayList<>();
        days.add("wed");
        recurringWatering.setDays(days);
        recurringWatering.setTime("16:00");
        recurringWateringList.add(recurringWatering);

        Date latestCheckDate = new Date(1621259880000L); //Monday, 17 May 2021 15:58:00 GMT+02:00

        Date nowDate = new Date(1621260001000L); // Monday, 17 May 2021 16:00:01 GMT+02:00

        assertEquals(0, pollFirestore.checkIfAnyRecurringWateringShouldBeDone(recurringWateringList, latestCheckDate, nowDate).size());
    }
}