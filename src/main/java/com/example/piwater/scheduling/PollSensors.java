package com.example.piwater.scheduling;

import com.example.piwater.constants.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class PollSensors {
    private static final Logger log = LoggerFactory.getLogger(PollSensors.class);
    private final JmsTemplate jmsTemplate;
    private final TaskScheduler executor;

    @Resource(name = "userSettings")
    private Map<String, Object> userSettings;

    @Autowired
    public PollSensors(JmsTemplate publisher, TaskScheduler taskExecutor) {
        this.jmsTemplate = publisher;
        this.executor = taskExecutor;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onSchedule() {
        schedulePolling(new PollSensorsTask(userSettings, jmsTemplate));
    }
    public void schedulePolling(final Runnable task) {
        log.info("Sensor polling will be scheduled");
        executor.scheduleWithFixedDelay(task, (int) userSettings.getOrDefault("sensorCheckTime", 120000)
);
    }

}
