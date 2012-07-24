package com.slauson.dasher.powerups;


import com.slauson.dasher.objects.Asteroid;
import com.slauson.dasher.objects.Sprite;

import android.graphics.Bitmap;

/**
 * Active powerup that is drawn on the screen
 * @author Josh Slauson
 *
 */
public abstract class ActivePowerup extends Sprite {

	// ending time of powerup
	protected long endingTime;
	
	// number of affected asteroids
	protected int numAffectedAsteroids;
	
	protected static final int FADE_OUT_DURATION = 1000;
	
	public ActivePowerup(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
		
		endingTime = 0;
		numAffectedAsteroids = 0;
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
	
	public long remainingDuration() {
		return endingTime - System.currentTimeMillis();
	}
	
	/**
	 * Activates this powerup
	 */
	public void activate(long duration) {
		endingTime = System.currentTimeMillis() + duration;
	}
	
	/**
	 * Returns the number of affected asteroids
	 * @return the number of affected asteroids
	 */
	public int getNumAffectedAsteroids() {
		return numAffectedAsteroids;
	}
	
	/**
	 * Returns true if powerup is fading out
	 * @return true if powerup is fading out
	 */
	public boolean isFadingOut() {
		return remainingDuration() < FADE_OUT_DURATION;
	}

	/**
	 * Adds the given amount of time to the ending time for the powerup
	 * @param timeDifference amount of time to add
	 */
	public void addTime(long timeDifference) {
		endingTime += timeDifference;
	}
	
	// abstract methods to be defined in subclasses
	/**
	 * Alters asteroid's path
	 * @param asteroid asteroid to alter
	 */
	public abstract void alterAsteroid(Asteroid asteroid);
	
	/**
	 * Checks any powerup-related achievements
	 */
	public abstract void checkAchievements();
}
