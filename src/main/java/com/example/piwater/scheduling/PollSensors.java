package com.example.piwater.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class PollSensors implements SchedulingConfigurer {
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

    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                () -> {
                    //String sensorTopic = (String) userSettings.getOrDefault(Keys.SENSOR_TOPIC, "topic1");
                    String sensorTopic = "topic";
                    log.info("Polling ESP32 to get current state of sensors on topic: {}", sensorTopic);
                    jmsTemplate.send(sensorTopic, session -> session.createTextMessage("on"));
                },
                triggerContext -> {
                    Calendar nextExecutionTime = new GregorianCalendar();
                    Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                    nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                    // nextExecutionTime.add(Calendar.MILLISECOND, env.getProperty("myRate", Integer.class)); //you can get the value from wherever you want
                    nextExecutionTime.add(Calendar.MILLISECOND, 120000); //you can get the value from wherever you want
                    //TODO: Fix this dynamically

                    return nextExecutionTime.getTime();
                }
        );
    }
}
