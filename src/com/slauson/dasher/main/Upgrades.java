package com.slauson.dasher.main;

import android.content.SharedPreferences;

/**
 * Contains upgrades to powerups/player
 * @author Josh Slauson
 *
 */
public class Upgrades {
	
	// TODO: transfer levels into powerups themselves, then compare against constants
	// TODO: have description string of each upgrade?

	// constants to represent what powerup upgrade levels mean
	public static int DASH_UPGRADE_REDUCED_RECHARGE_1 = 1;
	public static int DASH_UPGRADE_REDUCED_RECHARGE_2 = 2;
	public static int DASH_UPGRADE_REDUCED_RECHARGE_3 = 3;
	public static int DASH_UPGRADE_SPAWN_POWERUP = 4;
	
	public static int SMALL_UPGRADE_INCREASED_DURATION_1 = 1;
	public static int SMALL_UPGRADE_INCREASED_DURATION_2 = 2;
	public static int SMALL_UPGRADE_INCREASED_DURATION_3 = 3;
	public static int SMALL_UPGRADE_QUARTER_SIZE = 4;
	
	public static int SLOW_UPGRADE_INCREASED_DURATION_1 = 1;
	public static int SLOW_UPGRADE_INCREASED_DURATION_2 = 2;
	public static int SLOW_UPGRADE_INCREASED_DURATION_3 = 3;
	public static int SLOW_UPGRADE_QUARTER_TIME = 4;
	
	public static int INVULNERABILITY_UPGRADE_INCREASED_DURATION_1 = 1;
	public static int INVULNERABILITY_UPGRADE_INCREASED_DURATION_2 = 2;
	public static int INVULNERABILITY_UPGRADE_INCREASED_DURATION_3 = 3;
	public static int INVULNERABILITY_UPGRADE_ACTIVATE_DROPS = 4;
	
	public static int DRILL_UPGRADE_SEEK_1 = 1;
	public static int DRILL_UPGRADE_SEEK_2 = 2;
	public static int DRILL_UPGRADE_SEEK_3 = 3;
	public static int DRILL_UPGRADE_TELEPORT = 4;
	
	public static int MAGNET_UPGRADE_INCREASED_DURATION_1 = 1;
	public static int MAGNET_UPGRADE_INCREASED_DURATION_2 = 2;
	public static int MAGNET_UPGRADE_INCREASED_DURATION_3 = 3;
	public static int MAGNET_UPGRADE_INCREASED_RANGE = 4;
	public static int MAGNET_UPGRADE_COLLECT_DROPS = 5;
	
	public static int WHITE_HOLE_UPGRADE_INCREASED_DURATION_1 = 1;
	public static int WHITE_HOLE_UPGRADE_INCREASED_DURATION_2 = 2;
	public static int WHITE_HOLE_UPGRADE_INCREASED_DURATION_3 = 3;
	public static int WHITE_HOLE_UPGRADE_INCREASED_RANGE = 4;
	public static int WHITE_HOLE_UPGRADE_COLLECT_DROPS = 5;
	
	public static int BUMPER_UPGRADE_INCREASED_DURATION_1 = 1;
	public static int BUMPER_UPGRADE_INCREASED_DURATION_2 = 2;
	public static int BUMPER_UPGRADE_INCREASED_DURATION_3 = 3;
	public static int BUMPER_UPGRADE_INCREASED_SIZE = 4;
	
	public static int BOMB_UPGRADE_NO_EFFECT_DROPS = 1;
	public static int BOMB_UPGRADE_NO_EFFECT_POWERUPS = 2;
	public static int BOMB_UPGRADE_CAUSE_DROPS = 3;
	
	// actual upgrades
	public Upgrade dashUpgrade = new Upgrade("upgrade_dash");
	public Upgrade smallUpgrade = new Upgrade("upgrade_small");
	public Upgrade slowUpgrade = new Upgrade("upgrade_slow");
	public Upgrade invulnerabilityUpgrade = new Upgrade("upgrade_invulnerability");
	public Upgrade drillUpgrade = new Upgrade("upgrade_drill");
	public Upgrade magnetUpgrade = new Upgrade("upgrade_magnet");
	public Upgrade whiteHoleUpgrade = new Upgrade("upgrade_white_hole");
	public Upgrade bumperUpgrade = new Upgrade("upgrade_bumper");
	public Upgrade bombUpgrade = new Upgrade("upgrade_bomb");
	
	/**
	 * Loads upgrades from application preferences
	 * @param preferences preferences to load from
	 */
	public void load(SharedPreferences preferences) {
		dashUpgrade.load(preferences);
		smallUpgrade.load(preferences);
		slowUpgrade.load(preferences);
		invulnerabilityUpgrade.load(preferences);
		drillUpgrade.load(preferences);
		magnetUpgrade.load(preferences);
		whiteHoleUpgrade.load(preferences);
		bumperUpgrade.load(preferences);
		bombUpgrade.load(preferences);
	}
	
	/**
	 * Saves upgrades to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor) {
		dashUpgrade.save(preferencesEditor);
		smallUpgrade.save(preferencesEditor);
		slowUpgrade.save(preferencesEditor);
		invulnerabilityUpgrade.save(preferencesEditor);
		drillUpgrade.save(preferencesEditor);
		magnetUpgrade.save(preferencesEditor);
		whiteHoleUpgrade.save(preferencesEditor);
		bumperUpgrade.save(preferencesEditor);
		bombUpgrade.save(preferencesEditor);
	}
}
