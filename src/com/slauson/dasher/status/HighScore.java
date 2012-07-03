package com.slauson.dasher.status;

import android.content.SharedPreferences;

/**
 * A high score
 * @author Josh Slauson
 *
 */
public class HighScore {

	private int num;
	private int score;

	public HighScore(int num) {
		this.num = num;
		this.score = 0;
	}
	
	/**
	 * Returns score of this high score
	 * @return score of this high score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets score of this high score
	 * @param score score to set high score to
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Loads score from application preferences
	 * @param preferences preferences to load from
	 */
	public void load(SharedPreferences preferences) {
		score = preferences.getInt("" + num, 0);
	}
	
	/**
	 * Saves score to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor) {
		preferencesEditor.putInt("" + num, score);
	}
	
}
