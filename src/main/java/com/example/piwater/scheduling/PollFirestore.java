package com.example.piwater.scheduling;

import com.example.piwater.db.*;
import com.example.piwater.exception.*;
import com.example.piwater.model.*;
import com.example.piwater.service.*;
import com.example.piwater.state.*;
import org.slf4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

@Component
public class PollFirestore {

	private static final Logger log = LoggerFactory.getLogger(PollFirestore.class);

	RecurringCheckState recurringCheckState;
	FirebaseConnector firebaseConnector;
	WaterService waterService;

	public PollFirestore(RecurringCheckState recurringCheckState, FirebaseConnector firebaseConnector, WaterService waterService) {
		this.recurringCheckState = recurringCheckState;
		this.firebaseConnector = firebaseConnector;
		this.waterService = waterService;
	}

	@Scheduled(fixedRateString = "${fixed.rate.in.milliseconds}")
	public void pollFirebase() throws ExecutionException, InterruptedException {
		//TODO: Poll firebase
		log.info("Polling firebase to find scheduled tasks");

		List<RecurringWatering> recurringWaterings = firebaseConnector.findActiveRecurringWaterings();

		List<RecurringWatering> recurringWateringsToDoRightNow = checkIfAnyRecurringWateringShouldBeDone(recurringWaterings,
		                                                                                                new Date(recurringCheckState.getLatestCheckTime()), new Date());

		if(!recurringWateringsToDoRightNow.isEmpty()){

			for(RecurringWatering recurringWatering : recurringWateringsToDoRightNow) {
				//Currently only one watering is supported at the time, but could be useful in the future
				try {
					waterService.enableWateringForDuration(new WaterInput(recurringWatering.getDuration(), LocalDateTime.now()));
				} catch (IsBusyException e) {
					log.error("Failed to start recurring watering. ", e);
				}
			}
		}
	}


	public List<RecurringWatering> checkIfAnyRecurringWateringShouldBeDone(List<RecurringWatering> recurringWaterings, Date latestCheckDate, Date nowDate) {
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);

		Calendar latestCheckDateCal = Calendar.getInstance();
		latestCheckDateCal.setTime(latestCheckDate);

		List<RecurringWatering> recurringWateringsToDoRightNow = new ArrayList<>();

		for (RecurringWatering recurringWatering : recurringWaterings) {
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			List<Date> parsedDates = new ArrayList<>();

			try {
				List<String> plannedTimestamps = recurringWatering.getTime();

				for (String plannedTimestamp : plannedTimestamps) {
					Date recurringTime = parser.parse(plannedTimestamp);
					parsedDates.add(recurringTime);
				}

			} catch (ParseException e) {
				log.info("Was not able to parse date", e);
			}
			Calendar recurringWateringDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Date date : parsedDates) {

				recurringWateringDate.setTime(date);
				recurringWateringDate.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
				recurringWateringDate.set(Calendar.MONTH, now.get(Calendar.MONTH));
				recurringWateringDate.set(Calendar.YEAR, now.get(Calendar.YEAR));


				if (recurringWateringDate.after(latestCheckDateCal) && recurringWateringDate.before(now)) {

					log.info("recurringWateringDate {} is before 'now' ({}) and  after latestcheckdate {} which means go for it",
					         formatter.format(recurringWateringDate.getTime()), formatter.format(nowDate), formatter.format(latestCheckDate));
					recurringWateringsToDoRightNow.add(recurringWatering);
				}
				else{
					log.info("recurringWateringDate {} 'now' ({}) latestcheckdate {} which means no",
					         formatter.format(recurringWateringDate.getTime()), formatter.format(nowDate), formatter.format(latestCheckDate));
				}

			}
		}

		recurringCheckState.setLatestCheckTime(new Date().getTime());

		return recurringWateringsToDoRightNow;
	}

}