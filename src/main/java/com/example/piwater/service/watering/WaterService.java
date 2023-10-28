package com.example.piwater.service.watering;

import com.example.piwater.db.FirebaseConnectorWatering;
import com.example.piwater.exception.IsBusyException;
import com.example.piwater.model.WaterState;
import com.example.piwater.model.WaterSystem;
import com.example.piwater.scheduling.ChangeStateTask;
import com.example.piwater.scheduling.WaterScheduler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class WaterService {

	private final WaterSystem waterSystem;
	private final WaterScheduler waterScheduler;
	private final FirebaseConnectorWatering firebaseConnector;
	private static final Logger log = LoggerFactory.getLogger(WaterService.class);

	public void enableWateringForDuration(WaterInput waterInput) throws IsBusyException {
		if (waterSystem.isBusy()) {
			throw new IsBusyException("The watering system is busy! Try again later.");
		}

		if(waterInput.getStartDate() == null || waterInput.getStartDate().isBefore(LocalDateTime.now())){
			waterInput.setStartDate(LocalDateTime.now());
		}

		firebaseConnector.addDataToFirestore(waterInput);
		waterScheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem, true), waterInput.getStartDate());
		waterScheduler.scheduleActivityWithDate(new ChangeStateTask(waterSystem, false), waterInput.getStartDate().plusMinutes(waterInput.getMinutesToWater()));

	}


	public List<Watering> getAllWaterings() {
		try {
			return firebaseConnector.findAllWaterings();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<Watering> getFutureWaterings() {
		try {
			return firebaseConnector.findFutureWaterings();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public void saveRecurringWater(RecurringWatering recurringWatering) {
		firebaseConnector.updateRecurringWateringToFirestore(recurringWatering);
	}


	public List<RecurringWatering> getAllRecurringWaterings() {
		try {
			return firebaseConnector.findAllRecurringWaterings();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	public List<RecurringWatering> getActiveRecurringWaterings() {
		try {
			return firebaseConnector.findActiveRecurringWaterings();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public WaterState stopWatering() {
		log.info("Manual stop triggered!");
		return waterSystem.changeState(false);
	}

	public Watering getCurrentWatering() {
		List<Watering> possiblyOngoingWaterings;
		List<Watering> ongoingWaterings = new ArrayList<>();
		try {
			possiblyOngoingWaterings = firebaseConnector.findPossiblyOngoingWaterings();
			ongoingWaterings = possiblyOngoingWaterings.stream().filter(watering -> watering.startDate<= new Date().getTime()).toList();

		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		if(ongoingWaterings.isEmpty() || !getState().isOn()){
			return null;
		}
		return ongoingWaterings.get(0);
	}

	public WaterState getState() {
		return waterSystem.getState();
	}

}
