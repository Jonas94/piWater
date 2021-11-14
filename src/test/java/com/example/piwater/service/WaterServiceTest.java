package com.example.piwater.service;

import com.example.piwater.db.*;
import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;

import com.example.piwater.service.watering.WaterInput;
import com.example.piwater.service.watering.WaterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WaterServiceTest {

    @Mock
    private WaterSystem waterSystem;

    @Mock
    WaterScheduler waterScheduler;

    @Mock
    FirebaseConnectorWatering firebaseConnector;

    @InjectMocks
    WaterService waterService;

    @Test
    void testScheduleWatering() throws IsBusyException {
        WaterInput waterInput = new WaterInput(1, LocalDateTime.now());
        waterService.enableWateringForDuration(waterInput);
        verify(waterScheduler, times(2)).scheduleActivityWithDate(any(), any());
    }
}