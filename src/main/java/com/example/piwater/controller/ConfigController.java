package com.example.piwater.controller;

import com.example.piwater.model.Config;
import com.example.piwater.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {

    ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping
    public ResponseEntity<Config> getCurrent() throws IOException {
        return ResponseEntity.ok(configService.getCurrentConfig());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCurrentConfig(@RequestBody Config config) throws IOException {
        configService.saveConfig(config);
        return ResponseEntity.ok("Det gick bra");
    }

}
