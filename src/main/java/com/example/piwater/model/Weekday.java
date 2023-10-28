package com.example.piwater.model;

import java.util.HashMap;
import java.util.Map;

public enum Weekday {

	SUN(1),
	MON(2),
	TUE(3),
	WED (4),
	THU (5),
	FRI (6),
	SAT (7);


	private final int weekdayCode;
	private static final Map<Integer, Weekday> map = new HashMap<>();

	Weekday(int weekdayCode) {
		this.weekdayCode = weekdayCode;
	}

	static {
		for (Weekday weekday : Weekday.values()) {
			map.put(weekday.weekdayCode, weekday);
		}
	}

	public static Weekday valueOf(int weekdayCode) {
		return map.get(weekdayCode);
	}

	public int getWeekdayCode() {
		return this.weekdayCode;
	}

}

