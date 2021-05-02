package com.example.piwater.service;

import java.util.*;

public class WaterInput {
	private int minutesToWater;
	private Date startDate;


	public WaterInput(int minutesToWater, Date startDate) {
		this.minutesToWater = minutesToWater;
		this.startDate = startDate;
	}

	public int getMinutesToWater() {
		return minutesToWater;
	}

	public Date getStartDate() {
		return startDate;
	}
}
