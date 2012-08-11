package com.slauson.dasher.status;


public class LocalStatistics {
	private static Statistics statistics = new Statistics();
	
	public static Statistics getInstance() {
		return statistics;
	}
}
