package com.slauson.dasher.powerups;

/**
 * Inactive powerup that is not drawn on the screen
 * @author Josh Slauson
 *
 */
public abstract class InactivePowerup {
	
	protected long endingTime;
	
	public InactivePowerup() {
		// do nothing
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
}