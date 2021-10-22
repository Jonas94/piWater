package com.example.piwater.controller;

import com.example.piwater.service.temperature.TempService;
import com.example.piwater.service.watering.Watering;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/temp")
public class TempController {

    TempService tempService;

    public TempController(TempService tempService) {
        this.tempService = tempService;
    }

    @GetMapping("/current")
    public ResponseEntity<Double> getCurrent() throws IOException {
        return ResponseEntity.ok(tempService.getCurrentTemperature());
    }

    @GetMapping("/historical")
    public ResponseEntity<Watering> getCurrentWatering() {
        //TODO: Implement
        throw new NotImplementedException();
        //return ResponseEntity.ok().build();
    }

    @GetMapping("/sensors")
    public ResponseEntity<List<String>> getAllSensors() throws IOException {
        return ResponseEntity.ok(tempService.getAllSensors());
    }


    //TODO: Forecast stuff

}
