package com.slauson.dasher.powerups;

/**
 * Stop powerup that stops time
 * @author josh
 *
 */
public class PowerupStop extends InactivePowerup {
	
	private int range;
	
	public PowerupStop() {
		super();
		
		this.range = 0;
	}
	
	public PowerupStop(int range) {
		super();
		
		this.range = range;
	}

	public int getRange() {
		return range;
	}
}