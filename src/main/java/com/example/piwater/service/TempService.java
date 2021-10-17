package com.example.piwater.service;


import java.io.IOException;
import java.util.List;

public interface TempService {

    double getCurrentTemperature() throws IOException;
    List<String> getAllSensors() throws IOException;
}
