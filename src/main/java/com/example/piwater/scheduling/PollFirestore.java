package com.example.piwater.scheduling;

import com.example.piwater.db.*;
import com.example.piwater.service.*;
import com.example.piwater.state.*;
import org.slf4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.util.*;
import java.util.concurrent.*;

@Component
public class PollFirestore {

	private static final Logger log = LoggerFactory.getLogger(PollFirestore.class);

	RecurringCheckState recurringCheckState;

	FirebaseConnector firebaseConnector;

	@Scheduled(fixedRateString = "${fixed.rate.in.milliseconds}")
	public void pollFirebase() throws ExecutionException, InterruptedException {
		//TODO: Poll firebase
		log.info("Polling firebase to find scheduled tasks");

		List<RecurringWatering> recurringWaterings = firebaseConnector.findActiveRecurringWaterings();

		List<RecurringWatering> recurringWateringsToDoRightNow = checkIfAnyRecurringWateringShouldBeDone(recurringWaterings,
		                                                                                                new Date(recurringCheckState.getLatestCheckTime()), new Date());


	}


	public List<RecurringWatering> checkIfAnyRecurringWateringShouldBeDone(List<RecurringWatering> recurringWaterings, Date latestCheckDate, Date nowDate) {


		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);

		Calendar latestCheckDateCal = Calendar.getInstance();
		latestCheckDateCal.setTime(latestCheckDate);

		System.out.println(latestCheckDateCal.get(Calendar.HOUR_OF_DAY));
		System.out.println(latestCheckDateCal.get(Calendar.MINUTE));

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
				// Invalid date was entered
				//TODO: Log something or send error
			}
			Calendar recurringWateringDate = Calendar.getInstance();

			for (Date date : parsedDates) {

				recurringWateringDate.setTime(date);
				recurringWateringDate.set(Calendar.DAY_OF_MONTH, latestCheckDateCal.get(Calendar.DAY_OF_MONTH));
				recurringWateringDate.set(Calendar.MONTH, latestCheckDateCal.get(Calendar.MONTH));
				recurringWateringDate.set(Calendar.YEAR, latestCheckDateCal.get(Calendar.YEAR));


				if (recurringWateringDate.after(latestCheckDateCal) && recurringWateringDate.before(now)) {

					log.info("recurringWateringDate is before 'now' and  after latestcheckdate which means go for it");
					recurringWateringsToDoRightNow.add(recurringWatering);

				}

			}
		}

		return recurringWateringsToDoRightNow;
	}

}