package com.example.piwater.service.config;

import com.example.piwater.model.Config;

import java.io.IOException;

public interface ConfigService {

    Config getCurrentConfig() throws IOException;

    void saveConfig(Config config) throws IOException;
}
