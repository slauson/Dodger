package com.slauson.asteroid_dasher.status;

import com.slauson.asteroid_dasher.other.Util;

public class Statistics {

	// time played
	public int timePlayed;
	public int timesPlayed;
	
	// asteroids destroyed
	public int asteroidsDestroyedByDash;
	public int asteroidsDestroyedByDrill;
	public int asteroidsDestroyedByMagnet;
	public int asteroidsDestroyedByBlackHole;
	public int asteroidsDestroyedByBumper;
	public int asteroidsDestroyedByBomb;
	
	// powerup uses
	public int usesDash;
	public int usesSmall;
	public int usesSlow;
	public int usesInvulnerability;
	public int usesDrill;
	public int usesMagnet;
	public int usesBlackHole;
	public int usesBumper;
	public int usesBomb;
	
	public int pointsEarned;
	public int pointsSpent;
	
	public Statistics() {
		timePlayed = 0;
		timesPlayed = 0;
		
		asteroidsDestroyedByDash = 0;
		asteroidsDestroyedByDrill = 0;
		asteroidsDestroyedByMagnet = 0;
		asteroidsDestroyedByBlackHole = 0;
		asteroidsDestroyedByBumper = 0;
		asteroidsDestroyedByBomb = 0;
		
		usesDash = 0;
		usesSmall = 0;
		usesSlow = 0;
		usesInvulnerability = 0;
		usesDrill = 0;
		usesMagnet = 0;
		usesBlackHole = 0;
		usesBumper = 0;
		usesBomb = 0;
		
		pointsEarned = 0;
		pointsSpent = 0;
	}
	
	/**
	 * Returns total number of asteroids destroyed by powerups/abilities
	 * @return total number of asteroids destroyed by powerups/abilities
	 */
	public int getTotalNumAsteroidsDestroyed() {
		return asteroidsDestroyedByDash +
				asteroidsDestroyedByDrill +
				asteroidsDestroyedByMagnet +
				asteroidsDestroyedByBlackHole +
				asteroidsDestroyedByBumper +
				asteroidsDestroyedByBomb;
	}
	
	/**
	 * Returns total number of powerup/ability uses
	 * @return total number of powerup/ability uses
	 */
	public int getTotalUses() {
		return usesDash +
				usesSmall +
				usesSlow +
				usesInvulnerability +
				usesDrill +
				usesMagnet +
				usesBlackHole +
				usesBumper +
				usesBomb;
	}
	
	/**
	 * Returns string representation of time played
	 * @param hours true if hours should be displayed
	 * @return string representation of time played
	 */
	public String getTimePlayedString(boolean hours) {
		return Util.getTimeString(timePlayed, hours);
	}
	
	/**
	 * Resets all statistics to 0
	 */
	public void reset() {
		timePlayed = 0;
		timesPlayed = 0;
		
		asteroidsDestroyedByDash = 0;
		asteroidsDestroyedByDrill = 0;
		asteroidsDestroyedByMagnet = 0;
		asteroidsDestroyedByBlackHole = 0;
		asteroidsDestroyedByBumper = 0;
		asteroidsDestroyedByBomb = 0;
		
		usesDash = 0;
		usesSmall = 0;
		usesSlow = 0;
		usesInvulnerability = 0;
		usesDrill = 0;
		usesMagnet = 0;
		usesBlackHole = 0;
		usesBumper = 0;
		usesBomb = 0;
		
		pointsEarned = 0;
		pointsSpent = 0;
	}
}
