package com.example.piwater.service;

import com.example.piwater.db.FirebaseConnector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TempServiceTest {
    @Mock
    FirebaseConnector firebaseConnector;

    @InjectMocks
    TempService tempService;

    @Test
    void testGetCurrentTemperature() throws IOException {
        //When something return that

        assertEquals(99,tempService.getCurrentTemperature());

    }

}