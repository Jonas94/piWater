package com.example.piwater.scheduling;

import com.example.piwater.config.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;


@EnableScheduling
@Component
public class Scheduler {

	private final TaskScheduler executor;
	private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

	@Autowired
	public Scheduler(TaskScheduler taskExecutor) {
		this.executor = taskExecutor;
	}


	public void scheduleActivity(final Runnable task, int duration) {
		// Schedule a task to run once at the given date (here in 1minute)
		LOG.info("TASK {} HAS BEEN SCHEDULED IN {} MINUTE!", task.getClass(), duration);
		executor.schedule(task, Date.from(LocalDateTime.now().plusMinutes(duration)
		                                               .atZone(ZoneId.systemDefault()).toInstant()));

		// Schedule a task with the given cron expression
		//		executor.schedule(task, new CronTrigger("*/5 * * * * MON-FRI"));
	}


}
