package com.slauson.dasher.status;

import com.slauson.dasher.other.Util;

public abstract class Statistics {

	// time played
	public static int timePlayed = 0;
	
	// asteroids destroyed
	public static int asteroidsDestroyedByDash = 0;
	public static int asteroidsDestroyedByDrill = 0;
	public static int asteroidsDestroyedByMagnet = 0;
	public static int asteroidsDestroyedByWhiteHole = 0;
	public static int asteroidsDestroyedByBumper = 0;
	public static int asteroidsDestroyedByBomb = 0;
	
	// powerup uses
	public static int usesDash = 0;
	public static int usesSmall = 0;
	public static int usesSlow = 0;
	public static int usesInvulnerability = 0;
	public static int usesDrill = 0;
	public static int usesMagnet = 0;
	public static int usesWhiteHole = 0;
	public static int usesBumper = 0;
	public static int usesBomb = 0;
	
	/**
	 * Returns total number of asteroids destroyed by powerups/abilities
	 * @return total number of asteroids destroyed by powerups/abilities
	 */
	public static int getTotalNumAsteroidsDestroyed() {
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
	public static String getTimePlayedString() {
		return Util.getTimeString(timePlayed);
	}
	
	/**
	 * Resets all statistics to 0
	 */
	public static void reset() {
		timePlayed = 0;
		
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
