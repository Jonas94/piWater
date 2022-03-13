package com.example.piwater.controller;

import com.example.piwater.model.Moisture;
import com.example.piwater.model.Temperature;
import com.example.piwater.service.moisture.MoistureService;
import com.example.piwater.service.temperature.TempService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/moisture")
public class MoistureController {

    MoistureService moistureService;

    public MoistureController(MoistureService moistureService) {
        this.moistureService = moistureService;
    }

    @GetMapping("/current")
    public ResponseEntity<List<Moisture>> getCurrent() throws IOException {
        return ResponseEntity.ok(moistureService.getCurrentMoistureValues());
    }
}
