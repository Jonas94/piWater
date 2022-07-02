package com.example.piwater.scheduling;

import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.scheduling.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

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
