package com.slauson.asteroid_dasher.status;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.slauson.asteroid_dasher.other.Util;

/**
 * Represents an individual achievement
 * @author Josh Slauson
 *
 */
public class Achievement {

	// key prefix to save value/time, also used for resource names of title, description
	private String key;
	
	// value of achievement (locked/unlocked)
	private boolean value;
	
	// time achievement was unlocked
	private long time;
	
	// resource ids of icon, title, description 
	private int idIcon;
	private int idTitle;
	private int idDescription;
	
	// number of something required for achievement, this is inserted into description using %s
	private int num;
	
	// id of statistic associated with this achievement
	private int statistic;
	
	// description
	private String description;
	
	public Achievement(String key, int idIcon) {
		this(key, idIcon, -1, -1);
	}
	
	public Achievement(String key, int idIcon, int num) {
		this(key, idIcon, num, -1);
	}

	
	public Achievement(String key, int idIcon, int num, int statistic) {
		this.key = key;
		this.idIcon = idIcon;
		this.num = num;
		this.statistic = statistic;
	
		this.value = false;
		this.time = 0;
		
		this.idTitle = 0;
		this.idDescription = 0;
		
		this.description = "";
	}
	
	/**
	 * Loads achievement value from application preferences
	 * @param preferences preferences to load from
	 */
	public void load(SharedPreferences preferences) {
		value = preferences.getBoolean(key + "_value", false);
		time = preferences.getLong(key + "_time", 0);
	}
	
	/**
	 * Saves achievement value to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor) {
		preferencesEditor.putBoolean(key + "_value", value);
		preferencesEditor.putLong(key + "_time", time);
	}
	
	/**
	 * Returns value of achievement
	 * @return value of achievement
	 */
	public boolean getValue() {
		return value;
	}
	
	/**
	 * Returns time achievement was unlocked
	 * @return time achievement was unlocked
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * Resets value/time of achievement
	 */
	public void reset() {
		value = false;
		time = 0;
	}
	
	/**
	 * Sets value of achievement
	 * @param value value of achievement
	 */
	public void unlock() {
		value = true;
		time = System.currentTimeMillis();
	}
	
	/**
	 * Returns resource id of achievement icon
	 * @return resource id of achievement icon
	 */
	public int getIcon() {
		return idIcon;
	}
	
	/**
	 * Returns resource id of achievement title
	 * @return resource id of achievement title
	 */
	public int getTitle() {
		return idTitle;
	}
	
	/**
	 * Returns resource id of achievement description
	 * @return resource id achievement description
	 */
	public int getDescriptionID() {
		return idDescription;
	}
	
	/**
	 * Returns achievement description
	 * @return achievement description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets achievement description
	 * @param description description of achievement
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Loads resource for title, description based off key
	 * 
	 * adapted from here: http://stackoverflow.com/questions/3042961/how-can-i-get-the-resource-id-of-an-image-if-i-know-its-name
	 * and here: adapted from here: http://stackoverflow.com/questions/4746058/reference-one-string-from-another-string-in-strings-xml
	 * 
	 */
	public void loadResources(Resources resources, String packageName) {

		// load resource ids for title, description
		idTitle = resources.getIdentifier(key + "_title", "string", packageName);
		idDescription = resources.getIdentifier(key + "_description", "string", packageName);

		if (idTitle == 0) {
			System.out.println("loadResources error: " + key + "_title");
		}
		
		if (idDescription == 0) {
			System.out.println("loadResources error: " + key + "_description");
		}
		
		// load description, substitution text where necessary
		if (num != -1) {
			description = resources.getString(idDescription, "" + num);
		} else {
			description = resources.getString(idDescription);
		}
	}
	
	@Override
	public String toString() {
		return key;
	}
	
	/**
	 * Returns date representation of time achievement was unlocked
	 * @return date representation of time achievement was unlocked
	 */
	public String getTimeString() {
		return Util.getDateString(time);
	}
	
	/**
	 * Returns progress of this achievement (0 - 1)
	 * @return progress of this achievement
	 */
	public float getProgress() {
		if (num == -1 || statistic == -1) {
			return 0;
		}
		
		// get current count
		int count = GlobalStatistics.getStatistic(statistic);
		
		float percent = 1.f * count / num;
		
		if (percent > 1) {
			percent = 1;
		}
		
		return percent;
	}
}
