package com.slauson.dasher.status;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.res.Resources;

/**
 * Contains upgrades to powerups/player
 * @author Josh Slauson
 *
 */
public class Upgrades {
	
	// constants for upgrade costs
	public static final int POINTS_UPGRADE_1 = 1000;
	public static final int POINTS_UPGRADE_2 = 2500;
	public static final int POINTS_UPGRADE_3 = 5000;
	public static final int POINTS_UPGRADE_4 = 10000;
	
	// constants to represent what powerup upgrade levels mean
	public static final int DASH_UPGRADE_REDUCED_RECHARGE_1 = 1;
	public static final int DASH_UPGRADE_REDUCED_RECHARGE_2 = 2;
	public static final int DASH_UPGRADE_REDUCED_RECHARGE_3 = 3;
	public static final int DASH_UPGRADE_MULTIPLE_POWERUPS = 4;
	
	public static final int SMALL_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int SMALL_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int SMALL_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int SMALL_UPGRADE_QUARTER_SIZE = 4;
	
	public static final int SLOW_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int SLOW_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int SLOW_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int SLOW_UPGRADE_QUARTER_SPEED = 4;
	
	public static final int INVULNERABILITY_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int INVULNERABILITY_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int INVULNERABILITY_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int INVULNERABILITY_UPGRADE_DASHER = 4;
	
	public static final int DRILL_UPGRADE_SEEK_1 = 1;
	public static final int DRILL_UPGRADE_SEEK_2 = 2;
	public static final int DRILL_UPGRADE_SEEK_3 = 3;
	public static final int DRILL_UPGRADE_TELEPORT = 4;
	
	public static final int MAGNET_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int MAGNET_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int MAGNET_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int MAGNET_UPGRADE_SPIN = 4;
	
	public static final int WHITE_HOLE_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int WHITE_HOLE_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int WHITE_HOLE_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int WHITE_HOLE_UPGRADE_QUASAR = 4;
	
	public static final int BUMPER_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int BUMPER_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int BUMPER_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int BUMPER_UPGRADE_INCREASED_SIZE = 4;
	
	public static final int BOMB_UPGRADE_NO_EFFECT_DROPS = 1;
	public static final int BOMB_UPGRADE_NO_EFFECT_POWERUPS = 2;
	public static final int BOMB_UPGRADE_CAUSE_DROP = 3;
	public static final int BOMB_UPGRADE_CAUSE_DROPS = 4;
	
	// actual upgrades
	public static Upgrade dashUpgrade = new Upgrade("upgrade_dash");
	public static Upgrade smallUpgrade = new Upgrade("upgrade_small");
	public static Upgrade slowUpgrade = new Upgrade("upgrade_slow");
	public static Upgrade invulnerabilityUpgrade = new Upgrade("upgrade_invulnerability");
	public static Upgrade drillUpgrade = new Upgrade("upgrade_drill");
	public static Upgrade magnetUpgrade = new Upgrade("upgrade_magnet");
	public static Upgrade whiteHoleUpgrade = new Upgrade("upgrade_white_hole");
	public static Upgrade bumperUpgrade = new Upgrade("upgrade_bumper");
	public static Upgrade bombUpgrade = new Upgrade("upgrade_bomb");
	
	// array of upgrades
	private static ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	
	// populate array
	static {
		upgrades.add(dashUpgrade);
		upgrades.add(smallUpgrade);
		upgrades.add(slowUpgrade);
		upgrades.add(invulnerabilityUpgrade);
		upgrades.add(drillUpgrade);
		upgrades.add(magnetUpgrade);
		upgrades.add(whiteHoleUpgrade);
		upgrades.add(bumperUpgrade);
		upgrades.add(bombUpgrade);
	}
	
	/**
	 * Loads upgrades from application preferences
	 * @param preferences preferences to load from
	 */
	public static void load(SharedPreferences preferences) {
		for (Upgrade upgrade : upgrades) {
			upgrade.load(preferences);
		}
	}
	
	/**
	 * Saves upgrades to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor preferencesEditor) {
		for (Upgrade upgrade : upgrades) {
			upgrade.save(preferencesEditor);
		}
	}
	
	/**
	 * Loads resources for upgrades
	 * @param resources resources to use
	 * @param packageName package name
	 */
	public static void loadResources(Resources resources, String packageName) {
		for (Upgrade upgrade : upgrades) {
			upgrade.loadResources(resources, packageName);
		}
	}
	
	/**
	 * Returns upgrade id
	 * @param upgrade upgrade to get id of
	 * @return upgrade id
	 */
	public static int getUpgradeID(Upgrade upgrade) {
		return upgrades.indexOf(upgrade);
	}
	
	/**
	 * Returns upgrade associated with given id
	 * @param id upgrade id
	 * @return upgrade associated with given id
	 */
	public static Upgrade getUpgrade(int id) {
		if (id < 0 || id >= upgrades.size()) {
			return null;
		}
		
		return upgrades.get(id);
	}

}
