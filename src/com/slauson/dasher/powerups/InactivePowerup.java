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
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * Adds the given amount of time to the ending time for the powerup
	 * @param timeDifference amount of time to add
	 */
	public void addTime(long timeDifference) {
		endingTime += timeDifference;
	}
	
	// abstract methods
	public abstract void activate();
}