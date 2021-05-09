package com.example.piwater.service;

import com.example.piwater.*;
import com.example.piwater.db.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.time.*;
import java.util.concurrent.*;

@Service
public class WaterService {

	WaterSystem waterSystem;
	Scheduler scheduler;
	FirebaseConnector firebaseConnector;

	@Autowired
	public WaterService(WaterSystem waterSystem, Scheduler scheduler, FirebaseConnector firebaseConnector) {
		this.waterSystem = waterSystem;
		this.scheduler = scheduler;
		this.firebaseConnector = firebaseConnector;
	}

	public void enableWateringForDuration(WaterInput waterInput) throws IsBusyException {
		if (waterSystem.isBusy()) {
			throw new IsBusyException("The watering system is busy! Try again later.");
		}

		//TODO: Set start date and end date in a better way

		if(waterInput.getStartDate().isBefore(LocalDateTime.now())){
			waterInput.setStartDate(LocalDateTime.now());
		}

		try {
			firebaseConnector.addDataToFirestore(waterInput);
			scheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem,true), waterInput.getStartDate());
			scheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem, false), waterInput.getStartDate().plusMinutes(waterInput.getMinutesToWater()));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void enableWateringNowForDuration(WaterInput waterInput) throws IsBusyException {
		//TODO: Delete this? Should be enough with the other endpoint
		if (waterSystem.isBusy()) {
			throw new IsBusyException("The watering system is busy! Try again later.");
		}

		//TODO: Set start date and end date in a better way


		scheduler.scheduleActivityWithDelayInMinutes(new ChangeStateTask(waterSystem,true), 0);

		scheduler.scheduleActivityWithDelayInMinutes(new ChangeStateTask(waterSystem,false), waterInput.getMinutesToWater());

	}



	public String changeState(boolean state) {
		return waterSystem.changeState(state);
	}
	public String getState() {
		return waterSystem.getState();
	}

}
