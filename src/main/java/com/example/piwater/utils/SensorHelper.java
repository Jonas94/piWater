package com.example.piwater.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SensorHelper {

    /**
     * The base directory for the device data
     */
    public static final String BASE_DIR = "/sys/bus/w1/devices/";

    /**
     * The file the sensor data is stored in
     */
    public static final String SENSOR_FILE = "/w1_slave";

    /**
     * DS18B20 sensor folders start with 28 followed by the unique ID
     */
    public static final String DEVICE_INITIAL_IDENTIFIER = "28";

    /**
     * How frequently to log the temperature
     */
    public static final int LOG_INTERVAL = 5;

    /**
     * Get the available sensor names
     *
     * @return
     * @throws IOException
     */
    public List<String> getSensorNames() throws IOException {

        File deviceFolder = new File(BASE_DIR);

        if (!deviceFolder.exists()) {
            throw new IOException("The base directory does not exist");
        }

        List<String> names = new ArrayList<>();

        for (File file : Objects.requireNonNull(deviceFolder.listFiles())) {
            if (file.isDirectory() && file.getName().startsWith(DEVICE_INITIAL_IDENTIFIER)) {
                names.add(file.getName());
            }
        }

        return names;
    }

    public double getTemperatureForSensor(String name) throws IOException {
        File deviceFile = new File(BASE_DIR + name + SENSOR_FILE);

        return readTemp(deviceFile);
    }

    private double readTemp(File deviceFile) throws IOException {
        List<String> list = Files.readAllLines(deviceFile.toPath(), StandardCharsets.UTF_8);

        return parseTempFromLine(list.get(1));
    }

    private double parseTempFromLine(String string) {

        String temp = string.split("t=")[1];

        return Double.valueOf(temp) / 1000d;
    }

    public List<Double> getTemperaturesForSensors(List<String> sensorNames) throws IOException {
        List<Double> temps = new ArrayList<>();

        for (String name : sensorNames) {
            temps.add(getTemperatureForSensor(name));
        }
        return temps;
    }


}
