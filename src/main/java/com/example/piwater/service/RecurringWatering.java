package com.example.piwater.service;

import java.util.*;

public class RecurringWatering {

	boolean active;
	List<String> day;
	List<String> time;

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
}
