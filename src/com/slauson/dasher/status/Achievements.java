package com.slauson.dasher.status;

import android.content.SharedPreferences;

/**
 * Achievements
 * @author Josh Slauson
 *
 */
public class Achievements {
	
	// constants
	public static final int LOCAL_DESTROY_ASTEROIDS_NUM_1 = 3;
	public static final int LOCAL_DESTROY_ASTEROIDS_NUM_2 = 5;
	public static final int LOCAL_DESTROY_ASTEROIDS_NUM_3 = 10;
	
	public static final int LOCAL_INVULNERABILITY_PASS_THROUGH_NUM = 10;
	public static final int LOCAL_MAGNET_HOLD_IN_PLACE_TIME = 10000;
	public static final int LOCAL_BOMB_DESTROY_DROPS_NUM = 3;
	public static final int LOCAL_DASH_ACTIVATE_DROPS_NUM = 3;
	
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
	public static Achievement localDestroyAsteroidsWithDash1 = new Achievement("local_destroy_asteroids_with_dash_1");
	public static Achievement localDestroyAsteroidsWithDash2 = new Achievement("local_destroy_asteroids_with_dash_2");
	public static Achievement localDestroyAsteroidsWithDash3 = new Achievement("local_destroy_asteroids_with_dash_3");
	
	public static Achievement localDestroyAsteroidsWithDrill1 = new Achievement("local_destroy_asteroids_with_drill_1");
	public static Achievement localDestroyAsteroidsWithDrill2 = new Achievement("local_destroy_asteroids_with_drill_2");
	public static Achievement localDestroyAsteroidsWithDrill3 = new Achievement("local_destroy_asteroids_with_drill_3");
	
	public static Achievement localDestroyAsteroidsWithMagnet1 = new Achievement("local_destroy_asteroids_with_magnet_1");
	public static Achievement localDestroyAsteroidsWithMagnet2 = new Achievement("local_destroy_asteroids_with_magnet_2");
	public static Achievement localDestroyAsteroidsWithMagnet3 = new Achievement("local_destroy_asteroids_with_magnet_3");
	
	public static Achievement localDestroyAsteroidsWithWhiteHole1 = new Achievement("local_destroy_asteroids_with_white_hole_1");
	public static Achievement localDestroyAsteroidsWithWhiteHole2 = new Achievement("local_destroy_asteroids_with_white_hole_2");
	public static Achievement localDestroyAsteroidsWithWhiteHole3 = new Achievement("local_destroy_asteroids_with_white_hole_3");
	
	public static Achievement localDestroyAsteroidsWithBumper1 = new Achievement("local_destroy_asteroids_with_bumper_1");
	public static Achievement localDestroyAsteroidsWithBumper2 = new Achievement("local_destroy_asteroids_with_bumper_2");
	public static Achievement localDestroyAsteroidsWithBumper3 = new Achievement("local_destroy_asteroids_with_bumper_3");
	
	public static Achievement localDestroyAsteroidsWithBomb1 = new Achievement("local_destroy_asteroids_with_bomb_1");
	public static Achievement localDestroyAsteroidsWithBomb2 = new Achievement("local_destroy_asteroids_with_bomb_2");
	public static Achievement localDestroyAsteroidsWithBomb3 = new Achievement("local_destroy_asteroids_with_bomb_3");

	// local achievements - powerups
	public static Achievement localSlowBeforeDestroyed = new Achievement("local_slow_before_destroyed");
	public static Achievement localInvulnerabilityPassThrough = new Achievement("local_invulnerability_pass_through");
	public static Achievement localDrillUseMaximumTime = new Achievement("local_drill_use_maximum_time");
	public static Achievement localWhiteHolePlayerDestroyed = new Achievement("local_white_hole_player_destroyed");
	public static Achievement localMagnetHoldInPlace = new Achievement("local_magnet_hold_in_place");
	public static Achievement localBumperBetween = new Achievement("local_bumper_between");
	public static Achievement localBombDestroyDrops = new Achievement("local_bomb_destroy_drops");
	public static Achievement localDashActivateDrops = new Achievement("local_dash_activate_drops");
	
	// local achievements - playtime
	public static Achievement localPlaytime1 = new Achievement("local_playtime_1");
	public static Achievement localPlaytime2 = new Achievement("local_playtime_2");
	public static Achievement localPlaytime3 = new Achievement("local_playtime_3");

	// global achievements - destroy asteroids
	public static Achievement globalDestroyAsteroidsWithDash1 = new Achievement("global_destroy_asteroids_with_dash_1");
	public static Achievement globalDestroyAsteroidsWithDash2 = new Achievement("global_destroy_asteroids_with_dash_2");
	public static Achievement globalDestroyAsteroidsWithDash3 = new Achievement("global_destroy_asteroids_with_dash_3");
	
	public static Achievement globalDestroyAsteroidsWithDrill1 = new Achievement("global_destroy_asteroids_with_drill_1");
	public static Achievement globalDestroyAsteroidsWithDrill2 = new Achievement("global_destroy_asteroids_with_drill_2");
	public static Achievement globalDestroyAsteroidsWithDrill3 = new Achievement("global_destroy_asteroids_with_drill_3");
	
	public static Achievement globalDestroyAsteroidsWithMagnet1 = new Achievement("global_destroy_asteroids_with_magnet_1");
	public static Achievement globalDestroyAsteroidsWithMagnet2 = new Achievement("global_destroy_asteroids_with_magnet_2");
	public static Achievement globalDestroyAsteroidsWithMagnet3 = new Achievement("global_destroy_asteroids_with_magnet_3");
	
	public static Achievement globalDestroyAsteroidsWithWhiteHole1 = new Achievement("global_destroy_asteroids_with_white_hole_1");
	public static Achievement globalDestroyAsteroidsWithWhiteHole2 = new Achievement("global_destroy_asteroids_with_white_hole_2");
	public static Achievement globalDestroyAsteroidsWithWhiteHole3 = new Achievement("global_destroy_asteroids_with_white_hole_3");
	
	public static Achievement globalDestroyAsteroidsWithBumper1 = new Achievement("global_destroy_asteroids_with_bumper_1");
	public static Achievement globalDestroyAsteroidsWithBumper2 = new Achievement("global_destroy_asteroids_with_bumper_2");
	public static Achievement globalDestroyAsteroidsWithBumper3 = new Achievement("global_destroy_asteroids_with_bumper_3");
	
	public static Achievement globalDestroyAsteroidsWithBomb1 = new Achievement("global_destroy_asteroids_with_bomb_1");
	public static Achievement globalDestroyAsteroidsWithBomb2 = new Achievement("global_destroy_asteroids_with_bomb_2");
	public static Achievement globalDestroyAsteroidsWithBomb3 = new Achievement("global_destroy_asteroids_with_bomb_3");
	
	public static Achievement globalDestroyAsteroidsCollision1 = new Achievement("global_destroy_asteroids_collision_1");
	public static Achievement globalDestroyAsteroidsCollision2 = new Achievement("global_destroy_asteroids_collision_2");
	public static Achievement globalDestroyAsteroidsCollision3 = new Achievement("global_destroy_asteroids_collision_3");

	public static Achievement globalDestroyAsteroidsTotal1 = new Achievement("global_destroy_asteroids_total_1");
	public static Achievement globalDestroyAsteroidsTotal2 = new Achievement("global_destroy_asteroids_total_2");
	public static Achievement globalDestroyAsteroidsTotal3 = new Achievement("global_destroy_asteroids_total_3");
	
	// global achievements - playtime
	public static Achievement globalPlaytime1 = new Achievement("global_playtime_1");
	public static Achievement globalPlaytime2 = new Achievement("global_playtime_2");
	public static Achievement globalPlaytime3 = new Achievement("global_playtime_3");

	/**
	 * Updates global achievements based on GlobalStatistics
	 */
	public void updateGlobalAchievements() {
		
		// destroy asteroids - dash
		if (GlobalStatistics.asteroidsDestroyedByDash >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithDash1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByDash >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithDash2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByDash >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithDash3.setValue(true);
		}
		
		// destroy asteroids - drill
		if (GlobalStatistics.asteroidsDestroyedByDrill >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithDrill1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByDrill >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithDrill2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByDrill >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithDrill3.setValue(true);
		}

		// destroy asteroids - magnet
		if (GlobalStatistics.asteroidsDestroyedByMagnet >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithMagnet1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByMagnet >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithMagnet2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByMagnet >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithMagnet3.setValue(true);
		}

		// destroy asteroids - white hole
		if (GlobalStatistics.asteroidsDestroyedByWhiteHole >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithWhiteHole1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByWhiteHole >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithWhiteHole2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByWhiteHole >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithWhiteHole3.setValue(true);
		}

		// destroy asteroids - bumper
		if (GlobalStatistics.asteroidsDestroyedByBumper >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithBumper1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByBumper >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithBumper2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByBumper >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithBumper3.setValue(true);
		}

		// destroy asteroids - bomb
		if (GlobalStatistics.asteroidsDestroyedByBomb >= GLOBAL_DESTROY_ASTEROIDS_NUM_1) {
			globalDestroyAsteroidsWithBomb1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByBomb >= GLOBAL_DESTROY_ASTEROIDS_NUM_2) {
			globalDestroyAsteroidsWithBomb2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByBomb >= GLOBAL_DESTROY_ASTEROIDS_NUM_3) {
			globalDestroyAsteroidsWithBomb3.setValue(true);
		}
		
		// destroy asteroids - collisions
		if (GlobalStatistics.asteroidsDestroyedByCollision >= GLOBAL_DESTROY_ASTEROIDS_COLLISION_NUM_1) {
			globalDestroyAsteroidsWithDrill1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByCollision >= GLOBAL_DESTROY_ASTEROIDS_COLLISION_NUM_2) {
			globalDestroyAsteroidsWithDrill2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedByCollision >= GLOBAL_DESTROY_ASTEROIDS_COLLISION_NUM_3) {
			globalDestroyAsteroidsWithDrill3.setValue(true);
		}

		// destroy asteroids - total
		if (GlobalStatistics.asteroidsDestroyedTotal >= GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_1) {
			globalDestroyAsteroidsWithDrill1.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedTotal >= GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_2) {
			globalDestroyAsteroidsWithDrill2.setValue(true);
		}
		if (GlobalStatistics.asteroidsDestroyedTotal >= GLOBAL_DESTROY_ASTEROIDS_TOTAL_NUM_3) {
			globalDestroyAsteroidsWithDrill3.setValue(true);
		}
		
		// destroy asteroids - playtime
		if (GlobalStatistics.timePlayed >= GLOBAL_PLAYTIME_1) {
			globalPlaytime1.setValue(true);
		}
		if (GlobalStatistics.timePlayed >= GLOBAL_PLAYTIME_2) {
			globalPlaytime2.setValue(true);
		}
		if (GlobalStatistics.timePlayed >= GLOBAL_PLAYTIME_3) {
			globalPlaytime3.setValue(true);
		}
	}
	
	/**
	 * Loads achievement values from application preferences
	 * @param preferences preferences to load from
	 */
	public void load(SharedPreferences preferences) {
		localDestroyAsteroidsWithDash1.load(preferences);
		localDestroyAsteroidsWithDash2.load(preferences);
		localDestroyAsteroidsWithDash3.load(preferences);
		
		localDestroyAsteroidsWithDrill1.load(preferences);
		localDestroyAsteroidsWithDrill2.load(preferences);
		localDestroyAsteroidsWithDrill3.load(preferences);
		
		localDestroyAsteroidsWithMagnet1.load(preferences);
		localDestroyAsteroidsWithMagnet2.load(preferences);
		localDestroyAsteroidsWithMagnet3.load(preferences);
		
		localDestroyAsteroidsWithWhiteHole1.load(preferences);
		localDestroyAsteroidsWithWhiteHole2.load(preferences);
		localDestroyAsteroidsWithWhiteHole3.load(preferences);
		
		localDestroyAsteroidsWithBumper1.load(preferences);
		localDestroyAsteroidsWithBumper2.load(preferences);
		localDestroyAsteroidsWithBumper3.load(preferences);
		
		localDestroyAsteroidsWithBomb1.load(preferences);
		localDestroyAsteroidsWithBomb2.load(preferences);
		localDestroyAsteroidsWithBomb3.load(preferences);

		localSlowBeforeDestroyed.load(preferences);
		localInvulnerabilityPassThrough.load(preferences);
		localDrillUseMaximumTime.load(preferences);
		localWhiteHolePlayerDestroyed.load(preferences);
		localMagnetHoldInPlace.load(preferences);
		localBumperBetween.load(preferences);
		localBombDestroyDrops.load(preferences);
		localDashActivateDrops.load(preferences);
		
		localPlaytime1.load(preferences);
		localPlaytime2.load(preferences);
		localPlaytime3.load(preferences);

		globalDestroyAsteroidsWithDash1.load(preferences);
		globalDestroyAsteroidsWithDash2.load(preferences);
		globalDestroyAsteroidsWithDash3.load(preferences);
		
		globalDestroyAsteroidsWithDrill1.load(preferences);
		globalDestroyAsteroidsWithDrill2.load(preferences);
		globalDestroyAsteroidsWithDrill3.load(preferences);
		
		globalDestroyAsteroidsWithMagnet1.load(preferences);
		globalDestroyAsteroidsWithMagnet2.load(preferences);
		globalDestroyAsteroidsWithMagnet3.load(preferences);
		
		globalDestroyAsteroidsWithWhiteHole1.load(preferences);
		globalDestroyAsteroidsWithWhiteHole2.load(preferences);
		globalDestroyAsteroidsWithWhiteHole3.load(preferences);
		
		globalDestroyAsteroidsWithBumper1.load(preferences);
		globalDestroyAsteroidsWithBumper2.load(preferences);
		globalDestroyAsteroidsWithBumper3.load(preferences);
		
		globalDestroyAsteroidsWithBomb1.load(preferences);
		globalDestroyAsteroidsWithBomb2.load(preferences);
		globalDestroyAsteroidsWithBomb3.load(preferences);
		
		globalDestroyAsteroidsCollision1.load(preferences);
		globalDestroyAsteroidsCollision2.load(preferences);
		globalDestroyAsteroidsCollision3.load(preferences);

		globalDestroyAsteroidsTotal1.load(preferences);
		globalDestroyAsteroidsTotal2.load(preferences);
		globalDestroyAsteroidsTotal3.load(preferences);
		
		globalPlaytime1.load(preferences);
		globalPlaytime2.load(preferences);
		globalPlaytime3.load(preferences);
	}
	
	/**
	 * Saves achievement values to application preferencesEditor
	 * @param preferenceEditor preferencesEditor to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor) {
		localDestroyAsteroidsWithDash1.save(preferencesEditor);
		localDestroyAsteroidsWithDash2.save(preferencesEditor);
		localDestroyAsteroidsWithDash3.save(preferencesEditor);
		
		localDestroyAsteroidsWithDrill1.save(preferencesEditor);
		localDestroyAsteroidsWithDrill2.save(preferencesEditor);
		localDestroyAsteroidsWithDrill3.save(preferencesEditor);
		
		localDestroyAsteroidsWithMagnet1.save(preferencesEditor);
		localDestroyAsteroidsWithMagnet2.save(preferencesEditor);
		localDestroyAsteroidsWithMagnet3.save(preferencesEditor);
		
		localDestroyAsteroidsWithWhiteHole1.save(preferencesEditor);
		localDestroyAsteroidsWithWhiteHole2.save(preferencesEditor);
		localDestroyAsteroidsWithWhiteHole3.save(preferencesEditor);
		
		localDestroyAsteroidsWithBumper1.save(preferencesEditor);
		localDestroyAsteroidsWithBumper2.save(preferencesEditor);
		localDestroyAsteroidsWithBumper3.save(preferencesEditor);
		
		localDestroyAsteroidsWithBomb1.save(preferencesEditor);
		localDestroyAsteroidsWithBomb2.save(preferencesEditor);
		localDestroyAsteroidsWithBomb3.save(preferencesEditor);

		localSlowBeforeDestroyed.save(preferencesEditor);
		localInvulnerabilityPassThrough.save(preferencesEditor);
		localDrillUseMaximumTime.save(preferencesEditor);
		localWhiteHolePlayerDestroyed.save(preferencesEditor);
		localMagnetHoldInPlace.save(preferencesEditor);
		localBumperBetween.save(preferencesEditor);
		localBombDestroyDrops.save(preferencesEditor);
		localDashActivateDrops.save(preferencesEditor);
		
		localPlaytime1.save(preferencesEditor);
		localPlaytime2.save(preferencesEditor);
		localPlaytime3.save(preferencesEditor);

		globalDestroyAsteroidsWithDash1.save(preferencesEditor);
		globalDestroyAsteroidsWithDash2.save(preferencesEditor);
		globalDestroyAsteroidsWithDash3.save(preferencesEditor);
		
		globalDestroyAsteroidsWithDrill1.save(preferencesEditor);
		globalDestroyAsteroidsWithDrill2.save(preferencesEditor);
		globalDestroyAsteroidsWithDrill3.save(preferencesEditor);
		
		globalDestroyAsteroidsWithMagnet1.save(preferencesEditor);
		globalDestroyAsteroidsWithMagnet2.save(preferencesEditor);
		globalDestroyAsteroidsWithMagnet3.save(preferencesEditor);
		
		globalDestroyAsteroidsWithWhiteHole1.save(preferencesEditor);
		globalDestroyAsteroidsWithWhiteHole2.save(preferencesEditor);
		globalDestroyAsteroidsWithWhiteHole3.save(preferencesEditor);
		
		globalDestroyAsteroidsWithBumper1.save(preferencesEditor);
		globalDestroyAsteroidsWithBumper2.save(preferencesEditor);
		globalDestroyAsteroidsWithBumper3.save(preferencesEditor);
		
		globalDestroyAsteroidsWithBomb1.save(preferencesEditor);
		globalDestroyAsteroidsWithBomb2.save(preferencesEditor);
		globalDestroyAsteroidsWithBomb3.save(preferencesEditor);
		
		globalDestroyAsteroidsTotal1.save(preferencesEditor);
		globalDestroyAsteroidsTotal2.save(preferencesEditor);
		globalDestroyAsteroidsTotal3.save(preferencesEditor);
		
		globalPlaytime1.save(preferencesEditor);
		globalPlaytime2.save(preferencesEditor);
		globalPlaytime3.save(preferencesEditor);
	}

}
