package com.slauson.dasher.powerups;

import com.slauson.dasher.status.Upgrades;

/**
 * Small powerup that makes the player ship small
 * @author Josh Slauson
 *
 */
public class PowerupSmall extends InactivePowerup {

	// constants
	private static final int DURATION_0 = 10000;
	private static final int DURATION_1 = 15000;
	private static final int DURATION_2 = 20000;
	private static final int DURATION_3 = 30000;

	public PowerupSmall(int level) {
		super(level);
	}
	
	@Override
	public void activate() {
		switch(level) {
		case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
			break;
		case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
			break;
		case Upgrades.SMALL_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.SMALL_UPGRADE_BIG_DASH:
			activate(DURATION_3);
			break;
		default:
			activate(DURATION_0);
			break;
		}
	}
	
	/**
	 * Returns true if dash is normal size
	 * @return true if dash is normal size
	 */
	public boolean isBigDash() {
		return level >= Upgrades.SMALL_UPGRADE_BIG_DASH;
	}
}
