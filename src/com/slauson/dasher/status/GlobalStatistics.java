package com.slauson.dasher.status;


import android.content.SharedPreferences;

/**
 * Keeps track of global statistics
 * @author Josh Slauson
 *
 */
public class GlobalStatistics extends Statistics {

	// public constants
	
	// ids for achievements associated with statistics
	public static final int ID_TIME_PLAYED = 0;
	
	public static final int ID_ASTEROIDS_DESTROYED_BY_DASH = 1;
	public static final int ID_ASTEROIDS_DESTROYED_BY_DRILL = 2;
	public static final int ID_ASTEROIDS_DESTROYED_BY_MAGNET = 3;
	public static final int ID_ASTEROIDS_DESTROYED_BY_WHITE_HOLE = 4;
	public static final int ID_ASTEROIDS_DESTROYED_BY_BUMPER = 5;
	public static final int ID_ASTEROIDS_DESTROYED_BY_BOMB = 6;
	public static final int ID_ASTEROIDS_DESTROYED_TOTAL = 7;
	
	public static final int ID_USES_DASH = 8;
	public static final int ID_USES_SMALL = 9;
	public static final int ID_USES_SLOW = 10;
	public static final int ID_USES_INVULNERABILITY = 11;
	public static final int ID_USES_DRILL = 12;
	public static final int ID_USES_MAGNET = 13;
	public static final int ID_USES_WHITE_HOLE = 14;
	public static final int ID_USES_BUMPER = 15;
	public static final int ID_USES_BOMB = 16;
	
	// private constants
	private static final String TIME_PLAYED = "statistics_time_played";
	
	private static final String ASTEROIDS_DESTROYED_BY_DASH = "statistics_asteroids_destroyed_by_dash";
	private static final String ASTEROIDS_DESTROYED_BY_DRILL = "statistics_asteroids_destroyed_by_drill";
	private static final String ASTEROIDS_DESTROYED_BY_MAGNET = "statistics_asteroids_destroyed_by_magnet";
	private static final String ASTEROIDS_DESTROYED_BY_WHITE_HOLE = "statistics_asteroids_destroyed_by_white_hole";
	private static final String ASTEROIDS_DESTROYED_BY_BUMPER = "statistics_asteroids_destroyed_by_bumper";
	private static final String ASTEROIDS_DESTROYED_BY_BOMB = "statistics_asteroids_destroyed_by_bomb";
	
	private static final String USES_DASH = "statistics_uses_dash";
	private static final String USES_SMALL = "statistics_uses_small";
	private static final String USES_SLOW = "statistics_uses_slow";
	private static final String USES_INVULNERABILITY = "statistics_uses_invulnerability";
	private static final String USES_DRILL = "statistics_uses_drill";
	private static final String USES_MAGNET = "statistics_uses_magnet";
	private static final String USES_WHITE_HOLE = "statistics_uses_white_hole";
	private static final String USES_BUMPER = "statistics_uses_bumper";
	private static final String USES_BOMB = "statistics_uses_bomb";
	
	private static boolean initialized = false;
	
	/**
	 * Loads statistics from preferences
	 * @param sharedPreferences preferences to load from
	 */
	public static void load(SharedPreferences sharedPreferences) {
		
		initialized = true;
		
		timePlayed = sharedPreferences.getInt(TIME_PLAYED, 0);
		
		asteroidsDestroyedByDash = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_DASH, 0);
		asteroidsDestroyedByDrill = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_DRILL, 0);
		asteroidsDestroyedByMagnet = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_MAGNET, 0);
		asteroidsDestroyedByWhiteHole = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_WHITE_HOLE, 0);
		asteroidsDestroyedByBumper = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_BUMPER, 0);
		asteroidsDestroyedByBomb = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_BOMB, 0);
		
		usesDash = sharedPreferences.getInt(USES_DASH, 0);
		usesSmall = sharedPreferences.getInt(USES_SMALL, 0);
		usesSlow = sharedPreferences.getInt(USES_SLOW, 0);
		usesInvulnerability = sharedPreferences.getInt(USES_INVULNERABILITY, 0);
		usesDrill = sharedPreferences.getInt(USES_DRILL, 0);
		usesMagnet = sharedPreferences.getInt(USES_MAGNET, 0);
		usesWhiteHole = sharedPreferences.getInt(USES_WHITE_HOLE, 0);
		usesBumper = sharedPreferences.getInt(USES_BUMPER, 0);
		usesBomb = sharedPreferences.getInt(USES_BOMB, 0);
	}
	
	/**
	 * Saves statistics to preferences
	 * @param sharedPreferencesEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor sharedPreferencesEditor) {
		sharedPreferencesEditor.putInt(TIME_PLAYED, timePlayed);
		
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_DASH, asteroidsDestroyedByDash);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_DRILL, asteroidsDestroyedByDrill);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_MAGNET, asteroidsDestroyedByMagnet);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_WHITE_HOLE, asteroidsDestroyedByWhiteHole);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_BUMPER, asteroidsDestroyedByBumper);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_BOMB, asteroidsDestroyedByBomb);
		
		sharedPreferencesEditor.putInt(USES_DASH, usesDash);
		sharedPreferencesEditor.putInt(USES_SMALL, usesSmall);
		sharedPreferencesEditor.putInt(USES_SLOW, usesSlow);
		sharedPreferencesEditor.putInt(USES_INVULNERABILITY, usesInvulnerability);
		sharedPreferencesEditor.putInt(USES_DRILL, usesDrill);
		sharedPreferencesEditor.putInt(USES_MAGNET, usesMagnet);
		sharedPreferencesEditor.putInt(USES_WHITE_HOLE, usesWhiteHole);
		sharedPreferencesEditor.putInt(USES_BUMPER, usesBumper);
		sharedPreferencesEditor.putInt(USES_BOMB, usesBomb);
	}
	
	/**
	 * Returns true if the global statistics were initialized from application preferences
	 * @return true if the global statistics were initialized from application preferences
	 */
	public static boolean initialized() {
		return initialized;
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
		
		usesDash += LocalStatistics.usesDash;
		usesSmall += LocalStatistics.usesSmall;
		usesSlow += LocalStatistics.usesSlow;
		usesInvulnerability += LocalStatistics.usesInvulnerability;
		usesDrill += LocalStatistics.usesDrill;
		usesMagnet += LocalStatistics.usesMagnet;
		usesWhiteHole += LocalStatistics.usesWhiteHole;
		usesBumper += LocalStatistics.usesBumper;
		usesBomb += LocalStatistics.usesBomb;
		
		timePlayed += LocalStatistics.timePlayed;
	}
	
	/**
	 * Returns statistic associated with given id
	 * @param id id to check
	 * @return statistic associated with given id
	 */
	public static int getStatistic(int id) {
		switch(id) {
		case ID_TIME_PLAYED:
			return timePlayed/60;
		case ID_ASTEROIDS_DESTROYED_BY_DASH:
			return asteroidsDestroyedByDash;
		case ID_ASTEROIDS_DESTROYED_BY_DRILL:
			return asteroidsDestroyedByDrill;
		case ID_ASTEROIDS_DESTROYED_BY_MAGNET:
			return asteroidsDestroyedByMagnet;
		case ID_ASTEROIDS_DESTROYED_BY_WHITE_HOLE:
			return asteroidsDestroyedByWhiteHole;
		case ID_ASTEROIDS_DESTROYED_BY_BUMPER:
			return asteroidsDestroyedByBumper;
		case ID_ASTEROIDS_DESTROYED_BY_BOMB:
			return asteroidsDestroyedByBomb;
		case ID_ASTEROIDS_DESTROYED_TOTAL:
			return asteroidsDestroyedByDash +
					asteroidsDestroyedByDrill +
					asteroidsDestroyedByMagnet +
					asteroidsDestroyedByWhiteHole +
					asteroidsDestroyedByBumper +
					asteroidsDestroyedByBomb;
		case ID_USES_DASH:
			return usesDash;
		case ID_USES_SMALL:
			return usesSmall;
		case ID_USES_SLOW:
			return usesSlow;
		case ID_USES_INVULNERABILITY:
			return usesInvulnerability;
		case ID_USES_DRILL:
			return usesDrill;
		case ID_USES_MAGNET:
			return usesMagnet;
		case ID_USES_WHITE_HOLE:
			return usesWhiteHole;
		case ID_USES_BUMPER:
			return usesBumper;
		case ID_USES_BOMB:
			return usesBomb;
		default:
			return -1;
		}
	}

	/**
	 * Resets all global statistics to 0
	 * @param preferencesEditor preferences to save to
	 */
	public static void reset(SharedPreferences.Editor sharedPreferencesEditor) {
		reset();
		save(sharedPreferencesEditor);
	}
}
