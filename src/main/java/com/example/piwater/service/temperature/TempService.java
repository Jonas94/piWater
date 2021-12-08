package com.example.piwater.service.temperature;


import com.example.piwater.model.Temperature;

import java.io.IOException;
import java.util.List;

public interface TempService {

    Temperature getCurrentTemperature() throws IOException;
    List<String> getAllSensors() throws IOException;

    void saveCurrentTemp() throws IOException;
}
