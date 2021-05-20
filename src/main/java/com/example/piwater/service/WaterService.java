package com.example.piwater.service;

import com.example.piwater.db.*;
import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.scheduling.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

@Service
public class WaterService {

	WaterSystem waterSystem;
	WaterScheduler waterScheduler;
	FirebaseConnector firebaseConnector;
	private static final Logger log = LoggerFactory.getLogger(WaterService.class);

	@Autowired
	public WaterService(WaterSystem waterSystem, WaterScheduler waterScheduler, FirebaseConnector firebaseConnector) {
		this.waterSystem = waterSystem;
		this.waterScheduler = waterScheduler;
		this.firebaseConnector = firebaseConnector;
	}

	public void enableWateringForDuration(WaterInput waterInput) throws IsBusyException {
		if (waterSystem.isBusy()) {
			throw new IsBusyException("The watering system is busy! Try again later.");
		}

		if(waterInput.getStartDate().isBefore(LocalDateTime.now())){
			waterInput.setStartDate(LocalDateTime.now());
		}

		firebaseConnector.addDataToFirestore(waterInput);
		waterScheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem, true), waterInput.getStartDate());
		waterScheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem, false), waterInput.getStartDate().plusMinutes(waterInput.getMinutesToWater()));

	}


	public List<Watering> getAllWaterings() {
		try {
			return firebaseConnector.findAllWaterings();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<Watering> getFutureWaterings() {
		try {
			return firebaseConnector.findFutureWaterings();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<RecurringWatering> getAllRecurringWaterings() {
		try {
			return firebaseConnector.findAllRecurringWaterings();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	public List<RecurringWatering> getActiveRecurringWaterings() {
		try {
			return firebaseConnector.findActiveRecurringWaterings();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	public WaterState stopWatering() {
		log.info("Manual stop triggered!");
		return waterSystem.changeState(false);
	}

	public WaterState getState() {
		return waterSystem.getState();
	}

}
