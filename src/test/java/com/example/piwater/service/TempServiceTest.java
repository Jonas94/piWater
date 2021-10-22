package com.example.piwater.service;

import com.example.piwater.db.FirebaseConnectorTemperature;
import com.example.piwater.service.temperature.TempServiceImpl;
import com.example.piwater.utils.SensorHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TempServiceTest {
    @Mock
    FirebaseConnectorTemperature firebaseConnector;

    @Mock
    SensorHelper sensorHelper;

    @InjectMocks
    TempServiceImpl tempService;

    @Captor
    ArgumentCaptor<String> tempCaptor;

    private static final String MOCKED_SENSOR = "MOCKED_SENSOR_1";

    @Test
    void testGetCurrentTemperature() throws IOException {
        when(sensorHelper.getSensorNames()).thenReturn(List.of(MOCKED_SENSOR));
        when(sensorHelper.getTemperatureForSensor(MOCKED_SENSOR)).thenReturn(99.0);
        assertEquals(99, tempService.getCurrentTemperature());
    }

    @Test
    void testSaveCurrentTemperature() throws IOException {
        when(sensorHelper.getSensorNames()).thenReturn(List.of(MOCKED_SENSOR));
        when(sensorHelper.getTemperatureForSensor(MOCKED_SENSOR)).thenReturn(99.0);

      //  tempService.saveCurrentTemperature();

        Mockito.verify(firebaseConnector).addDataToFirestore(tempCaptor.capture());

//        tempCaptor.capture()

    }



}