package com.slauson.dasher.powerups;

import com.slauson.dasher.status.Upgrades;

/**
 * Slow powerup that slows down time
 * @author Josh Slauson
 *
 */
public class PowerupSlow extends InactivePowerup {
	
	// constants
	private final static int DURATION_0 = 5000;
	private final static int DURATION_1 = 10000;
	private final static int DURATION_2 = 15000;
	private final static int DURATION_3 = 20000;
	
	public PowerupSlow(int level) {
		super(level);
	}
	
	@Override
	public void activate() {
		switch(level) {
		case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
		case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
		case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.SLOW_UPGRADE_QUARTER_SPEED:
			activate(DURATION_3);
		default:
			activate(DURATION_0);
		}
	}
	
	/**
	 * Returns true if time is at one quarter speed
	 * @return true if time is at one quarter speed
	 */
	public boolean isQuarterSpeed() {
		return level >= Upgrades.SMALL_UPGRADE_QUARTER_SIZE;
	}
}