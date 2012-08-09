package com.slauson.dasher.status;

import android.content.SharedPreferences;

/**
 * Game configuration
 * @author Josh Slauson
 *
 */
public class Configuration {
	
	// control methods
	public static final int CONTROL_TOUCH = 0;
	public static final int CONTROL_ACCELEROMETER = 1;
	public static final int CONTROL_KEYBOARD = 2;
	
	// these correspond to the configuration.xml file used in the OptionsMenu
	private static final String CONTROL_STRING = "configuration_controls"; 
	private static final String CONTROL_TOUCH_STRING = "Touch";
	private static final String CONTROL_ACCELEROMETER_STRING = "Accelerometer";
	//private static final String CONTROL_KEYBOARD_STRING = "Keyboard";
	
	// accelerometer sensitivity
	public static final int ACCELEROMETER_SENSITIVITY_LOW = 0;
	public static final int ACCELEROMETER_SENSITIVITY_MEDIUM = 1;
	public static final int ACCELEROMETER_SENSITIVITY_HIGH = 2;
	
	// these correspond to the configuration.xml file used in the OptionsMenu
	private static final String ACCELEROMETER_SENSITIVITY_STRING = "accelerometer_sensitivity"; 
	private static final String ACCELEROMETER_SENSITIVITY_LOW_STRING = "Low";
	private static final String ACCELEROMETER_SENSITIVITY_MEDIUM_STRING = "Medium";
	//private static final String ACCELEROMETER_SENSITIVITY_HIGH_STRING = "High";

	// graphics settings
	public static final int GRAPHICS_LOW = 0;
	public static final int GRAPHICS_NORMAL = 1;
	
	// these correspond to the configuration.xml file used in the OptionsMenu
	private static final String GRAPHICS_STRING = "configuration_graphics";
	private static final String GRAPHICS_NORMAL_STRING = "Normal";
	//private static final String GRAPHICS_LOW_STRING = "Low";
	
	// frame rate settings
	private static final int FRAME_RATE_LOW = 15;
	private static final int FRAME_RATE_NORMAL = 30;
	private static final int FRAME_RATE_HIGH = 60;
	
	// offset height default setting
	private static final float OFFSET_HEIGHT_DEFAULT = 0.125f;
	
	// these correspond to the configuration.xml file used in the OptionsMenu
	private static final String FRAME_RATE_STRING = "configuration_frame_rate";
	private static final String FRAME_RATE_LOW_STRING = "Low";
	private static final String FRAME_RATE_NORMAL_STRING = "Normal";
	//private static final String FRAME_RATE_HIGH_STRING = "High";


	// controls
	public static int controlType = CONTROL_TOUCH;
	
	// accelerometer sensitivity
	public static int accelerometerSensitivity = ACCELEROMETER_SENSITIVITY_MEDIUM;
	
	// graphics
	public static int graphicsType = GRAPHICS_NORMAL;
	
	// frame rate
	public static int frameRate = FRAME_RATE_NORMAL;
	
	// offset height
	public static float offsetHeight = OFFSET_HEIGHT_DEFAULT;
	
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
		
		// load accelerometer sensitivities
		String accelerometerSensitivityString = preferences.getString(ACCELEROMETER_SENSITIVITY_STRING, ACCELEROMETER_SENSITIVITY_MEDIUM_STRING);
		
		if (accelerometerSensitivityString.equals(ACCELEROMETER_SENSITIVITY_LOW_STRING)) {
			accelerometerSensitivity = ACCELEROMETER_SENSITIVITY_LOW;
		} else if (accelerometerSensitivityString.equals(ACCELEROMETER_SENSITIVITY_MEDIUM_STRING)) {
			accelerometerSensitivity = ACCELEROMETER_SENSITIVITY_MEDIUM;
		} else {
			accelerometerSensitivity = ACCELEROMETER_SENSITIVITY_HIGH;
		}
		
		// load graphics
		String graphicsString = preferences.getString(GRAPHICS_STRING, GRAPHICS_NORMAL_STRING);
		
		if (graphicsString.equals(GRAPHICS_NORMAL_STRING)) {
			graphicsType = GRAPHICS_NORMAL;
		} else {
			graphicsType = GRAPHICS_LOW;
		}
		
		// load frame rate
		String frameRateString = preferences.getString(FRAME_RATE_STRING, FRAME_RATE_NORMAL_STRING);
		
		if (frameRateString.equals(FRAME_RATE_LOW_STRING)) {
			frameRate = FRAME_RATE_LOW;
		} else if (frameRateString.equals(FRAME_RATE_NORMAL_STRING)) {
			frameRate = FRAME_RATE_NORMAL;
		} else {
			frameRate = FRAME_RATE_HIGH;
		}
	}
}