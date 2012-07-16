package com.slauson.dasher.powerups;

import com.slauson.dasher.status.Achievements;
import com.slauson.dasher.status.Upgrades;

/**
 * Invulnerable powerup that makes ship invulnerable for short time period
 * @author josh
 *
 */
public class PowerupInvulnerable extends InactivePowerup {

	// constants
	private static final int DURATION_0 = 10000;
	private static final int DURATION_1 = 15000;
	private static final int DURATION_2 = 20000;
	private static final int DURATION_3 = 30000;

	private int counter;
	private int passThroughNum;
	
	public PowerupInvulnerable(int level) {
		super(level);
		
		counter = Integer.MAX_VALUE;
		passThroughNum = 0;
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
		
		if (!isActive()) {
			passThroughNum = 0;
		}
		
		switch(level) {
		case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_1:
			activate(DURATION_1);
			break;
		case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_2:
			activate(DURATION_2);
			break;
		case Upgrades.INVULNERABILITY_UPGRADE_INCREASED_DURATION_3:
		case Upgrades.INVULNERABILITY_UPGRADE_DASHER:
			activate(DURATION_3);
			break;
		default:
			activate(DURATION_0);
			break;
		}
	}
	
	/**
	 * Returns true if player can destroy powerups with dash while invulnerable
	 * @return true if player can destroy powerups with dash while invulnerable
	 */
	public boolean isDasher() {
		return level >= Upgrades.INVULNERABILITY_UPGRADE_DASHER;
	}
	
	/**
	 * Increments the number of passed through asteroids
	 */
	public void passThrough() {
		passThroughNum++;
	}

	/**
	 * Checks invulnerability powerup related achievements
	 */
	public void checkAchievements() {
		if (passThroughNum > Achievements.LOCAL_INVULNERABILITY_PASS_THROUGH_NUM) {
			Achievements.unlockLocalAchievement(Achievements.localInvulnerabilityPassThrough);
		}
	}

}