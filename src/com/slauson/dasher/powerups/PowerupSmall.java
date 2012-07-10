package com.slauson.dasher.powerups;

import com.slauson.dasher.status.Upgrades;

/**
 * Small powerup that makes the player ship small
 * @author Josh Slauson
 *
 */
public class PowerupSmall extends InactivePowerup {

	// constants
	private final static int DURATION_0 = 5000;
	private final static int DURATION_1 = 10000;
	private final static int DURATION_2 = 15000;
	private final static int DURATION_3 = 20000;

	public PowerupSmall(int level) {
		super(level);
	}
	
	@Override
	public void activate() {
		switch(level) {
		case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
		case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
		case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.SMALL_UPGRADE_QUARTER_SIZE:
			activate(DURATION_3);
		default:
			activate(DURATION_0);
		}
	}
	
	/**
	 * Returns true if ship is one quarter size
	 * @return true if ship is one quarter size
	 */
	public boolean isQuarterSize() {
		return level >= Upgrades.SLOW_UPGRADE_QUARTER_SPEED;
	}
}
