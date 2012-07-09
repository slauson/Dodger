package com.slauson.dasher.status;

import android.content.SharedPreferences;

import com.slauson.dasher.other.Util;

/**
 * A high score
 * @author Josh Slauson
 *
 */
public class HighScore {

	private int score;
	private long time;

	public HighScore() {
		this.score = 0;
		this.time = 0;
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
		
		time = System.currentTimeMillis();
	}
	
	/**
	 * Loads score from application preferences
	 * @param preferences preferences to load from
	 */
	public void load(SharedPreferences preferences, int num) {
		score = preferences.getInt("high_score_score_" + num, 0);
		time = preferences.getLong("high_score_time_" + num, 0);
	}
	
	/**
	 * Saves score to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor, int num) {
		preferencesEditor.putInt("high_score_score_" + num, score);
		preferencesEditor.putLong("high_score_time_" + num, time);
	}
	
	public String getScoreString() {
		
		return Util.getTimeString(score);
	}
	
	public String getDateString() {
		return Util.getDateString(time);
	}
	
}
