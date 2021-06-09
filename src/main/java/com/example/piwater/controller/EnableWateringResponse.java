package com.example.piwater.controller;

public class EnableWateringResponse {

	private long startDate;
	private int duration;

	public EnableWateringResponse() {
	}

	public EnableWateringResponse(long startDate, int duration) {
		this.startDate = startDate;
		this.duration = duration;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
