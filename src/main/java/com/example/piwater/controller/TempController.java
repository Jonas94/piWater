package com.example.piwater.controller;

import com.example.piwater.model.Temperature;
import com.example.piwater.service.temperature.TempService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
//@RestController
//@RequestMapping("/temp")
public class TempController {

    TempService tempService;

    public TempController(TempService tempService) {
        this.tempService = tempService;
    }

    @GetMapping("/current")
    public ResponseEntity<Temperature> getCurrent() throws IOException {
        return ResponseEntity.ok(tempService.getCurrentTemperature());
    }

    @GetMapping("/historical/{sinceDate}")
    public ResponseEntity<List<Temperature>> getHistoricalTemperatures(@RequestParam(value = "sinceDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                   LocalDateTime sinceDate) throws IOException {
        if (sinceDate == null) {
            sinceDate = LocalDateTime.now().minusDays(5);
        }
        return ResponseEntity.ok(tempService.getHistoricalTemperatures(sinceDate));
    }

    @GetMapping("/sensors")
    public ResponseEntity<List<String>> getAllSensors() throws IOException {
        return ResponseEntity.ok(tempService.getAllSensors());
    }

    @GetMapping("/save") //Temporary endpoint
    public ResponseEntity<String> saveCurrentTemp() throws IOException {
        tempService.saveCurrentTemp();

        return ResponseEntity.ok("Det gick bra");
    }


    //TODO: Forecast stuff

}
