package com.slauson.dasher.powerups;

/**
 * Inactive powerup that is not drawn on the screen
 * @author Josh Slauson
 *
 */
public abstract class InactivePowerup {
	
	protected long endingTime;
	protected int level;
	
	public InactivePowerup(int level) {
		this.level = level;
	}
	
	/**
	 * Returns true if this powerup is active
	 * @return true if this powerup is active
	 */
	public boolean isActive() {
		return System.currentTimeMillis() < endingTime;
	}
	
	/**
	 * Activates this powerup for the given duration
	 * @param duration duration to activate powerup for
	 */
	protected void activate(long duration) {
		endingTime = System.currentTimeMillis() + duration;
	}
	
	public void update() {
		// do nothing
	}
	
	// abstract methods
	public abstract void activate();
}