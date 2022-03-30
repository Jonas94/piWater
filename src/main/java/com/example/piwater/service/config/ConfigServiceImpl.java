package com.example.piwater.service.config;

import com.example.piwater.db.FirebaseConnectorConfiguration;
import com.example.piwater.model.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    FirebaseConnectorConfiguration firebaseConnector;

    private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    public ConfigServiceImpl(FirebaseConnectorConfiguration firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    public Config getCurrentConfig() {
        return firebaseConnector.findCurrentConfig();
    }

    @Override
    public void saveConfig(Config config) {

        firebaseConnector.addDataToFirestore(config);
    }
}
