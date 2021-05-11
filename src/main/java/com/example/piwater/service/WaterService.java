package com.example.piwater.service;

import com.example.piwater.db.*;
import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import org.slf4j.*;
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
	private static final Logger log = LoggerFactory.getLogger(WaterService.class);

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

		if(waterInput.getStartDate().isBefore(LocalDateTime.now())){
			waterInput.setStartDate(LocalDateTime.now());
		}

		try {
			firebaseConnector.addDataToFirestore(waterInput);
			scheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem,true), waterInput.getStartDate());
			scheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem, false), waterInput.getStartDate().plusMinutes(waterInput.getMinutesToWater()));

		} catch (ExecutionException | InterruptedException e) {
			//TODO: LOG ISSUE AND SHOW IT
		}
	}


	public void getAllWaterings() {

		try {
			firebaseConnector.findAllWaterings();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void getFutureWaterings() {

		try {
			firebaseConnector.findFutureWaterings();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String stopWatering() {
		log.info("Manual stop triggered!");
		return waterSystem.changeState(false);
	}

	public String getState() {
		return waterSystem.getState();
	}

}
