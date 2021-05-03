package com.example.piwater.scheduling;

import com.example.piwater.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

public class ChangeStateTask implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(ChangeStateTask.class);
	private boolean newState = true;

	private WaterSystem waterSystem;

	@Autowired
	public ChangeStateTask(@Qualifier("waterSystem")WaterSystem waterSystem, boolean newState) {
		this.waterSystem = waterSystem;
		this.newState = newState;
	}

	@Override
	public void run() {
		waterSystem.changeState(newState);
		LOG.info("Task has been executed! New state {}", newState);

	}


}
