package com.slauson.dasher.powerups;

/**
 * Invulnerable powerup that makes ship invulnerable for short time period
 * @author josh
 *
 */
public class PowerupInvulnerable extends InactivePowerup {
	
	private int counter;
	
	public PowerupInvulnerable() {
		super();
		
		counter = Integer.MAX_VALUE;
	}
	
	public void update() {
		counter--;
		
		if (counter < 0) {
			counter = Integer.MAX_VALUE;
		}
	}
	
	public int getCounter() {
		return counter;
	}
}