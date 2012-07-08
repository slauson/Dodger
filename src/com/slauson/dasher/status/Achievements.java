package com.slauson.dasher.status;

import java.util.ArrayList;
import java.util.List;

import com.slauson.dasher.R;

import android.content.SharedPreferences;
import android.content.res.Resources;

/**
 * Achievements
 * @author Josh Slauson
 *
 */
public class Achievements {
	
	// constants
	public static final int LOCAL_DESTROY_ASTEROIDS_NUM_1 = 5;
	public static final int LOCAL_DESTROY_ASTEROIDS_NUM_2 = 10;
	public static final int LOCAL_DESTROY_ASTEROIDS_NUM_3 = 15;
	
	public static final int LOCAL_INVULNERABILITY_PASS_THROUGH_NUM = 10;
	public static final int LOCAL_MAGNET_HOLD_IN_PLACE_NUM = 1000;
	public static final int LOCAL_BOMB_DESTROY_DROPS_NUM = 3;
	public static final int LOCAL_DASH_ACTIVATE_DROPS_NUM = 3;
	public static final int LOCAL_OTHER_STAY_IN_PLACE_NUM = 20;
	
	public static final int LOCAL_PLAYTIME_1 = 60;
	public static final int LOCAL_PLAYTIME_2 = 300;
	public static final int LOCAL_PLAYTIME_3 = 600;
	
	public static final int GLOBAL_DESTROY_ASTEROIDS_NUM_1 = 25;
	public static final int GLOBAL_DESTROY_ASTEROIDS_NUM_2 = 100;
	public static final int GLOBAL_DESTROY_ASTEROIDS_NUM_3 = 500;
	
	public static final int GLOBAL_DESTROY_ASTEROIDS_COLLISION_NUM_1 = 250;
	public static final int GLOBAL_DESTROY_ASTEROIDS_COLLISION_NUM_2 = 1000;
	public static final int GLOBAL_DESTROY_ASTEROIDS_COLLISION_NUM_3 = 5000;
	
	public static final int GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_1 = 250;
	public static final int GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_2 = 1000;
	public static final int GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_3 = 5000;
	
	public static final int GLOBAL_PLAYTIME_1 = 600;
	public static final int GLOBAL_PLAYTIME_2 = 3600;
	public static final int GLOBAL_PLAYTIME_3 = 36000;
	
	// local achievments - destroy asteroids
	public static Achievement localDestroyAsteroidsWithDash1 = new Achievement("achievement_local_destroy_asteroids_with_dash_1", R.drawable.powerup_ship, "" + LOCAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement localDestroyAsteroidsWithDash2 = new Achievement("achievement_local_destroy_asteroids_with_dash_2", R.drawable.powerup_ship, "" + LOCAL_DESTROY_ASTEROIDS_NUM_2);
	public static Achievement localDestroyAsteroidsWithDash3 = new Achievement("achievement_local_destroy_asteroids_with_dash_3", R.drawable.powerup_ship, "" + LOCAL_DESTROY_ASTEROIDS_NUM_3);
	
	public static Achievement localDestroyAsteroidsWithDrill1 = new Achievement("achievement_local_destroy_asteroids_with_drill_1", R.drawable.powerup_drill, "" + LOCAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement localDestroyAsteroidsWithDrill2 = new Achievement("achievement_local_destroy_asteroids_with_drill_2", R.drawable.powerup_drill, "" + LOCAL_DESTROY_ASTEROIDS_NUM_2);
	public static Achievement localDestroyAsteroidsWithDrill3 = new Achievement("achievement_local_destroy_asteroids_with_drill_3", R.drawable.powerup_drill, "" + LOCAL_DESTROY_ASTEROIDS_NUM_3);
	
	public static Achievement localDestroyAsteroidsWithMagnet1 = new Achievement("achievement_local_destroy_asteroids_with_magnet_1", R.drawable.powerup_magnet, "" + LOCAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement localDestroyAsteroidsWithMagnet2 = new Achievement("achievement_local_destroy_asteroids_with_magnet_2", R.drawable.powerup_magnet, "" + LOCAL_DESTROY_ASTEROIDS_NUM_2);
	public static Achievement localDestroyAsteroidsWithMagnet3 = new Achievement("achievement_local_destroy_asteroids_with_magnet_3", R.drawable.powerup_magnet, "" + LOCAL_DESTROY_ASTEROIDS_NUM_3);
	
	public static Achievement localDestroyAsteroidsWithWhiteHole1 = new Achievement("achievement_local_destroy_asteroids_with_white_hole_1", R.drawable.powerup_white_hole, "" + LOCAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement localDestroyAsteroidsWithWhiteHole2 = new Achievement("achievement_local_destroy_asteroids_with_white_hole_2", R.drawable.powerup_white_hole, "" + LOCAL_DESTROY_ASTEROIDS_NUM_2);
	public static Achievement localDestroyAsteroidsWithWhiteHole3 = new Achievement("achievement_local_destroy_asteroids_with_white_hole_3", R.drawable.powerup_white_hole, "" + LOCAL_DESTROY_ASTEROIDS_NUM_3);
	
	public static Achievement localDestroyAsteroidsWithBumper1 = new Achievement("achievement_local_destroy_asteroids_with_bumper_1", R.drawable.powerup_bumper, "" + LOCAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement localDestroyAsteroidsWithBumper2 = new Achievement("achievement_local_destroy_asteroids_with_bumper_2", R.drawable.powerup_bumper, "" + LOCAL_DESTROY_ASTEROIDS_NUM_2);
	public static Achievement localDestroyAsteroidsWithBumper3 = new Achievement("achievement_local_destroy_asteroids_with_bumper_3", R.drawable.powerup_bumper, "" + LOCAL_DESTROY_ASTEROIDS_NUM_3);
	
	public static Achievement localDestroyAsteroidsWithBomb1 = new Achievement("achievement_local_destroy_asteroids_with_bomb_1", R.drawable.powerup_bomb, "" + LOCAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement localDestroyAsteroidsWithBomb2 = new Achievement("achievement_local_destroy_asteroids_with_bomb_2", R.drawable.powerup_bomb, "" + LOCAL_DESTROY_ASTEROIDS_NUM_2);
	public static Achievement localDestroyAsteroidsWithBomb3 = new Achievement("achievement_local_destroy_asteroids_with_bomb_3", R.drawable.powerup_bomb, "" + LOCAL_DESTROY_ASTEROIDS_NUM_3);

	// local achievements - powerups
	// TODO: implement these
	public static Achievement localSlowBeforeDestroyed = new Achievement("achievement_local_slow_before_destroyed", R.drawable.powerup_slow);
	public static Achievement localInvulnerabilityPassThrough = new Achievement("achievement_local_invulnerability_pass_through", R.drawable.powerup_invulnerable, "" + LOCAL_INVULNERABILITY_PASS_THROUGH_NUM);
	public static Achievement localDrillUseMaximumTime = new Achievement("achievement_local_drill_use_maximum_time", R.drawable.powerup_drill);
	public static Achievement localWhiteHolePlayerDestroyed = new Achievement("achievement_local_white_hole_player_destroyed", R.drawable.powerup_white_hole);
	public static Achievement localMagnetHoldInPlace = new Achievement("achievement_local_magnet_hold_in_place", R.drawable.powerup_magnet, "" + LOCAL_MAGNET_HOLD_IN_PLACE_NUM);
	public static Achievement localBumperBetween = new Achievement("achievement_local_bumper_between", R.drawable.powerup_bumper);
	//public static Achievement localBombDestroyDrops = new Achievement("achievement_local_bomb_destroy_drops", R.drawable.powerup_bomb);
	public static Achievement localDashActivateDrops = new Achievement("achievement_local_dash_activate_drops", R.drawable.powerup_ship, "" + LOCAL_DASH_ACTIVATE_DROPS_NUM);
	public static Achievement localOtherStayInPlace = new Achievement("achievement_local_other_stay_in_place", R.drawable.powerup_ship, "" + LOCAL_OTHER_STAY_IN_PLACE_NUM);
	
	// local achievements - playtime
	public static Achievement localPlaytime1 = new Achievement("achievement_local_playtime_1", R.drawable.powerup_slow, "" + LOCAL_PLAYTIME_1/60);
	public static Achievement localPlaytime2 = new Achievement("achievement_local_playtime_2", R.drawable.powerup_slow, "" + LOCAL_PLAYTIME_2/60);
	public static Achievement localPlaytime3 = new Achievement("achievement_local_playtime_3", R.drawable.powerup_slow, "" + LOCAL_PLAYTIME_3/60);

	// global achievements - destroy asteroids
	public static Achievement globalDestroyAsteroidsWithDash1 = new Achievement("achievement_global_destroy_asteroids_with_dash_1", R.drawable.powerup_ship, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithDash2 = new Achievement("achievement_global_destroy_asteroids_with_dash_2", R.drawable.powerup_ship, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithDash3 = new Achievement("achievement_global_destroy_asteroids_with_dash_3", R.drawable.powerup_ship, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	
	public static Achievement globalDestroyAsteroidsWithDrill1 = new Achievement("achievement_global_destroy_asteroids_with_drill_1", R.drawable.powerup_drill, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithDrill2 = new Achievement("achievement_global_destroy_asteroids_with_drill_2", R.drawable.powerup_drill, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithDrill3 = new Achievement("achievement_global_destroy_asteroids_with_drill_3", R.drawable.powerup_drill, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	
	public static Achievement globalDestroyAsteroidsWithMagnet1 = new Achievement("achievement_global_destroy_asteroids_with_magnet_1", R.drawable.powerup_magnet, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithMagnet2 = new Achievement("achievement_global_destroy_asteroids_with_magnet_2", R.drawable.powerup_magnet, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithMagnet3 = new Achievement("achievement_global_destroy_asteroids_with_magnet_3", R.drawable.powerup_magnet, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	
	public static Achievement globalDestroyAsteroidsWithWhiteHole1 = new Achievement("achievement_global_destroy_asteroids_with_white_hole_1", R.drawable.powerup_white_hole, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithWhiteHole2 = new Achievement("achievement_global_destroy_asteroids_with_white_hole_2", R.drawable.powerup_white_hole, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithWhiteHole3 = new Achievement("achievement_global_destroy_asteroids_with_white_hole_3", R.drawable.powerup_white_hole, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	
	public static Achievement globalDestroyAsteroidsWithBumper1 = new Achievement("achievement_global_destroy_asteroids_with_bumper_1", R.drawable.powerup_bumper, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithBumper2 = new Achievement("achievement_global_destroy_asteroids_with_bumper_2", R.drawable.powerup_bumper, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithBumper3 = new Achievement("achievement_global_destroy_asteroids_with_bumper_3", R.drawable.powerup_bumper, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	
	public static Achievement globalDestroyAsteroidsWithBomb1 = new Achievement("achievement_global_destroy_asteroids_with_bomb_1", R.drawable.powerup_bomb, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithBomb2 = new Achievement("achievement_global_destroy_asteroids_with_bomb_2", R.drawable.powerup_bomb, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	public static Achievement globalDestroyAsteroidsWithBomb3 = new Achievement("achievement_global_destroy_asteroids_with_bomb_3", R.drawable.powerup_bomb, "" + GLOBAL_DESTROY_ASTEROIDS_NUM_1);
	
	// global achievements - playtime
	public static Achievement globalPlaytime1 = new Achievement("achievement_global_playtime_1", R.drawable.powerup_slow, "" + GLOBAL_PLAYTIME_1/60);
	public static Achievement globalPlaytime2 = new Achievement("achievement_global_playtime_2", R.drawable.powerup_slow, "" + GLOBAL_PLAYTIME_2/60);
	public static Achievement globalPlaytime3 = new Achievement("achievement_global_playtime_3", R.drawable.powerup_slow, "" + GLOBAL_PLAYTIME_3/60);
	
	// list of local achievements unlocked during current playthrough
	public static ArrayList<Achievement> localAchievements = new ArrayList<Achievement>();

	// list of all achievements
	private static ArrayList<Achievement> achievements = new ArrayList<Achievement>();

	// populate list of all achievements
	static {
		
		achievements.add(localSlowBeforeDestroyed);
		achievements.add(localInvulnerabilityPassThrough);
		
		achievements.add(localDestroyAsteroidsWithDash1);
		achievements.add(localDestroyAsteroidsWithDash2);
		achievements.add(localDestroyAsteroidsWithDash3);
		achievements.add(localDashActivateDrops);
		achievements.add(localDestroyAsteroidsWithDrill1);
		achievements.add(localDestroyAsteroidsWithDrill2);
		achievements.add(localDestroyAsteroidsWithDrill3);
		achievements.add(localDrillUseMaximumTime);
		achievements.add(localDestroyAsteroidsWithMagnet1);
		achievements.add(localDestroyAsteroidsWithMagnet2);
		achievements.add(localDestroyAsteroidsWithMagnet3);
		achievements.add(localMagnetHoldInPlace);
		achievements.add(localDestroyAsteroidsWithWhiteHole1);
		achievements.add(localDestroyAsteroidsWithWhiteHole2);
		achievements.add(localDestroyAsteroidsWithWhiteHole3);
		achievements.add(localWhiteHolePlayerDestroyed);
		achievements.add(localDestroyAsteroidsWithBumper1);
		achievements.add(localDestroyAsteroidsWithBumper2);
		achievements.add(localDestroyAsteroidsWithBumper3);
		achievements.add(localBumperBetween);
		achievements.add(localDestroyAsteroidsWithBomb1);
		achievements.add(localDestroyAsteroidsWithBomb2);
		achievements.add(localDestroyAsteroidsWithBomb3);
		
		//achievements.add(localBombDestroyDrops);
		achievements.add(localOtherStayInPlace);
		
		achievements.add(localPlaytime1);
		achievements.add(localPlaytime2);
		achievements.add(localPlaytime3);
		
		achievements.add(globalDestroyAsteroidsWithDash1);
		achievements.add(globalDestroyAsteroidsWithDash2);
		achievements.add(globalDestroyAsteroidsWithDash3);
		achievements.add(globalDestroyAsteroidsWithDrill1);
		achievements.add(globalDestroyAsteroidsWithDrill2);
		achievements.add(globalDestroyAsteroidsWithDrill3);
		achievements.add(globalDestroyAsteroidsWithMagnet1);
		achievements.add(globalDestroyAsteroidsWithMagnet2);
		achievements.add(globalDestroyAsteroidsWithMagnet3);
		achievements.add(globalDestroyAsteroidsWithWhiteHole1);
		achievements.add(globalDestroyAsteroidsWithWhiteHole2);
		achievements.add(globalDestroyAsteroidsWithWhiteHole3);
		achievements.add(globalDestroyAsteroidsWithBumper1);
		achievements.add(globalDestroyAsteroidsWithBumper2);
		achievements.add(globalDestroyAsteroidsWithBumper3);
		achievements.add(globalDestroyAsteroidsWithBomb1);
		achievements.add(globalDestroyAsteroidsWithBomb2);
		achievements.add(globalDestroyAsteroidsWithBomb3);
		
		achievements.add(globalPlaytime1);
		achievements.add(globalPlaytime2);
		achievements.add(globalPlaytime3);
	}
	
	/**
	 * Updates global achievements based on GlobalStatistics
	 */
	public static void updateGlobalAchievements() {
		
		// destroy asteroids - dash
		if (GlobalStatistics.asteroidsDestroyedByDash >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithDash1.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByDash >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithDash2.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByDash >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithDash3.unlock();
		}
		
		// destroy asteroids - drill
		if (GlobalStatistics.asteroidsDestroyedByDrill >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithDrill1.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByDrill >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithDrill2.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByDrill >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithDrill3.unlock();
		}

		// destroy asteroids - magnet
		if (GlobalStatistics.asteroidsDestroyedByMagnet >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithMagnet1.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByMagnet >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithMagnet2.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByMagnet >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithMagnet3.unlock();
		}

		// destroy asteroids - white hole
		if (GlobalStatistics.asteroidsDestroyedByWhiteHole >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithWhiteHole1.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByWhiteHole >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithWhiteHole2.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByWhiteHole >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithWhiteHole3.unlock();
		}

		// destroy asteroids - bumper
		if (GlobalStatistics.asteroidsDestroyedByBumper >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithBumper1.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByBumper >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithBumper2.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByBumper >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithBumper3.unlock();
		}

		// destroy asteroids - bomb
		if (GlobalStatistics.asteroidsDestroyedByBomb >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithBomb1.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByBomb >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithBomb2.unlock();
		}
		if (GlobalStatistics.asteroidsDestroyedByBomb >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithBomb3.unlock();
		}
		
		// calculate total number of asteroids destroyed
		int asteroidsDestroyedTotal = GlobalStatistics.asteroidsDestroyedByBomb +
				GlobalStatistics.asteroidsDestroyedByBumper +
				GlobalStatistics.asteroidsDestroyedByDash +
				GlobalStatistics.asteroidsDestroyedByDrill +
				GlobalStatistics.asteroidsDestroyedByMagnet +
				GlobalStatistics.asteroidsDestroyedByWhiteHole;

		// destroy asteroids - total
		if (asteroidsDestroyedTotal >= GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_1) {
			globalDestroyAsteroidsWithDrill1.unlock();
		}
		if (asteroidsDestroyedTotal >= GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_2) {
			globalDestroyAsteroidsWithDrill2.unlock();
		}
		if (asteroidsDestroyedTotal >= GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_3) {
			globalDestroyAsteroidsWithDrill3.unlock();
		}
		
		// destroy asteroids - playtime
		if (GlobalStatistics.timePlayed >= GLOBAL_PLAYTIME_1) {
			globalPlaytime1.unlock();
		}
		if (GlobalStatistics.timePlayed >= GLOBAL_PLAYTIME_2) {
			globalPlaytime2.unlock();
		}
		if (GlobalStatistics.timePlayed >= GLOBAL_PLAYTIME_3) {
			globalPlaytime3.unlock();
		}
	}
	
	/**
	 * Loads achievement values from application preferences
	 * @param preferences preferences to load from
	 */
	public static void load(SharedPreferences preferences) {
		for (Achievement achievement : achievements) {
			achievement.load(preferences);
		}
	}
	
	/**
	 * Saves achievement values to application preferencesEditor
	 * @param preferenceEditor preferencesEditor to save to
	 */
	public static void save(SharedPreferences.Editor preferencesEditor) {
		for (Achievement achievement : achievements) {
			achievement.save(preferencesEditor);
		}
	}
	
	/**
	 * Adds achievement to list of local achievements
	 * @param achievement achievement to add
	 */
	public static void unlockLocalAchievement(Achievement achievement) {
		achievement.unlock();
		localAchievements.add(achievement);
	}

	/**
	 * Removes all achievements from list of local achievements
	 */
	public static void resetLocalAchievements() {
		localAchievements.clear();
	}

	/**
	 * Returns list of locked achievements
	 * @return list of locked achievements
	 */
	public static List<Achievement> getLockedAchievements() {
		ArrayList<Achievement> lockedAchievements = new ArrayList<Achievement>();
		
		for (Achievement achievement : achievements) {
			if (!achievement.getValue()) {
				lockedAchievements.add(achievement);
			}
		}
		return lockedAchievements;
	}
	
	/**
	 * Returns list of unlocked achievements
	 * @return list of unlocked achievements
	 */
	public static List<Achievement> getUnlockedAchievements() {
		ArrayList<Achievement> unlockedAchievements = new ArrayList<Achievement>();
		
		for (Achievement achievement : achievements) {
			if (achievement.getValue()) {
				unlockedAchievements.add(achievement);
			}
		}
		return unlockedAchievements;
	}

	/**
	 * Loads resources for achievements
	 * @param resources resources to use
	 * @param packageName package name
	 */
	public static void loadAchievementResources(Resources resources, String packageName) {
		for (Achievement achievement : achievements) {
			achievement.loadResources(resources, packageName);
		}
	}
}