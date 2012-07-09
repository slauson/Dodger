package com.slauson.dasher.status;


public class LocalStatistics extends Statistics {

	/**
	 * Reset local statistics back to 0
	 */
	public static void reset() {
		timePlayed = 0;
		
		asteroidsDestroyedByDash = 0;
		asteroidsDestroyedByDrill = 0;
		asteroidsDestroyedByMagnet = 0;
		asteroidsDestroyedByWhiteHole = 0;
		asteroidsDestroyedByBumper = 0;
		asteroidsDestroyedByBomb = 0;
	}
}
