package com.slauson.dasher.main;

import android.content.SharedPreferences;

/**
 * Game configuration
 * @author Josh Slauson
 *
 */
public class Configuration {
	
	// constants
	public static final int CONTROL_TOUCH = 0;
	public static final int CONTROL_ACCELEROMETER = 1;
	public static final int CONTROL_KEYBOARD = 2;
	
	// these correspond to the configuration.xml file used in the OptionsMenu
	private static final String CONTROL_STRING = "configuration_controls"; 
	private static final String CONTROL_TOUCH_STRING = "Touch";
	private static final String CONTROL_KEYBOARD_STRING = "Keyboard";
	private static final String CONTROL_ACCELEROMETER_STRING = "Accelerometer";

	public static final int GRAPHICS_LOW = 0;
	public static final int GRAPHICS_NORMAL = 1;
	
	// these correspond to the configuration.xml file used in the OptionsMenu
	private static final String GRAPHICS_STRING = "configuration_graphics";
	private static final String GRAPHICS_LOW_STRING = "Low";
	private static final String GRAPHICS_NORMAL_STRING = "Normal";

	// controls
	public static int controlType = CONTROL_TOUCH;
	
	// graphics
	public static int graphicsType = GRAPHICS_NORMAL;
	
	/**
	 * Loads configuration from the given preferences
	 * @param preferences preferences to load from
	 */
	public static void load(SharedPreferences preferences) {
		
		// load controls
		String controlString = preferences.getString(CONTROL_STRING, CONTROL_TOUCH_STRING);
		
		if (controlString.equals(CONTROL_TOUCH_STRING)) {
			controlType = CONTROL_TOUCH;
		} else if (controlString.equals(CONTROL_ACCELEROMETER_STRING)) {
			controlType = CONTROL_ACCELEROMETER;
		} else {
			controlType = CONTROL_KEYBOARD;
		}
		
		// load graphics
		String graphicsString = preferences.getString(GRAPHICS_STRING, GRAPHICS_NORMAL_STRING);
		
		if (graphicsString.equals(GRAPHICS_NORMAL_STRING)) {
			graphicsType = GRAPHICS_NORMAL;
		} else {
			graphicsType = GRAPHICS_LOW;
		}
	}
}