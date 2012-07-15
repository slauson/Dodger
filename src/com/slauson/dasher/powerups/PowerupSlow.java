package com.slauson.dasher.powerups;

import com.slauson.dasher.status.Upgrades;

/**
 * Slow powerup that slows down time
 * @author Josh Slauson
 *
 */
public class PowerupSlow extends InactivePowerup {
	
	// constants
	private static final int DURATION_0 = 10000;
	private static final int DURATION_1 = 15000;
	private static final int DURATION_2 = 20000;
	private static final int DURATION_3 = 30000;
	
	public PowerupSlow(int level) {
		super(level);
	}
	
	@Override
	public void activate() {
		switch(level) {
		case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
			break;
		case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
			break;
		case Upgrades.SLOW_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.SLOW_UPGRADE_NO_AFFECT_DROPS_AND_POWERUPS:
			activate(DURATION_3);
			break;
		default:
			activate(DURATION_0);
			break;
		}
	}
	
	/**
	 * Returns true if slowed time affects drops and powerups
	 * @return true if slowed time affects drops and powerups
	 */
	public boolean isAffectingDropsAndPowerups() {
		return level >= Upgrades.SLOW_UPGRADE_NO_AFFECT_DROPS_AND_POWERUPS;
	}
}