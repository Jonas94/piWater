package com.example.piwater.service;

import java.util.*;

public class RecurringWatering {

	boolean active;
	List<String> day;
	List<String> time;
	long from;
	long to;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public List<String> getDay() {
		return day;
	}

	public void setDay(List day) {
		this.day = day;
	}

	public List<String> getTime() {
		return time;
	}

	public void setTime(List time) {
		this.time = time;
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
}
