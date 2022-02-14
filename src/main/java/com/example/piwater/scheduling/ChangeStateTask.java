package com.example.piwater.scheduling;

import com.example.piwater.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

public class ChangeStateTask implements Runnable {
	//TODO: hide this class better, make sure it can never be used from the wrong place
	private static final Logger log = LoggerFactory.getLogger(ChangeStateTask.class);
	private boolean newState = true;

	private WaterSystem waterSystem;

	@Autowired //TODO: remove?
	public ChangeStateTask(@Qualifier("waterSystem")WaterSystem waterSystem, boolean newState) {
		this.waterSystem = waterSystem;
		this.newState = newState;
	}

	@Override
	public void run() {
		waterSystem.changeState(newState);
		log.info("Task has been executed! New state {}", newState);

	}


}
