package com.slauson.dasher.status;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.slauson.dasher.game.MyGameView;

public class Debugging {

	public static int dropType = MyGameView.POWERUP_NONE;
	public static int level = 0;
	public static boolean levelProgression = false;
	public static boolean godMode = false;
	public static boolean runtimeAnalysis = false;
	
	private static boolean initialized = false;
	
	public static void load(SharedPreferences sharedPreferences) {
		
		initialized = true;
		
		dropType = sharedPreferences.getInt("debugging_drop_type", MyGameView.POWERUP_NONE);
		level = sharedPreferences.getInt("debugging_level", 0);
		levelProgression = sharedPreferences.getBoolean("debugging_level_progression", false);
		godMode = sharedPreferences.getBoolean("debugging_god_mode", false);
		runtimeAnalysis = sharedPreferences.getBoolean("debugging_runtime_analysis", false);
	}

	public static void save(Editor sharedPreferencesEditor) {
		
		sharedPreferencesEditor.putInt("debugging_drop_type", dropType);
		sharedPreferencesEditor.putInt("debugging_level", level);
		sharedPreferencesEditor.putBoolean("debugging_level_progression", levelProgression);
		sharedPreferencesEditor.putBoolean("debugging_god_mode", godMode);
		sharedPreferencesEditor.putBoolean("debugging_runtime_analysis", runtimeAnalysis);
		
		sharedPreferencesEditor.commit();
	}
	
	/**
	 * Returns true if the debugging info was initialized from application preferences
	 * @return true if the debugging info was initialized from application preferences
	 */
	public static boolean initialized() {
		return initialized;
	}

}
