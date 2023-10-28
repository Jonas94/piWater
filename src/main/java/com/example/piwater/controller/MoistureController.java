package com.example.piwater.controller;

import com.example.piwater.model.Moisture;
import com.example.piwater.service.moisture.MoistureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
