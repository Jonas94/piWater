package com.example.piwater.service.watering;

public class Watering {

	long startDate;
	long stopDate;
	int duration;


	public Watering() {
	}

	public Watering(long startDate, long stopDate, int duration) {
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.duration = duration;
	}


	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getStopDate() {
		return stopDate;
	}

	public void setStopDate(long stopDate) {
		this.stopDate = stopDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}


	@Override
	public String toString() {
		return "Watering{" +
				"startDate=" + startDate +
				", stopDate=" + stopDate +
				", duration=" + duration +
				'}';
	}
}
