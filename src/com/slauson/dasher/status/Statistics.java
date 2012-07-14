package com.slauson.dasher.status;

import com.slauson.dasher.other.Util;

public class Statistics {

	// time played
	public int timePlayed = 0;
	public int timesPlayed = 0;
	
	// asteroids destroyed
	public int asteroidsDestroyedByDash = 0;
	public int asteroidsDestroyedByDrill = 0;
	public int asteroidsDestroyedByMagnet = 0;
	public int asteroidsDestroyedByWhiteHole = 0;
	public int asteroidsDestroyedByBumper = 0;
	public int asteroidsDestroyedByBomb = 0;
	
	// powerup uses
	public int usesDash = 0;
	public int usesSmall = 0;
	public int usesSlow = 0;
	public int usesInvulnerability = 0;
	public int usesDrill = 0;
	public int usesMagnet = 0;
	public int usesWhiteHole = 0;
	public int usesBumper = 0;
	public int usesBomb = 0;
	
	public Statistics() {
		timePlayed = 0;
		timesPlayed = 0;
		
		asteroidsDestroyedByDash = 0;
		asteroidsDestroyedByDrill = 0;
		asteroidsDestroyedByMagnet = 0;
		asteroidsDestroyedByWhiteHole = 0;
		asteroidsDestroyedByBumper = 0;
		asteroidsDestroyedByBomb = 0;
		
		usesDash = 0;
		usesSmall = 0;
		usesSlow = 0;
		usesInvulnerability = 0;
		usesDrill = 0;
		usesMagnet = 0;
		usesWhiteHole = 0;
		usesBumper = 0;
		usesBomb = 0;
	}
	
	/**
	 * Returns total number of asteroids destroyed by powerups/abilities
	 * @return total number of asteroids destroyed by powerups/abilities
	 */
	public int getTotalNumAsteroidsDestroyed() {
		return asteroidsDestroyedByDash +
				asteroidsDestroyedByDrill +
				asteroidsDestroyedByMagnet +
				asteroidsDestroyedByWhiteHole +
				asteroidsDestroyedByBumper +
				asteroidsDestroyedByBomb;
	}
	
	/**
	 * Returns string representation of time played
	 * @return string representation of time played
	 */
	public String getTimePlayedString() {
		return Util.getTimeString(timePlayed);
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
		asteroidsDestroyedByWhiteHole = 0;
		asteroidsDestroyedByBumper = 0;
		asteroidsDestroyedByBomb = 0;
		
		usesDash = 0;
		usesSmall = 0;
		usesSlow = 0;
		usesInvulnerability = 0;
		usesDrill = 0;
		usesMagnet = 0;
		usesWhiteHole = 0;
		usesBumper = 0;
		usesBomb = 0;
	}
}
