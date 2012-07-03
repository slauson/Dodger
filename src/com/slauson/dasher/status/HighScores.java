package com.slauson.dasher.status;

import android.content.SharedPreferences;

public class HighScores {
	
	public static HighScore highScore1 = new HighScore(1);
	public static HighScore highScore2 = new HighScore(2);
	public static HighScore highScore3 = new HighScore(3);
	public static HighScore highScore4 = new HighScore(4);
	public static HighScore highScore5 = new HighScore(5);

	/**
	 * Loads high scores from application preferences
	 * @param preferences preferences to load from
	 */
	public static void load(SharedPreferences preferences) {
		highScore1.load(preferences);
		highScore2.load(preferences);
		highScore3.load(preferences);
		highScore4.load(preferences);
		highScore5.load(preferences);
	}
	
	/**
	 * Saves upgrades to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public static void save(SharedPreferences.Editor preferencesEditor) {
		highScore1.save(preferencesEditor);
		highScore2.save(preferencesEditor);
		highScore3.save(preferencesEditor);
		highScore4.save(preferencesEditor);
		highScore5.save(preferencesEditor);
	}
}
