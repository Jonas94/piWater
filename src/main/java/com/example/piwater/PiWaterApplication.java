package com.example.piwater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.*;

@EnableScheduling
@SpringBootApplication
public class PiWaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiWaterApplication.class, args);
    }

    @Autowired
    Runnable MessageListener;

    @Bean
    public CommandLineRunner schedulingRunner(@Qualifier("applicationTaskExecutor") TaskExecutor executor) {
        return args -> executor.execute(MessageListener);

    }
}
