package com.slauson.dasher.game;

import com.slauson.dasher.other.GameBaseActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Accelerometer object
 * @author Josh Slauson
 *
 * adapted from here: http://android-coding.blogspot.com/2012/03/surfaceview-game-step-by-step-react.html
 *
 */
public class Accelerometer implements SensorEventListener {

	/** Sensor manager **/
	private SensorManager sensorManager;
	/** Accelerometer sensor **/
	private Sensor sensorAccelerometer;
	/** Callback for passing accelerometer info to **/
	private GameBaseActivity gameBaseActivity;
	
	/** Maximum range of accelerometer **/
	private float maximumRange;
	
	/** Set to true if listener was successfully registered **/
	boolean listenerRegistered;
	
	public Accelerometer(GameBaseActivity gameBaseActivity) {
		this.gameBaseActivity = gameBaseActivity;
		
		listenerRegistered = false;
	}
	
	/**
	 * Registers listener for accelerometer
	 */
	public void registerListener() {
		sensorManager = (SensorManager)gameBaseActivity.getSystemService(Context.SENSOR_SERVICE);
		sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		// return if sensorAccelerometer is null (this happens when there isn't an accelerometer)
		if (sensorAccelerometer == null) {
			return;
		}
		
		maximumRange = sensorAccelerometer.getMaximumRange();
		sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		listenerRegistered = true;
	}
	
	/**
	 * Unregisters listener for accelerometer
	 */
	public void unregisterListener() {
		if (listenerRegistered) {
			sensorManager.unregisterListener(this);
			listenerRegistered = false;
		}
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore for now
	}

	/**
	 * Called when accelerometer sensor changes
	 */
	public void onSensorChanged(SensorEvent event) {
		/*
		 * event.values[0]: azimuth, rotation around the z-axis
		 * event.values[1]: pitch, rotation around the x-axis
		 * event.values[2]: roll, rotation around the y-axis
		 */
		
		float valueAzimuth = event.values[0];
		float valuePitch = event.values[1];
		
		gameBaseActivity.updateAccelerometer(valueAzimuth/maximumRange, -valuePitch/maximumRange);
	}
}
