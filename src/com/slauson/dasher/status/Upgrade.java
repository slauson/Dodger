package com.slauson.dasher.status;

import android.content.SharedPreferences;
import android.content.res.Resources;

/**
 * Upgrade
 * @author Josh Slauson
 *
 */
public class Upgrade {
	
	private static int NUM_LEVELS = 5;
	
	private String key;
	private int level;
	
	private int[] titles;
	
	public Upgrade(String key) {
		this(key, 0);
	}
	
	public Upgrade(String key, int level) {
		this.key = key;
		this.level = level;
		
		// setup title resource ids
		titles = new int[NUM_LEVELS];
		
		for (int i = 0; i < NUM_LEVELS; i++) {
			titles[i] = 0;
		}
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
	
	/**
	 * Loads resource for titles based off key
	 */
	public void loadResources(Resources resources, String packageName) {

		// load resource ids titles
		for (int i = 0; i < NUM_LEVELS; i++) {
			titles[i] = resources.getIdentifier(key + "_" + i, "string", packageName);
			
			if (titles[i] == 0) {
				System.out.println("loadResources error: " + key + "_title_" + i);
			}
		}
	}
	
	/**
	 * Returns resource id of title for given upgrade level
	 * @param level upgrade level
	 * @return resource id of title for given upgrade level
	 */
	public int getTitleResourceId(int level) {
		if (level >= NUM_LEVELS) {
			return -1;
		}
		
		return titles[level];
	}
	
	@Override
	public String toString() { 
		return key + ": " + level;
	}
}
