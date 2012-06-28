package com.slauson.dasher.main;

import android.content.SharedPreferences;

/**
 * Upgrade
 * @author Josh Slauson
 *
 */
public class Upgrade {
	private String key;
	private int level;
	
	public Upgrade(String key) {
		this.key = key;
		this.level = 0;
	}
	
	public Upgrade(String key, int level) {
		this.key = key;
		this.level = level;
	}
	
	/**
	 * Loads upgrade level from application preferences
	 * @param preferences preferences to load from
	 */
	public void load(SharedPreferences preferences) {
		level = preferences.getInt(key, 0);
	}
	
	/**
	 * Saves upgrade level to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor) {
		preferencesEditor.putInt(key, level);
	}
	
	/**
	 * Returns level of upgrade
	 * @return level of upgrade
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Sets level of upgrade
	 * @param level level of upgrade
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
