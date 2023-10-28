package com.example.piwater.scheduling;

import com.example.piwater.model.WaterSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeStateTask implements Runnable {
	//TODO: hide this class better, make sure it can never be used from the wrong place
	private static final Logger log = LoggerFactory.getLogger(ChangeStateTask.class);
	private boolean newState = true;

	private WaterSystem waterSystem;

	public ChangeStateTask(WaterSystem waterSystem, boolean newState) {
		this.waterSystem = waterSystem;
		this.newState = newState;
	}

	@Override
	public void run() {
		waterSystem.changeState(newState);
		log.info("Task has been executed! New state {}", newState);
	}


}
