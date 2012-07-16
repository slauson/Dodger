package com.slauson.dasher.status;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	public static final int SMALL_UPGRADE_BIG_DASH = 4;
	
	public static final int SLOW_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int SLOW_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int SLOW_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int SLOW_UPGRADE_NO_AFFECT_DROPS_AND_POWERUPS = 4;
	
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
	
	public static final int BLACK_HOLE_UPGRADE_INCREASED_DURATION_1 = 1;
	public static final int BLACK_HOLE_UPGRADE_INCREASED_DURATION_2 = 2;
	public static final int BLACK_HOLE_UPGRADE_INCREASED_DURATION_3 = 3;
	public static final int BLACK_HOLE_UPGRADE_QUASAR = 4;
	
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
	public static Upgrade blackHoleUpgrade = new Upgrade("upgrade_black_hole");
	public static Upgrade bumperUpgrade = new Upgrade("upgrade_bumper");
	public static Upgrade bombUpgrade = new Upgrade("upgrade_bomb");
	
	private static boolean initialized = false;
	
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
		upgrades.add(blackHoleUpgrade);
		upgrades.add(bumperUpgrade);
		upgrades.add(bombUpgrade);
	}
	
	/**
	 * Loads upgrades from application preferences
	 * @param sharedPreferences preferences to load from
	 */
	public static void load(SharedPreferences sharedPreferences) {
		for (Upgrade upgrade : upgrades) {
			upgrade.load(sharedPreferences);
		}
	}
	
	/**
	 * Saves upgrades to application preferences
	 * @param sharedPreferencesEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor sharedPreferencesEditor) {
		for (Upgrade upgrade : upgrades) {
			upgrade.save(sharedPreferencesEditor);
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

	/**
	 * Returns true if the upgrades were initialized from application preferences
	 * @return true if the upgrades were initialized from application preferences
	 */
	public static boolean initialized() {
		return initialized;
	}

	/**
	 * Resets all upgrades back to 0
	 * @param sharedPreferencesEditor preferences to save to
	 */
	public static void reset(Editor sharedPreferencesEditor) {
		
		for (Upgrade upgrade : upgrades) {
			upgrade.setLevel(0);
		}
		
		save(sharedPreferencesEditor);
	}

	/**
	 * Checks upgrade related achievements
	 */
	public static void checkAchievements() {
		
		// check if all powerups are unlocked
		// TODO
		
		// check if all upgrades are purchased
		int i;
		for (i = 0; i < upgrades.size(); i++) {
			if (upgrades.get(i).getLevel() < 4) {
				break;
			}
		}
		if (i == upgrades.size()) {
			Achievements.globalPurchaseAllUpgrades.unlock();
		}
	}

}
