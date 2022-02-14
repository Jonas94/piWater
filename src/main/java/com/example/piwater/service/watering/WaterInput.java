package com.example.piwater.service.watering;

import org.springframework.format.annotation.*;

import java.time.*;
import java.util.*;

public class WaterInput {
	private int minutesToWater;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDate;
	private Date stopDate;

	public WaterInput(int minutesToWater) {
		this.minutesToWater = minutesToWater;
		this.startDate = LocalDateTime.now();
	}

	public WaterInput(int minutesToWater, LocalDateTime startDate) {
		this.minutesToWater = minutesToWater;
		this.startDate = startDate;
	}

	public int getMinutesToWater() {
		return minutesToWater;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public long getStartDateAsLong(){
		return startDate.atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli();
	}

	public long getStopDateAsLong() {
		return startDate.plusMinutes(getMinutesToWater()).atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli();
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
}
