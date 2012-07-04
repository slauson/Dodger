package com.slauson.dasher.status;


import android.content.SharedPreferences;

/**
 * Keeps track of global statistics
 * @author Josh Slauson
 *
 */
public class GlobalStatistics extends Statistics {

	// constants
	private static final String TIME_PLAYED = "statistics_time_played";
	//private static final String ASTEROIDS_DESTROYED_TOTAL = "statistics_asteroids_destroyed_total";
	//private static final String ASTEROIDS_DESTROYED_BY_COLLISION = "statistics_asteroids_destroyed_by_collision";
	private static final String ASTEROIDS_DESTROYED_BY_DASH = "statistics_asteroids_destroyed_by_dash";
	private static final String ASTEROIDS_DESTROYED_BY_DRILL = "statistics_asteroids_destroyed_by_drill";
	private static final String ASTEROIDS_DESTROYED_BY_MAGNET = "statistics_asteroids_destroyed_by_magnet";
	private static final String ASTEROIDS_DESTROYED_BY_WHITE_HOLE = "statistics_asteroids_destroyed_by_white_hole";
	private static final String ASTEROIDS_DESTROYED_BY_BUMPER = "statistics_asteroids_destroyed_by_bumper";
	private static final String ASTEROIDS_DESTROYED_BY_BOMB = "statistics_asteroids_destroyed_by_bomb";
	
	/**
	 * Loads statistics from preferences
	 * @param preferences preferences to load from
	 */
	public static void load(SharedPreferences preferences) {
		
		timePlayed = preferences.getInt(TIME_PLAYED, 0);
		
		//asteroidsDestroyedTotal = preferences.getInt(ASTEROIDS_DESTROYED_TOTAL, 0);
		//asteroidsDestroyedByCollision = preferences.getInt(ASTEROIDS_DESTROYED_BY_COLLISION, 0);
		asteroidsDestroyedByDash = preferences.getInt(ASTEROIDS_DESTROYED_BY_DASH, 0);
		asteroidsDestroyedByDrill = preferences.getInt(ASTEROIDS_DESTROYED_BY_DRILL, 0);
		asteroidsDestroyedByMagnet = preferences.getInt(ASTEROIDS_DESTROYED_BY_MAGNET, 0);
		asteroidsDestroyedByWhiteHole = preferences.getInt(ASTEROIDS_DESTROYED_BY_WHITE_HOLE, 0);
		asteroidsDestroyedByBumper = preferences.getInt(ASTEROIDS_DESTROYED_BY_BUMPER, 0);
		asteroidsDestroyedByBomb = preferences.getInt(ASTEROIDS_DESTROYED_BY_BOMB, 0);
	}
	
	/**
	 * Saves statistics to preferences
	 * @param preferences preferences to save to
	 */
	public static void save(SharedPreferences.Editor preferencesEditor) {
		preferencesEditor.putInt(TIME_PLAYED, timePlayed);
		
		//preferencesEditor.putInt(ASTEROIDS_DESTROYED_TOTAL, asteroidsDestroyedTotal);
		//preferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_COLLISION, asteroidsDestroyedByCollision);
		preferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_DASH, asteroidsDestroyedByDash);
		preferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_DRILL, asteroidsDestroyedByDrill);
		preferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_MAGNET, asteroidsDestroyedByMagnet);
		preferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_WHITE_HOLE, asteroidsDestroyedByWhiteHole);
		preferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_BUMPER, asteroidsDestroyedByBumper);
		preferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_BOMB, asteroidsDestroyedByBomb);
	}
	
	/**
	 * Update global statistics from local statistics
	 */
	public static void update() {
		asteroidsDestroyedByDash += LocalStatistics.asteroidsDestroyedByDash;
		asteroidsDestroyedByDrill += LocalStatistics.asteroidsDestroyedByDrill;
		asteroidsDestroyedByMagnet += LocalStatistics.asteroidsDestroyedByMagnet;
		asteroidsDestroyedByWhiteHole += LocalStatistics.asteroidsDestroyedByWhiteHole;
		asteroidsDestroyedByBumper += LocalStatistics.asteroidsDestroyedByBumper;
		asteroidsDestroyedByBomb += LocalStatistics.asteroidsDestroyedByBomb;
	}
}
