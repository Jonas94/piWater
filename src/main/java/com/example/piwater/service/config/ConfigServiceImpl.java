package com.example.piwater.service.config;

import com.example.piwater.db.FirebaseConnectorConfiguration;
import com.example.piwater.model.Config;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.WriteResult;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

    FirebaseConnectorConfiguration firebaseConnector;

    @Resource(name = "userSettings")
    private Map<String, Object> userSettings;

    private static final String AUTO_WATERING_ENABLED = "autoWateringEnabled";
    private static final String DEFAULT_WATERING_MINUTES = "defaultWateringMinutes";
    private static final String MOISTURE_THRESHOLD = "moistureThreshold";

    private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    public ConfigServiceImpl(FirebaseConnectorConfiguration firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    public Config getCurrentConfig() {
        return firebaseConnector.findCurrentConfig();
    }

    @Override
    public void reloadConfig() {
        log.info("Reloading config from firestore");
        loadConfigFromFirestore();
    }


    private void loadConfigFromFirestore() {
        Config config = getCurrentConfig();

        userSettings.clear();
        userSettings.put(AUTO_WATERING_ENABLED, config.isAutoWateringEnabled());
        userSettings.put(DEFAULT_WATERING_MINUTES, config.getDefaultWateringMinutes());
        userSettings.put(MOISTURE_THRESHOLD, config.getMoistureThreshold());

    }


    @Override
    public void saveConfig(Config config) {

        log.info("Saving config data to firestore: {}", config);
        ApiFuture<WriteResult> result = firebaseConnector.addDataToFirestore(config);

        ApiFutures.addCallback(result, new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("Data adding failed! {}", throwable.getMessage());
            }

            @Override
            public void onSuccess(WriteResult writeResult) {
                log.info("Config data was added with success!");
                reloadConfig();
            }

        }, MoreExecutors.directExecutor());
    }
}
