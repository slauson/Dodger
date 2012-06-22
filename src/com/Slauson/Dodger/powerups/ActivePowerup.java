package com.slauson.dodger.powerups;


import com.slauson.dodger.objects.Asteroid;
import com.slauson.dodger.objects.Sprite;

import android.graphics.Bitmap;

/**
 * Active powerup that is drawn on the screen
 * @author Josh Slauson
 *
 */
public abstract class ActivePowerup extends Sprite {

	// ending time of powerup
	protected long endingTime;
	
	public ActivePowerup(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
		
		endingTime = 0;
	}
	
	@Override
	public void update() {
		// do nothing by default
	}
	
	/**
	 * Returns true if this powerup is active
	 * @return true if this powerup is active
	 */
	public boolean isActive() {
		return System.currentTimeMillis() < endingTime;
	}
	
	/**
	 * Activates this powerup
	 */
	public void activate(long duration) {
		endingTime = System.currentTimeMillis() + duration;
	}
	
	// abstract methods to be defined in subclasses
	/**
	 * Alters asteroid's path
	 * @param asteroid asteroid to alter
	 */
	public abstract void alterAsteroid(Asteroid asteroid);
}
