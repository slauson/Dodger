package com.slauson.dasher.powerups;

/**
 * Slow powerup that slows down time
 * @author Josh Slauson
 *
 */
public class PowerupSlow extends InactivePowerup {
	
	private int range;
	
	public PowerupSlow() {
		super();
		
		this.range = 0;
	}
	
	public PowerupSlow(int range) {
		super();
		
		this.range = range;
	}

	public int getRange() {
		return range;
	}
}