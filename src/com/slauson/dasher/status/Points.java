package com.slauson.dasher.status;

import android.content.SharedPreferences;

public class Points {

	// public constants
	public static final int POINTS_TIME_PLAYED = 10;
	public static final int POINTS_ASTEROIDS_DESTROYED = 10;
	public static final int POINTS_ACHIEVEMENT = 1000;
	
	private static int points;
	private static boolean initialized = false;
	
	private static final String POINTS = "points_points";
	
	/**
	 * Loads statistics from preferences
	 * @param sharedPreferences preferences to load from
	 */
	public static void load(SharedPreferences sharedPreferences) {
		points = sharedPreferences.getInt(POINTS, 0);
		initialized = true;
	}
	
	/**
	 * Saves statistics to preferences
	 * @param sharedPreferencesEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor sharedPreferencesEditor) {
		sharedPreferencesEditor.putInt(POINTS, points);
	}
	
	/**
	 * Returns true if points were initialized from application preferences
	 * @return true if points were initialized from application preferences
	 */
	public static boolean initialized() {
		return initialized;
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

	/**
	 * Resets points to 0
	 * @param preferencesEditor preferences to save to
	 */
	public static void reset(SharedPreferences.Editor sharedPreferencesEditor) {
		points = 0;
		save(sharedPreferencesEditor);
	}
}
