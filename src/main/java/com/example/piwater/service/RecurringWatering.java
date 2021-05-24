package com.example.piwater.service;

import java.util.*;

public class RecurringWatering {

	boolean active;
	List<String> days;
	List<String> times;
	long from;
	long to;
	int duration;

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

	public List<String> getTimes() {
		return times;
	}

	public void setTimes(List times) {
		this.times = times;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
