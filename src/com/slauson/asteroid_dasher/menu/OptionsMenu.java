package com.slauson.asteroid_dasher.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.slauson.asteroid_dasher.status.GlobalStatistics;
import com.slauson.asteroid_dasher.status.Options;
import com.slauson.asteroid_dasher.R;

public class OptionsMenu extends PreferenceActivity {
	
	/** Control values **/
	private List<String> controlEntries;
	private List<String> controlValues;
	
	/** Accelerometer sensitiviy entries **/
	private static final CharSequence[] accelerometerSensitivityEntries =
		{
			Options.ACCELEROMETER_SENSITIVITY_LOW_STRING,
			Options.ACCELEROMETER_SENSITIVITY_NORMAL_STRING,
			Options.ACCELEROMETER_SENSITIVITY_HIGH_STRING,
		};
	
	/** Accelerometer sensitiviy values **/
	private static final CharSequence[] accelerometerSensitivityValues=
		{
			"" + Options.ACCELEROMETER_SENSITIVITY_LOW,
			"" + Options.ACCELEROMETER_SENSITIVITY_NORMAL,
			"" + Options.ACCELEROMETER_SENSITIVITY_HIGH,
		};
	
	/** Player offset entries **/
	private static final CharSequence[] playerOffsetEntries =
		{
			Options.PLAYER_OFFSET_NONE_STRING,
			Options.PLAYER_OFFSET_SMALL_STRING,
			Options.PLAYER_OFFSET_NORMAL_STRING,
			Options.PLAYER_OFFSET_LARGE_STRING,
		};
	
	/** Player offset values **/
	private static final CharSequence[] playerOffsetValues=
		{
			"" + Options.PLAYER_OFFSET_NONE,
			"" + Options.PLAYER_OFFSET_SMALL,
			"" + Options.PLAYER_OFFSET_NORMAL,
			"" + Options.PLAYER_OFFSET_LARGE,
		};
	
	/** Graphics entries **/
	private static final CharSequence[] graphicsEntries =
		{
			Options.GRAPHICS_LOW_STRING,
			Options.GRAPHICS_NORMAL_STRING,
		};

	/** Graphics values **/
	private static final CharSequence[] graphicsValues =
		{
			"" + Options.GRAPHICS_LOW,
			"" + Options.GRAPHICS_NORMAL,
		};

	/** Frame rate entries **/
	private static final CharSequence[] frameRateEntries =
		{
			Options.FRAME_RATE_LOW_STRING,
			Options.FRAME_RATE_NORMAL_STRING,
			Options.FRAME_RATE_HIGH_STRING,
		};
	
	/** Frame rate values **/
	private static final CharSequence[] frameRateValues =
		{
			"" + Options.FRAME_RATE_LOW,
			"" + Options.FRAME_RATE_NORMAL,
			"" + Options.FRAME_RATE_HIGH,
		};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// if first time playing, set default options
		if (GlobalStatistics.getInstance().timesPlayed == 0) {
			SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
			Options.save(this, sharedPreferencesEditor);
			sharedPreferencesEditor.commit();
		}

		//add the base preferences from configuration.xml
		addPreferencesFromResource(R.xml.configuration);
				
		// check what control types are available
		boolean accelerometerDetected = false;
		boolean keyboardDetected = false;
		
		SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		accelerometerDetected = (sensorAccelerometer != null);
		
		keyboardDetected = (getResources().getConfiguration().keyboard == Configuration.KEYBOARD_QWERTY ||
				getResources().getConfiguration().navigation == Configuration.NAVIGATION_DPAD);
		
		// add available control types
		controlEntries = new ArrayList<String>();
		controlValues = new ArrayList<String>();
		controlEntries.add(Options.CONTROL_TOUCH_STRING);
		controlValues.add("" + Options.CONTROL_TOUCH);
		if (accelerometerDetected) {
			controlEntries.add(Options.CONTROL_ACCELEROMETER_STRING);
			controlValues.add("" + Options.CONTROL_ACCELEROMETER);
		}
		if (keyboardDetected) {
			controlEntries.add(Options.CONTROL_KEYBOARD_STRING);
			controlValues.add("" + Options.CONTROL_KEYBOARD);
		}
		
		// control types
		ListPreference controls = (ListPreference)findPreference(getString(R.string.configuration_key_controls));
		controls.setEntries(controlEntries.toArray(new CharSequence[controlEntries.size()]));
		controls.setEntryValues(controlValues.toArray(new CharSequence[controlValues.size()]));
		
		// accelerometer sensitivity
		ListPreference accelerometerSensitivity = (ListPreference)findPreference(getString(R.string.configuration_key_accelerometer_sensitivity));

		if (accelerometerDetected) {
			accelerometerSensitivity.setEntries(accelerometerSensitivityEntries);
			accelerometerSensitivity.setEntryValues(accelerometerSensitivityValues);
		} else {
			accelerometerSensitivity.setEnabled(false);
		}
		
		// player offset
		ListPreference playerOffset = (ListPreference)findPreference(getString(R.string.configuration_key_player_offset));
		playerOffset.setEntries(playerOffsetEntries);
		playerOffset.setEntryValues(playerOffsetValues);
		
		// graphics settings
		ListPreference graphics = (ListPreference)findPreference(getString(R.string.configuration_key_graphics));
		graphics.setEntries(graphicsEntries);
		graphics.setEntryValues(graphicsValues);
		
		// frame rate
		ListPreference frameRate = (ListPreference)findPreference(getString(R.string.configuration_key_frame_rate));
		frameRate.setEntries(frameRateEntries);
		frameRate.setEntryValues(frameRateValues);

		setContentView(R.layout.options_menu);
	}
	
	@Override
	public void onPause() {
		super.onPause();

		// do this in onPause instead of onDestroy because onDestroy gets called after the previous activity's onResume
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		Options.load(this, preferences);
	}
}
