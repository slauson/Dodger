package com.slauson.dasher.powerups;

import com.slauson.dasher.status.Upgrades;

/**
 * Invulnerable powerup that makes ship invulnerable for short time period
 * @author josh
 *
 */
public class PowerupInvulnerable extends InactivePowerup {

	// constants
	private final static int DURATION_0 = 5000;
	private final static int DURATION_1 = 10000;
	private final static int DURATION_2 = 15000;
	private final static int DURATION_3 = 20000;

	private int counter;
	
	public PowerupInvulnerable(int level) {
		super(level);
		
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
	
	@Override
	public void activate() {
		switch(level) {
		case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
		case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
		case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.INVULNERABILITY_UPGRADE_DASHER:
			activate(DURATION_3);
		default:
			activate(DURATION_0);
		}
	}
	
	/**
	 * Returns true if player can destroy powerups with dash while invulnerable
	 * @return true if player can destroy powerups with dash while invulnerable
	 */
	public boolean isDasher() {
		return level >= Upgrades.INVULNERABILITY_UPGRADE_DASHER;
	}

}