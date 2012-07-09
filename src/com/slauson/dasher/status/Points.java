package com.slauson.dasher.status;

import android.content.SharedPreferences;

public class Points {

	// public constants
	public static final int POINTS_TIME_PLAYED = 10;
	public static final int POINTS_ASTEROIDS_DESTROYED = 10;
	public static final int POINTS_ACHIEVEMENT = 1000;
	
	private static int points;
	
	private static final String POINTS = "points_points";
	
	/**
	 * Loads statistics from preferences
	 * @param preferences preferences to load from
	 */
	public static void load(SharedPreferences preferences) {
		points = preferences.getInt(POINTS, 0);
	}
	
	/**
	 * Saves statistics to preferences
	 * @param preferences preferences to save to
	 */
	public static void save(SharedPreferences.Editor preferencesEditor) {
		preferencesEditor.putInt(POINTS, points);
	}
	
	/**
	 * Update points
	 */
	public static void update(int change) {
		points += change;
	}

	/**
	 * Returns number of points
	 * @return number of points
	 */
	public static int getNumPoints() {
		return points;
	}
}
