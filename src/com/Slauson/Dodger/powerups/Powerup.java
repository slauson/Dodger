package com.slauson.dodger.powerups;

public abstract class Powerup {

	private long endingTime;
	
	public Powerup() {
		this.endingTime = 0;
	}
	
	public boolean isActive() {
		
		return System.currentTimeMillis() < endingTime;
	}
	
	public void activate(long duration) {
		endingTime = System.currentTimeMillis() + duration;
	}
	
}