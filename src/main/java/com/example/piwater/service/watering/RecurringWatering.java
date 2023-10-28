package com.example.piwater.service.watering;

import java.util.List;

public class RecurringWatering {

	String id;
	boolean active;
	List<String> days;
	String time;
	int duration;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public List<String> getDays() {
		return days;
	}

	public void setDays(List days) {
		this.days = days;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
