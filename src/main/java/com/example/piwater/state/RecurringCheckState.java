package com.example.piwater.state;

import org.springframework.stereotype.*;

@Component
public class RecurringCheckState {

	long latestCheckTime;

	public long getLatestCheckTime() {
		return latestCheckTime;
	}

	public void setLatestCheckTime(long latestCheckTime) {
		this.latestCheckTime = latestCheckTime;
	}
}
