package com.slauson.dasher.status;

import com.slauson.dasher.R;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Game configuration
 * @author Josh Slauson
 *
 */
public class Options {
	
	// control methods
	public static final int CONTROL_TOUCH = 0;
	public static final int CONTROL_ACCELEROMETER = 1;
	public static final int CONTROL_KEYBOARD = 2;
	public static final int CONTROL_DEFAULT = CONTROL_TOUCH;
	
	public static final String CONTROL_TOUCH_STRING = "Touch";
	public static final String CONTROL_ACCELEROMETER_STRING = "Accelerometer";
	public static final String CONTROL_KEYBOARD_STRING = "Keyboard";
	
	// accelerometer sensitivity
	public static final int ACCELEROMETER_SENSITIVITY_LOW = 0;
	public static final int ACCELEROMETER_SENSITIVITY_MEDIUM = 1;
	public static final int ACCELEROMETER_SENSITIVITY_HIGH = 2;
	public static final int ACCELEROMETER_SENSITIVITY_DEFAULT = ACCELEROMETER_SENSITIVITY_MEDIUM;

	public static final String ACCELEROMETER_SENSITIVITY_LOW_STRING = "Low";
	public static final String ACCELEROMETER_SENSITIVITY_MEDIUM_STRING = "Medium";
	public static final String ACCELEROMETER_SENSITIVITY_HIGH_STRING = "High";
	
	// graphics settings
	public static final int GRAPHICS_LOW = 0;
	public static final int GRAPHICS_NORMAL = 1;
	public static final int GRAPHICS_DEFAULT = GRAPHICS_NORMAL;
	
	public static final String GRAPHICS_NORMAL_STRING = "Normal";
	public static final String GRAPHICS_LOW_STRING = "Low";
	
	// frame rate settings
	public static final int FRAME_RATE_LOW = 15;
	public static final int FRAME_RATE_NORMAL = 30;
	public static final int FRAME_RATE_HIGH = 60;
	public static final int FRAME_RATE_DEFAULT = FRAME_RATE_NORMAL;
	
	public static final String FRAME_RATE_LOW_STRING = "Low";
	public static final String FRAME_RATE_NORMAL_STRING = "Normal";
	public static final String FRAME_RATE_HIGH_STRING = "High";
	
	/** Game control type **/
	public static int controlType = CONTROL_TOUCH;
	
	/** Accelerometer sensitivity **/
	public static int accelerometerSensitivity = ACCELEROMETER_SENSITIVITY_MEDIUM;
	
	/** Game graphics **/
	public static int graphicsType = GRAPHICS_NORMAL;
	
	/** Game frame rate **/
	public static int frameRate = FRAME_RATE_NORMAL;
	
	/** True if this is free version **/
	public static boolean freeVersion = true;
	
	private static boolean initialized = false;
	
	/**
	 * Loads configuration from the given preferences
	 * @param activity activity to load keys from
	 * @param preferences preferences to load from
	 */
	public static void load(Activity activity, SharedPreferences preferences) {
		
		System.out.println("Options::load()");
		
		controlType = Integer.parseInt(preferences.getString(activity.getString(R.string.configuration_key_controls), "" + CONTROL_DEFAULT));
		accelerometerSensitivity = Integer.parseInt(preferences.getString(activity.getString(R.string.configuration_key_accelerometer_sensitivity), "" + ACCELEROMETER_SENSITIVITY_DEFAULT));
		graphicsType = Integer.parseInt(preferences.getString(activity.getString(R.string.configuration_key_graphics), "" + GRAPHICS_DEFAULT));
		frameRate = Integer.parseInt(preferences.getString(activity.getString(R.string.configuration_key_frame_rate), "" + FRAME_RATE_DEFAULT));

		initialized = true;
	}
	
	/**
	 * Saves options to given preferences editor
	 * @param activity activity to load keys from
	 * @param preferencesEditor preferences editor to save to
	 */
	public static void save(Activity activity, SharedPreferences.Editor preferencesEditor) {
		
		System.out.println("Options::save()");
		
		preferencesEditor.putString(activity.getString(R.string.configuration_key_controls), "" + controlType);
		preferencesEditor.putString(activity.getString(R.string.configuration_key_accelerometer_sensitivity), "" + accelerometerSensitivity);
		preferencesEditor.putString(activity.getString(R.string.configuration_key_graphics), "" + graphicsType);
		preferencesEditor.putString(activity.getString(R.string.configuration_key_frame_rate), "" + frameRate);
	}

	/**
	 * Returns true if the options were initialized from application preferences
	 * @return true if the options were initialized from application preferences
	 */
	public static boolean initialized() {
		return initialized;
	}
}