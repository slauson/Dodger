package com.slauson.dasher.other;

import android.app.Activity;

/**
 * Abstract class used for updating activity from game.
 * 
 * Used by GameActivity and InstructionsMenu.
 * 
 * @author Josh Slauson
 *
 */
public abstract class GameBaseActivity extends Activity{

	/**
	 * Used when game is over so we can quit properly
	 */
	public abstract void gameOver();
	
	/**
	 * Updates accelerometer
	 * @param tx horizontal orientation
	 * @param ty vertical orientation
	 */
	public abstract void updateAccelerometer(float tx, float ty);
}
