package com.slauson.dasher.game;

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
public class MyAccelerometer implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor sensorAccelerometer;
	private MyGameActivity parent;
	
	private float maximumRange;
	
	public MyAccelerometer(Context c) {
		parent = (MyGameActivity)c;
	}
	
	void registerListener() {
		sensorManager = (SensorManager)parent.getSystemService(Context.SENSOR_SERVICE);
		sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		maximumRange = sensorAccelerometer.getMaximumRange();
		
		sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	void unregisterListener() {
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		/*
		 * event.values[0]: azimuth, rotation around the z-axis
		 * event.values[1]: pitch, rotation around the x-axis
		 * event.values[2]: roll, rotation around the y-axis
		 */
		
		float valueAzimuth = event.values[0];
		float valuePitch = event.values[1];
		
		parent.updateAccelerometer(valueAzimuth/maximumRange, -valuePitch/maximumRange);
	}

}
