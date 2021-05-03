package com.example.piwater.service;

import com.example.piwater.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;

@Service
public class WaterService {

	WaterSystem waterSystem;
	Scheduler scheduler;

	@Autowired
	public WaterService(WaterSystem waterSystem, Scheduler scheduler) {
		this.waterSystem = waterSystem;
		this.scheduler = scheduler;
	}

	public void enableWateringForDuration(WaterInput waterInput) throws IsBusyException {
		if (waterSystem.isBusy()) {
			throw new IsBusyException("The watering system is busy! Try again later.");
		}

		//TODO: Set start date and end date in a better way

		scheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem,true), waterInput.getStartDate());

		scheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem, false), waterInput.getStartDate().plusMinutes(waterInput.getMinutesToWater()));
	}

	public String changeState(boolean state) {
		return waterSystem.changeState(state);
	}
	public String getState() {
		return waterSystem.getState();
	}

}
