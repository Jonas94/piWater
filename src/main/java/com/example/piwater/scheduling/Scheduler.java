package com.example.piwater.scheduling;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Component
public class Scheduler {

	private final TaskScheduler executor;
	private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

	@Autowired
	public Scheduler(TaskScheduler taskExecutor) {
		this.executor = taskExecutor;
	}


	public void scheduleActivityWithDelayInMinutes(final Runnable task, int duration) {
		//Schedules from now + duration in minutes
		LOG.info("TASK {} HAS BEEN SCHEDULED IN {} MINUTE!", task.getClass().getName(), duration);
		executor.schedule(task, Date.from(LocalDateTime.now().plusMinutes(duration)
		                                               .atZone(ZoneId.systemDefault()).toInstant()));

	}

	public void scheduleActivityWithDate(final Runnable task, LocalDateTime localDateTime) {
		//Schedules from now + duration in minutes
		LOG.info("TASK {} HAS BEEN SCHEDULED AT TIME {}", task.getClass().getName(), localDateTime.toString());
		executor.schedule(task, Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

	}
}
