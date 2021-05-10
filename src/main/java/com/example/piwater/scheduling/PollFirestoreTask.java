package com.example.piwater.scheduling;

import org.slf4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Component
public class PollFirestoreTask {

	private static final Logger log = LoggerFactory.getLogger(PollFirestoreTask.class);


	@Scheduled(fixedRateString = "${fixed.rate.in.milliseconds}")
	public void pollFirebase() {
		//TODO: Poll firebase
		log.info("Polling firebase to find scheduled tasks");
	}
}
