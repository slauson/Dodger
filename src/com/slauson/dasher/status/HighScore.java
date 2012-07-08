package com.slauson.dasher.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.SharedPreferences;

/**
 * A high score
 * @author Josh Slauson
 *
 */
public class HighScore {

	private int num;
	private int score;
	private long time;

	public HighScore(int num) {
		this.num = num;
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
	public void load(SharedPreferences preferences) {
		score = preferences.getInt("high_score_score_" + num, 0);
		time = preferences.getLong("high_score_time_" + num, 0);
	}
	
	/**
	 * Saves score to application preferences
	 * @param preferenceEditor preferences to save to
	 */
	public void save(SharedPreferences.Editor preferencesEditor) {
		preferencesEditor.putInt("high_score_score_" + num, score);
		preferencesEditor.putLong("high_score_time_" + num, time);
	}
	
	public String getScoreString() {
		int minutes = (score / 60);
		int seconds = (score % 60);
		
		return "" + minutes + ":" + seconds;
	}
	
	public String getTimeString() {
		
		// no time stored
		if (time == 0) {
			return "";
		}
		
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy hh:mm aa");
		
		return format.format(date);
		
	}
	
}
