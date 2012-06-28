package com.slauson.dasher.main;

import android.content.SharedPreferences;

/**
 * Represents an individual achievement
 * @author Josh Slauson
 *
 */
public class Achievement {

	private String key;
	private boolean value;
	
	public Achievement(String key) {
		this.key = key;
		
		value = false;
	}
	
	/**
	 * Loads achievement value from application preferences
	 * @param preferences preferences to load from
	 */
	public void load(SharedPreferences preferences) {
		value = preferences.getBoolean(key, false);
	}
	
	/**
	 * Saves achievement value to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor) {
		preferencesEditor.putBoolean(key, value);
	}
	
	/**
	 * Returns value of achievement
	 * @return value of achievement
	 */
	public boolean getValue() {
		return value;
	}
	
	/**
	 * Sets value of achievement
	 * @param value value of achievement
	 */
	public void setValue(boolean value) {
		this.value = value;
	}
}
