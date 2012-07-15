package com.slauson.dasher.status;


import com.slauson.dasher.other.Util;

import android.content.SharedPreferences;

/**
 * Keeps track of global statistics
 * @author Josh Slauson
 *
 */
public class GlobalStatistics {

	// public constants
	
	// ids for achievements associated with statistics
	public static final int ID_TIME_PLAYED = 0;
	public static final int ID_TIMES_PLAYED = 1;
	
	public static final int ID_ASTEROIDS_DESTROYED_BY_DASH = 2;
	public static final int ID_ASTEROIDS_DESTROYED_BY_DRILL = 3;
	public static final int ID_ASTEROIDS_DESTROYED_BY_MAGNET = 4;
	public static final int ID_ASTEROIDS_DESTROYED_BY_BLACK_HOLE = 5;
	public static final int ID_ASTEROIDS_DESTROYED_BY_BUMPER = 6;
	public static final int ID_ASTEROIDS_DESTROYED_BY_BOMB = 7;
	public static final int ID_ASTEROIDS_DESTROYED_TOTAL = 8;
	
	public static final int ID_USES_DASH = 9;
	public static final int ID_USES_SMALL = 10;
	public static final int ID_USES_SLOW = 11;
	public static final int ID_USES_INVULNERABILITY = 12;
	public static final int ID_USES_DRILL = 13;
	public static final int ID_USES_MAGNET = 14;
	public static final int ID_USES_BLACK_HOLE = 15;
	public static final int ID_USES_BUMPER = 16;
	public static final int ID_USES_BOMB = 17;
	
	// private constants
	private static final String TIME_PLAYED = "statistics_time_played";
	private static final String TIMES_PLAYED = "statistics_times_played";
	
	private static final String ASTEROIDS_DESTROYED_BY_DASH = "statistics_asteroids_destroyed_by_dash";
	private static final String ASTEROIDS_DESTROYED_BY_DRILL = "statistics_asteroids_destroyed_by_drill";
	private static final String ASTEROIDS_DESTROYED_BY_MAGNET = "statistics_asteroids_destroyed_by_magnet";
	private static final String ASTEROIDS_DESTROYED_BY_BLACK_HOLE = "statistics_asteroids_destroyed_by_black_hole";
	private static final String ASTEROIDS_DESTROYED_BY_BUMPER = "statistics_asteroids_destroyed_by_bumper";
	private static final String ASTEROIDS_DESTROYED_BY_BOMB = "statistics_asteroids_destroyed_by_bomb";
	
	private static final String USES_DASH = "statistics_uses_dash";
	private static final String USES_SMALL = "statistics_uses_small";
	private static final String USES_SLOW = "statistics_uses_slow";
	private static final String USES_INVULNERABILITY = "statistics_uses_invulnerability";
	private static final String USES_DRILL = "statistics_uses_drill";
	private static final String USES_MAGNET = "statistics_uses_magnet";
	private static final String USES_BLACK_HOLE = "statistics_uses_black_hole";
	private static final String USES_BUMPER = "statistics_uses_bumper";
	private static final String USES_BOMB = "statistics_uses_bomb";
	
	private static boolean initialized = false;
	private static Statistics statistics = new Statistics();
	
	/**
	 * Returns statistics instance
	 * @return statistics instance
	 */
	public static Statistics getInstance() {
		return statistics;
	}

	/**
	 * Loads statistics from preferences
	 * @param sharedPreferences preferences to load from
	 */
	public static void load(SharedPreferences sharedPreferences) {
		
		initialized = true;
		
		statistics.timePlayed = sharedPreferences.getInt(TIME_PLAYED, 0);
		statistics.timesPlayed = sharedPreferences.getInt(TIMES_PLAYED, 0);
		
		statistics.asteroidsDestroyedByDash = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_DASH, 0);
		statistics.asteroidsDestroyedByDrill = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_DRILL, 0);
		statistics.asteroidsDestroyedByMagnet = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_MAGNET, 0);
		statistics.asteroidsDestroyedByBlackHole = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_BLACK_HOLE, 0);
		statistics.asteroidsDestroyedByBumper = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_BUMPER, 0);
		statistics.asteroidsDestroyedByBomb = sharedPreferences.getInt(ASTEROIDS_DESTROYED_BY_BOMB, 0);
		
		statistics.usesDash = sharedPreferences.getInt(USES_DASH, 0);
		statistics.usesSmall = sharedPreferences.getInt(USES_SMALL, 0);
		statistics.usesSlow = sharedPreferences.getInt(USES_SLOW, 0);
		statistics.usesInvulnerability = sharedPreferences.getInt(USES_INVULNERABILITY, 0);
		statistics.usesDrill = sharedPreferences.getInt(USES_DRILL, 0);
		statistics.usesMagnet = sharedPreferences.getInt(USES_MAGNET, 0);
		statistics.usesBlackHole = sharedPreferences.getInt(USES_BLACK_HOLE, 0);
		statistics.usesBumper = sharedPreferences.getInt(USES_BUMPER, 0);
		statistics.usesBomb = sharedPreferences.getInt(USES_BOMB, 0);
	}
	
	/**
	 * Saves statistics to preferences
	 * @param sharedPreferencesEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor sharedPreferencesEditor) {
		sharedPreferencesEditor.putInt(TIME_PLAYED, statistics.timePlayed);
		sharedPreferencesEditor.putInt(TIMES_PLAYED, statistics.timesPlayed);
		
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_DASH, statistics.asteroidsDestroyedByDash);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_DRILL, statistics.asteroidsDestroyedByDrill);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_MAGNET, statistics.asteroidsDestroyedByMagnet);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_BLACK_HOLE, statistics.asteroidsDestroyedByBlackHole);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_BUMPER, statistics.asteroidsDestroyedByBumper);
		sharedPreferencesEditor.putInt(ASTEROIDS_DESTROYED_BY_BOMB, statistics.asteroidsDestroyedByBomb);
		
		sharedPreferencesEditor.putInt(USES_DASH, statistics.usesDash);
		sharedPreferencesEditor.putInt(USES_SMALL, statistics.usesSmall);
		sharedPreferencesEditor.putInt(USES_SLOW, statistics.usesSlow);
		sharedPreferencesEditor.putInt(USES_INVULNERABILITY, statistics.usesInvulnerability);
		sharedPreferencesEditor.putInt(USES_DRILL, statistics.usesDrill);
		sharedPreferencesEditor.putInt(USES_MAGNET, statistics.usesMagnet);
		sharedPreferencesEditor.putInt(USES_BLACK_HOLE, statistics.usesBlackHole);
		sharedPreferencesEditor.putInt(USES_BUMPER, statistics.usesBumper);
		sharedPreferencesEditor.putInt(USES_BOMB, statistics.usesBomb);
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
		
		Statistics localStatistics = LocalStatistics.getInstance();
		
		statistics.asteroidsDestroyedByDash += localStatistics.asteroidsDestroyedByDash;
		statistics.asteroidsDestroyedByDrill += localStatistics.asteroidsDestroyedByDrill;
		statistics.asteroidsDestroyedByMagnet += localStatistics.asteroidsDestroyedByMagnet;
		statistics.asteroidsDestroyedByBlackHole += localStatistics.asteroidsDestroyedByBlackHole;
		statistics.asteroidsDestroyedByBumper += localStatistics.asteroidsDestroyedByBumper;
		statistics.asteroidsDestroyedByBomb += localStatistics.asteroidsDestroyedByBomb;
		
		statistics.usesDash += localStatistics.usesDash;
		statistics.usesSmall += localStatistics.usesSmall;
		statistics.usesSlow += localStatistics.usesSlow;
		statistics.usesInvulnerability += localStatistics.usesInvulnerability;
		statistics.usesDrill += localStatistics.usesDrill;
		statistics.usesMagnet += localStatistics.usesMagnet;
		statistics.usesBlackHole += localStatistics.usesBlackHole;
		statistics.usesBumper += localStatistics.usesBumper;
		statistics.usesBomb += localStatistics.usesBomb;
		
		statistics.timePlayed += localStatistics.timePlayed;
		statistics.timesPlayed++;
	}
	
	/**
	 * Returns statistic associated with given id
	 * @param id id to check
	 * @return statistic associated with given id
	 */
	public static int getStatistic(int id) {
		switch(id) {
		case ID_TIME_PLAYED:
			return statistics.timePlayed/60;
		case ID_TIMES_PLAYED:
			return statistics.timesPlayed;
		case ID_ASTEROIDS_DESTROYED_BY_DASH:
			return statistics.asteroidsDestroyedByDash;
		case ID_ASTEROIDS_DESTROYED_BY_DRILL:
			return statistics.asteroidsDestroyedByDrill;
		case ID_ASTEROIDS_DESTROYED_BY_MAGNET:
			return statistics.asteroidsDestroyedByMagnet;
		case ID_ASTEROIDS_DESTROYED_BY_BLACK_HOLE:
			return statistics.asteroidsDestroyedByBlackHole;
		case ID_ASTEROIDS_DESTROYED_BY_BUMPER:
			return statistics.asteroidsDestroyedByBumper;
		case ID_ASTEROIDS_DESTROYED_BY_BOMB:
			return statistics.asteroidsDestroyedByBomb;
		case ID_ASTEROIDS_DESTROYED_TOTAL:
			return statistics.asteroidsDestroyedByDash +
					statistics.asteroidsDestroyedByDrill +
					statistics.asteroidsDestroyedByMagnet +
					statistics.asteroidsDestroyedByBlackHole +
					statistics.asteroidsDestroyedByBumper +
					statistics.asteroidsDestroyedByBomb;
		case ID_USES_DASH:
			return statistics.usesDash;
		case ID_USES_SMALL:
			return statistics.usesSmall;
		case ID_USES_SLOW:
			return statistics.usesSlow;
		case ID_USES_INVULNERABILITY:
			return statistics.usesInvulnerability;
		case ID_USES_DRILL:
			return statistics.usesDrill;
		case ID_USES_MAGNET:
			return statistics.usesMagnet;
		case ID_USES_BLACK_HOLE:
			return statistics.usesBlackHole;
		case ID_USES_BUMPER:
			return statistics.usesBumper;
		case ID_USES_BOMB:
			return statistics.usesBomb;
		default:
			return -1;
		}
	}

	/**
	 * Resets all global statistics to 0
	 * @param preferencesEditor preferences to save to
	 */
	public static void reset(SharedPreferences.Editor sharedPreferencesEditor) {
		
		statistics.reset();
		save(sharedPreferencesEditor);
	}
	
	public static String getAverageTimePerPlayString() {
		if (statistics.timesPlayed != 0) {
			return Util.getTimeString(statistics.timePlayed/statistics.timesPlayed);
		}
		
		return Util.getTimeString(0);
	}
}
