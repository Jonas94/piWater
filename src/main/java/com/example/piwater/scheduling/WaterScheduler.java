package com.example.piwater.scheduling;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Component
public class WaterScheduler {

	private final TaskScheduler executor;
	private static final Logger LOG = LoggerFactory.getLogger(WaterScheduler.class);

	@Autowired
	public WaterScheduler(TaskScheduler taskExecutor) {
		this.executor = taskExecutor;
	}

	public void scheduleActivityWithDate(final Runnable task, LocalDateTime localDateTime) {
		//Schedules from now + duration in minutes
		LOG.info("TASK {} HAS BEEN SCHEDULED AT TIME {}", task.getClass().getName(), localDateTime);
		executor.schedule(task, Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

	}
}
