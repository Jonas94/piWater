package com.example.piwater.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sensor {
    private String name;
    private Double value;
    String type;
}
