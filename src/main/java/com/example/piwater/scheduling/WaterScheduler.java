package com.example.piwater.scheduling;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class WaterScheduler {

	private final TaskScheduler executor;
	private static final Logger LOG = LoggerFactory.getLogger(WaterScheduler.class);

	public void scheduleActivityWithDate(final Runnable task, LocalDateTime localDateTime) {
		//Schedules from now + duration in minutes
		LOG.info("TASK {} HAS BEEN SCHEDULED AT TIME {}", task.getClass().getName(), localDateTime);
		executor.schedule(task, Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

	}
}
